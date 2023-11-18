package ru.vanek.pastebin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vanek.pastebin.models.Role;
import ru.vanek.pastebin.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Role getUserRole(){
        return roleRepository.findByName("ROLE_USER").get();
    }

}
