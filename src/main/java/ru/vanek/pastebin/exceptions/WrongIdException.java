package ru.vanek.pastebin.exceptions;

public class WrongIdException extends RuntimeException{
    public WrongIdException(String message) {
        super(message);
    }
}
