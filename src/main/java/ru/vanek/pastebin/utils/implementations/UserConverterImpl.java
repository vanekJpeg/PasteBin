package ru.vanek.pastebin.utils.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vanek.pastebin.dto.UserRequest;
import ru.vanek.pastebin.dto.UserResponse;
import ru.vanek.pastebin.models.User;
import ru.vanek.pastebin.utils.UserConverter;
@Component
public class UserConverterImpl implements UserConverter {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserConverterImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public UserResponse convertUserToResponse(User user){
        return new UserResponse(user.getUsername(),user.getEmail(),user.getId());
    }
    public User convertToUser(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        return user;
    }
    public User convertToUser(UserRequest userRequest, User user){
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        return user;
    }
}