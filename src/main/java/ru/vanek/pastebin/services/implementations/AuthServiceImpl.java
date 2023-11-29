package ru.vanek.pastebin.services.implementations;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vanek.pastebin.dto.JwtRequest;
import ru.vanek.pastebin.dto.JwtResponse;
import ru.vanek.pastebin.dto.UserRequest;
import ru.vanek.pastebin.exceptions.AuthException;
import ru.vanek.pastebin.models.User;
import ru.vanek.pastebin.services.AuthService;
import ru.vanek.pastebin.services.UserService;
import ru.vanek.pastebin.utils.JwtTokenUtils;
import ru.vanek.pastebin.utils.UserConverter;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserConverter userConverter;
    private final AuthenticationManager authenticationManager;
    public AuthServiceImpl(UserService userService, JwtTokenUtils jwtTokenUtils, UserConverter userConverter, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userConverter = userConverter;
        this.authenticationManager = authenticationManager;
    }
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token= jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    public ResponseEntity<?> createNewUser(@RequestBody UserRequest userRequest) {
        if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())){
            throw new AuthException("Пароли не совпадают");
        }if(userService.findByUsername(userRequest.getUsername())!=null){
            throw new AuthException("Пользователь с указанным именем уже существует");
        }
        User user= userService.create(userRequest);
        return ResponseEntity.ok(userConverter.convertUserToResponse(user));
    }
}
