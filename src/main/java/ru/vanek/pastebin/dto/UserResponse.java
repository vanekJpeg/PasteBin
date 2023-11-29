package ru.vanek.pastebin.dto;



import lombok.Data;

@Data

public class UserResponse {
    private int id;
    private String name;
    private String email;
    public UserResponse(String name, String email,int id) {
        this.id=id;
        this.name = name;
        this.email = email;
    }
}
