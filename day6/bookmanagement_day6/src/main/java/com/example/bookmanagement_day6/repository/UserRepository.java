package  com.example.bookmanagement_day6.repository;

import  com.example.bookmanagement_day6.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}