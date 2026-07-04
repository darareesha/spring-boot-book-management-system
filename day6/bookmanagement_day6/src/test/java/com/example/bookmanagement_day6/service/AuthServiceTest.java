package  com.example.bookmanagement_day6.service;

import  com.example.bookmanagement_day6.dto.LoginRequestDto;
import  com.example.bookmanagement_day6.dto.LoginResponseDto;
import  com.example.bookmanagement_day6.entity.Role;
import  com.example.bookmanagement_day6.entity.User;
import  com.example.bookmanagement_day6.exception.ResourceNotFoundException;
import  com.example.bookmanagement_day6.repository.UserRepository;
import  com.example.bookmanagement_day6.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private  com.example.bookmanagement_day6.service.AuthService authService;

    @Test
    void testLogin_Success() {
        User user = new User();
        user.setUsername("admin1");
        user.setPassword("hashedPassword123");
        user.setRole(Role.ADMIN);

        LoginRequestDto request = new LoginRequestDto("admin1", "plainPassword");

        Mockito.when(userRepository.findByUsername("admin1")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("plainPassword", "hashedPassword123")).thenReturn(true);
        Mockito.when(jwtUtil.generateToken("admin1")).thenReturn("fake-jwt-token");

        LoginResponseDto response = authService.login(request);

        assertEquals("fake-jwt-token", response.getToken());
    }

    @Test
    void testLogin_WrongPassword() {
        User user = new User();
        user.setUsername("admin1");
        user.setPassword("hashedPassword123");
        user.setRole(Role.ADMIN);

        LoginRequestDto request = new LoginRequestDto("admin1", "wrongPassword");

        Mockito.when(userRepository.findByUsername("admin1")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("wrongPassword", "hashedPassword123")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            authService.login(request);
        });
    }

    @Test
    void testLogin_UserNotFound() {
        LoginRequestDto request = new LoginRequestDto("ghost", "anyPassword");

        Mockito.when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            authService.login(request);
        });
    }
}