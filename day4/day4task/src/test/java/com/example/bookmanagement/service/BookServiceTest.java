package com.example.bookmanagement.service;

import com.example.bookmanagement.dto.BookDto;
import com.example.bookmanagement.entity.Author;
import com.example.bookmanagement.entity.Book;
import com.example.bookmanagement.exception.ResourceNotFoundException;
import com.example.bookmanagement.repository.AuthorRepository;
import com.example.bookmanagement.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void testCreateBook() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Robert Martin");

        BookDto inputDto = new BookDto(null, "Clean Code", "9780132350884", 1L, null);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle("Clean Code");
        savedBook.setIsbn("9780132350884");
        savedBook.setAuthor(author);

        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(savedBook);

        BookDto result = bookService.createBook(inputDto);

        assertEquals(1L, result.getId());
        assertEquals("Clean Code", result.getTitle());
        assertEquals("Robert Martin", result.getAuthorName());
    }

    @Test
    void testGetBookById_Found() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Robert Martin");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setIsbn("9780132350884");
        book.setAuthor(author);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDto result = bookService.getBookById(1L);

        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    void testGetBookById_NotFound() {
        Mockito.when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookById(99L);
        });
    }
}