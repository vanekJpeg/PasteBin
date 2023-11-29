package ru.vanek.pastebin.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import ru.vanek.pastebin.exceptions.*;

@RestControllerAdvice
public class PasteBinExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),"Неизвестная ошибка"),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleNotEnoughRulesException(NotEnoughRulesException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.METHOD_NOT_ALLOWED.value(),e.getMessage()),HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleDefaultMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),"Ошибка валидации"),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(),"Неправильный логин или пароль"),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAuthException(AuthException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.NOT_FOUND.value(),e.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handlePasteNotFoundException(PasteNotFoundException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.NOT_FOUND.value(),e.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleWrongIdException(WrongIdException e){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
