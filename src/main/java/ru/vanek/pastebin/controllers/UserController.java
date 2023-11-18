package ru.vanek.pastebin.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.vanek.pastebin.dto.UserRequest;
import ru.vanek.pastebin.dto.UserResponse;
import ru.vanek.pastebin.exceptions.NotEnoughRulesException;
import ru.vanek.pastebin.models.User;
import ru.vanek.pastebin.services.UserService;
import ru.vanek.pastebin.utils.UserConverter;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;

    public UserController(UserService userService, UserConverter userConverter, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping()
    public List<UserResponse> getUsers(@RequestParam(value = "page",required = false, defaultValue = "0" ) int page) {
        return userService.findAll(page).stream().map(userConverter::convertUserToResponse).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public UserResponse show(@PathVariable("id") int id) {
        return userConverter.convertUserToResponse(userService.findOne(id));
    }

    @PatchMapping("/{id}")
    public UserRequest edit(Principal principal, @PathVariable("id") int id, @RequestBody UserRequest userRequest) {
        if(isEnoughRules(id,principal.getName())){
            User user = userConverter.convertToUser(userRequest);
            userService.update(id,user);
            return userRequest;
        } else throw new NotEnoughRulesException("У вас недостаточно прав для редактирования пользователя");

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id, Principal principal) {
        if(isEnoughRules(id,principal.getName())){
            userService.delete(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } else throw new NotEnoughRulesException("У вас недостаточно прав для удаления пользователя");
    }
    public boolean isEnoughRules(int userId, String username){
        return username.equals(userService.findOne(userId).getUsername());
    }

}
