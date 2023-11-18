package ru.vanek.pastebin.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vanek.pastebin.dto.UserRequest;
import ru.vanek.pastebin.dto.UserResponse;
import ru.vanek.pastebin.models.User;

@Component
public class UserConverter {
    private final PasswordEncoder passwordEncoder;

    public UserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse convertUserToResponse(User user){
        return new UserResponse(user.getUsername(),user.getEmail());
    }
    public User convertToUser(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));;
        user.setEmail(userRequest.getEmail());
        return user;
    }
}
