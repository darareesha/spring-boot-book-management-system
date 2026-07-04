package  com.example.bookmanagement_day6.repository;

import  com.example.bookmanagement_day6.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}