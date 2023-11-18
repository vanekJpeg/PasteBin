package ru.vanek.pastebin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vanek.pastebin.dto.UserRequest;
import ru.vanek.pastebin.exceptions.AuthException;
import ru.vanek.pastebin.models.User;
import ru.vanek.pastebin.repositories.UserRepository;
import ru.vanek.pastebin.utils.UserConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    public List<User> findAll(int page) {
        return userRepository.findAll(PageRequest.of(page,10, Sort.by("rate"))).getContent();
    }
    public User findOne(int id) {
        return userRepository.findById(id).orElseThrow(()->new AuthException("Данный пользователь не найден"));
    }
    public User create(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setRoles(List.of(roleService.getUserRole()));
        user.setRate(1);
        userRepository.save(user);
        return user;
    }
    @Transactional
    public void update(int id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }
    public User findByUsername(String name) {
        return userRepository.findByUsername(name).orElse(null);
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(String.format(
                "Пользователь %s не найден",username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }

}