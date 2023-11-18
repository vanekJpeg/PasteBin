package ru.vanek.pastebin.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vanek.pastebin.exceptions.AuthException;
import ru.vanek.pastebin.exceptions.ExceptionResponse;
import ru.vanek.pastebin.exceptions.PasteNotFoundException;
import ru.vanek.pastebin.exceptions.UserNotFoundException;

@RestControllerAdvice
public class PasteBinExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(),"Неправильный логин или пароль"),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAuthException(AuthException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAuthException(UserNotFoundException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAuthException(PasteNotFoundException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
