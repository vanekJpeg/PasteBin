package ru.vanek.pastebin.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vanek.pastebin.dto.UserRequest;
import ru.vanek.pastebin.dto.UserResponse;
import ru.vanek.pastebin.services.UserService;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public List<UserResponse> getUsers(@RequestParam(value = "page",required = false, defaultValue = "0" ) int page) {
        return userService.findAll(page);
    }
    @GetMapping("/{id}")
    public UserResponse show(@PathVariable("id") int id) {
        return userService.findOne(id);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> edit(Principal principal, @PathVariable("id") int id, @RequestBody UserRequest userRequest) {
        userService.update(id,userRequest,principal.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id, Principal principal) {
            userService.delete(id,principal.getName());
            return ResponseEntity.ok(HttpStatus.OK);
    }
}
