package ru.vanek.pastebin.exceptions;

import lombok.Data;

import java.util.Date;


@Data
public class ExceptionResponse {
    private int status;
    private String message;
    private Date timestamp;

    public ExceptionResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
