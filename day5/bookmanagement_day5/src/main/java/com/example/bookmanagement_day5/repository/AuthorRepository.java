package com.example.bookmanagement_day5.repository;

import com.example.bookmanagement_day5.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}