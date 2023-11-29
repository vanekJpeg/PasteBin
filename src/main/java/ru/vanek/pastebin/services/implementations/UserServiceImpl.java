package ru.vanek.pastebin.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vanek.pastebin.dto.UserRequest;
import ru.vanek.pastebin.dto.UserResponse;
import ru.vanek.pastebin.exceptions.AuthException;
import ru.vanek.pastebin.exceptions.NotEnoughRulesException;
import ru.vanek.pastebin.exceptions.WrongIdException;
import ru.vanek.pastebin.models.User;
import ru.vanek.pastebin.repositories.UserRepository;
import ru.vanek.pastebin.services.RoleService;
import ru.vanek.pastebin.services.UserService;
import ru.vanek.pastebin.utils.UserConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserConverter userConverter;
    public List<UserResponse> findAll(int page) {
        return userRepository.findAll(PageRequest.of(page,10, Sort.by("rate"))).getContent()
                .stream().map(userConverter::convertUserToResponse).collect(Collectors.toList());
    }
    public UserResponse findOne(int id) {
        return userConverter.convertUserToResponse(userRepository.findById(id).orElseThrow(()->new AuthException("Данный пользователь не найден")));
    }
    public User create(UserRequest userRequest) {
        User user = userConverter.convertToUser(userRequest);
        user.setRoles(List.of(roleService.getUserRole()));
        user.setRate(1);
        userRepository.save(user);
        return user;
    }
    @Transactional
    public void update(int userId,UserRequest userRequest, String username) {
        if(isEnoughRules(userId,username)){
            User user =  userConverter.convertToUser(userRequest,userRepository.findById(userId).orElseThrow(()->new WrongIdException("Пользователя с идентификатором: "+userId+" - не существует")));
            userRepository.save(user);
        } else throw new NotEnoughRulesException("У вас недостаточно прав для редактирования пользователя");
    }
    @Transactional
    public void delete(int id, String username) {
        if(isEnoughRules(id,username)){
            userRepository.deleteById(id);
        } else throw new NotEnoughRulesException("У вас недостаточно прав для удаления пользователя");
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
    public boolean isEnoughRules(int userId, String username){
        return username.equals(userRepository.findById(userId).orElseThrow(()->new WrongIdException("Пользователя с идентификатором: "+userId+" - не существует")).getUsername());
    }
}