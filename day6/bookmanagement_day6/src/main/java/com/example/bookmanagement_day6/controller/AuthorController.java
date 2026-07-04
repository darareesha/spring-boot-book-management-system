package  com.example.bookmanagement_day6.controller;

import  com.example.bookmanagement_day6.dto.AuthorDto;
import  com.example.bookmanagement_day6.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Authors", description = "Operations for managing authors")
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Create a new author", description = "Admin only")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorDto authorDto) {
        AuthorDto savedAuthor = authorService.createAuthor(authorDto);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all authors")
    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    @Operation(summary = "Get author by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.getAuthorById(id), HttpStatus.OK);
    }

    @Operation(summary = "Update an author", description = "Admin only")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(authorService.updateAuthor(id, authorDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete an author", description = "Admin only")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}