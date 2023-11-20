package ru.vanek.pastebin.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.vanek.pastebin.dto.UserRequest;
import ru.vanek.pastebin.dto.UserResponse;
import ru.vanek.pastebin.models.User;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    public List<UserResponse> findAll(int page);
    public UserResponse findOne(int id);
    public User create(UserRequest userRequest);
    public void update(int userId,UserRequest userRequest, String username);
    public void delete(int id, String username);
    public User findByUsername(String name);
    public UserDetails loadUserByUsername(String username);
    public boolean isEnoughRules(int userId, String username);
}