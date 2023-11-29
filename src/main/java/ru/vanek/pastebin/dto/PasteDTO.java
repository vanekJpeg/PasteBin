package ru.vanek.pastebin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
@Data
public class PasteDTO {
    private int id;
    @Size(min = 1, max = 100000)
    private String text;
    @NotEmpty
    private String name;
    private Date expirationAt;

    public PasteDTO(String name, String text, Date expirationAt, int id) {
        this.id=id;
        this.text = text;
        this.name = name;
        this.expirationAt = expirationAt;
    }
}
