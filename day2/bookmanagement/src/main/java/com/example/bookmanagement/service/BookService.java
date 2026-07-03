package com.example.bookmanagement.service;

import com.example.bookmanagement.dto.BookDto;
import com.example.bookmanagement.entity.Book;
import com.example.bookmanagement.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto createBook(BookDto bookDto) {
        log.info("Creating a new book with title: {}", bookDto.getTitle());
        Book book = toEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        log.info("Book created successfully with id: {}", savedBook.getId());
        return toDto(savedBook);
    }

    public List<BookDto> getAllBooks() {
        log.debug("Fetching all books from database");
        return bookRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        log.debug("Fetching book with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with id: {}", id);
                    return new RuntimeException("Book not found with id: " + id);
                });
        return toDto(book);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        log.info("Updating book with id: {}", id);
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update - book not found with id: {}", id);
                    return new RuntimeException("Book not found with id: " + id);
                });

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setIsbn(bookDto.getIsbn());

        Book updatedBook = bookRepository.save(existingBook);
        log.info("Book updated successfully with id: {}", updatedBook.getId());
        return toDto(updatedBook);
    }

    public void deleteBook(Long id) {
        log.info("Deleting book with id: {}", id);
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot delete - book not found with id: {}", id);
                    return new RuntimeException("Book not found with id: " + id);
                });
        bookRepository.delete(existingBook);
        log.info("Book deleted successfully with id: {}", id);
    }

    private BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn());
    }

    private Book toEntity(BookDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        return book;
    }
}