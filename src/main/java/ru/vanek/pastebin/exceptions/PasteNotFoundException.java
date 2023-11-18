package ru.vanek.pastebin.exceptions;

public class PasteNotFoundException extends RuntimeException{
    public PasteNotFoundException(String message) {
        super(message);
    }
}
