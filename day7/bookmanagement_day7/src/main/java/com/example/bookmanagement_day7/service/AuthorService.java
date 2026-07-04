package com.example.bookmanagement_day7.service;

import  com.example.bookmanagement_day7.dto.AuthorDto;
import  com.example.bookmanagement_day7.entity.Author;
import  com.example.bookmanagement_day7.entity.Book;
import  com.example.bookmanagement_day7.exception.ResourceNotFoundException;
import  com.example.bookmanagement_day7.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        Author savedAuthor = authorRepository.save(author);
        return toDto(savedAuthor);
    }

    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> result = new ArrayList<>();
        for (Author author : authors) {
            result.add(toDto(author));
        }
        return result;
    }

    public AuthorDto getAuthorById(Long id) {
        Author author = findAuthorOrThrow(id);
        return toDto(author);
    }

    public AuthorDto updateAuthor(Long id, AuthorDto authorDto) {
        Author existingAuthor = findAuthorOrThrow(id);
        existingAuthor.setName(authorDto.getName());
        Author updatedAuthor = authorRepository.save(existingAuthor);
        return toDto(updatedAuthor);
    }
    @Transactional
    public void deleteAuthor(Long id) {
        Author existingAuthor = findAuthorOrThrow(id);
        authorRepository.delete(existingAuthor);
    }

    // Helper: find author by id, or throw a clear error
    private Author findAuthorOrThrow(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    // Helper: convert Author entity -> AuthorDto
    private AuthorDto toDto(Author author) {
        List<Long> bookIds = new ArrayList<>();
        for (Book book : author.getBooks()) {
            bookIds.add(book.getId());
        }
        return new AuthorDto(author.getId(), author.getName(), bookIds);
    }
}