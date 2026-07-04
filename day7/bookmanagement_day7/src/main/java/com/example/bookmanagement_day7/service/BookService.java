package com.example.bookmanagement_day7.service;

import com.example.bookmanagement_day7.dto.BookDto;
import com.example.bookmanagement_day7.entity.Author;
import com.example.bookmanagement_day7.entity.Book;
import com.example.bookmanagement_day7.exception.ResourceNotFoundException;
import com.example.bookmanagement_day7.producer.BookEventProducer;
import com.example.bookmanagement_day7.repository.AuthorRepository;
import com.example.bookmanagement_day7.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final BookEventProducer bookEventProducer;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository,
                       SimpMessagingTemplate messagingTemplate, BookEventProducer bookEventProducer) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.messagingTemplate = messagingTemplate;
        this.bookEventProducer = bookEventProducer;
    }

    public BookDto createBook(BookDto bookDto) {
        log.info("Creating a new book with title: {}", bookDto.getTitle());
        Author author = findAuthorOrThrow(bookDto.getAuthorId());

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());
        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);
        log.info("Book created successfully with id: {}", savedBook.getId());

        // Uni-directional WebSocket push
        messagingTemplate.convertAndSend("/topic/new-books", "New book added: " + savedBook.getTitle());

        // Publish to Kafka topic
        bookEventProducer.sendBookCreatedEvent(savedBook.getTitle());

        return toDto(savedBook);
    }

    public Page<BookDto> getBooks(int page, int size, String sortBy) {
        log.debug("Fetching books - page: {}, size: {}, sortBy: {}", page, size, sortBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return bookPage.map(this::toDto);
    }

    @Cacheable(value = "books", key = "#id")
    public BookDto getBookById(Long id) {
        log.debug("Fetching from DB for id: {}", id);
        Book book = findBookOrThrow(id);
        return toDto(book);
    }

    @CachePut(value = "books", key = "#id")
    public BookDto updateBook(Long id, BookDto bookDto) {
        log.info("Updating DB and cache for id: {}", id);
        Book existingBook = findBookOrThrow(id);
        Author author = findAuthorOrThrow(bookDto.getAuthorId());

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setIsbn(bookDto.getIsbn());
        existingBook.setAuthor(author);

        Book updatedBook = bookRepository.save(existingBook);
        return toDto(updatedBook);
    }

    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        log.info("Deleting from DB and evicting cache for id: {}", id);
        Book existingBook = findBookOrThrow(id);
        bookRepository.delete(existingBook);
    }

    public List<BookDto> getBooksByAuthorName(String authorName) {
        log.debug("Searching books by author name: {}", authorName);
        List<Book> books = bookRepository.findByAuthorName(authorName);
        List<BookDto> result = new ArrayList<>();
        for (Book book : books) {
            result.add(toDto(book));
        }
        return result;
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with id: {}", id);
                    return new ResourceNotFoundException("Book not found with id: " + id);
                });
    }

    private Author findAuthorOrThrow(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Author not found with id: {}", id);
                    return new ResourceNotFoundException("Author not found with id: " + id);
                });
    }

    private BookDto toDto(Book book) {
        Long authorId = null;
        String authorName = null;
        if (book.getAuthor() != null) {
            authorId = book.getAuthor().getId();
            authorName = book.getAuthor().getName();
        }
        return new BookDto(book.getId(), book.getTitle(), book.getIsbn(), authorId, authorName);
    }
}