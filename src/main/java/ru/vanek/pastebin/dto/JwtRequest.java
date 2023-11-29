package ru.vanek.pastebin.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class JwtRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
