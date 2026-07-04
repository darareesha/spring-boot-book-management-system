package  com.example.bookmanagement_day6.dto;

import  com.example.bookmanagement_day6.entity.Role;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequestDto {

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;

    private Role role;

    public RegisterRequestDto() {
    }

    public RegisterRequestDto(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}