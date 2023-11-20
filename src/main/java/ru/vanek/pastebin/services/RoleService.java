package ru.vanek.pastebin.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.vanek.pastebin.models.Role;
@Service
public interface RoleService {
    public Role getUserRole();
}
