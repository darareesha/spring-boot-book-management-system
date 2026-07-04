package  com.example.bookmanagement_day6.repository;

import  com.example.bookmanagement_day6.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.author.name = :authorName")   //bookcontroller and boookservice
    List<Book> findByAuthorName(@Param("authorName") String authorName);

}