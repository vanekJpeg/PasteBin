package ru.vanek.pastebin.dto;



import lombok.Data;
import ru.vanek.pastebin.models.Paste;

import java.util.List;

@Data

public class UserResponse {
    private String name;
    private String email;
    public UserResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
