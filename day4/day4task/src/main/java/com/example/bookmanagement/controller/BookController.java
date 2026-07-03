package com.example.bookmanagement.controller;

import com.example.bookmanagement.dto.BookDto;
import com.example.bookmanagement.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Books", description = "Operations for managing books")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Create a new book", description = "Admin only. Adds a new book linked to an existing author")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto) {
        BookDto savedBook = bookService.createBook(bookDto);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all books", description = "Returns a paginated, sortable list of books")
    @GetMapping
    public ResponseEntity<Page<BookDto>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return new ResponseEntity<>(bookService.getBooks(page, size, sortBy), HttpStatus.OK);
    }

    @Operation(summary = "Get book by ID", description = "Returns a single book by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @Operation(summary = "Update a book", description = "Admin only. Updates an existing book's details")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.updateBook(id, bookDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete a book", description = "Admin only. Deletes a book by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Search books by author name", description = "Returns all books written by a given author")
    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> getBooksByAuthorName(@RequestParam String authorName) {
        return new ResponseEntity<>(bookService.getBooksByAuthorName(authorName), HttpStatus.OK);
    }
}