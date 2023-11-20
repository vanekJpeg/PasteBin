package ru.vanek.pastebin.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.vanek.pastebin.dto.JwtRequest;
import ru.vanek.pastebin.dto.UserRequest;
@Service
public interface AuthService {
    public ResponseEntity<?> createAuthToken(JwtRequest authRequest);
    public ResponseEntity<?> createNewUser(UserRequest userRequest);//todo is needed RequestBody
}