package ru.vanek.pastebin.exceptions;

public class NotEnoughRulesException extends  RuntimeException {
    public NotEnoughRulesException(String message) {
        super(message);
    }
}
