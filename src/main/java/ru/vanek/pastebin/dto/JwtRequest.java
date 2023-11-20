package ru.vanek.pastebin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
