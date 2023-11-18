package ru.vanek.pastebin.dto;

import lombok.Data;

import java.util.Date;
@Data
public class PasteDTO {
    private String text;
    private String name;
    private Date expirationAt;

    public PasteDTO(String name, String text, Date expirationAt) {
        this.text = text;
        this.name = name;
        this.expirationAt = expirationAt;
    }
}
