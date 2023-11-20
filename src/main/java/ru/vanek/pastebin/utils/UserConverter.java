package ru.vanek.pastebin.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vanek.pastebin.dto.UserRequest;
import ru.vanek.pastebin.dto.UserResponse;
import ru.vanek.pastebin.models.User;

@Component
public interface UserConverter {

    public UserResponse convertUserToResponse(User user);
    public User convertToUser(UserRequest userRequest, User user);
    public User convertToUser(UserRequest userRequest);
}
