package ru.vanek.pastebin.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vanek.pastebin.models.Role;
import ru.vanek.pastebin.repositories.RoleRepository;
import ru.vanek.pastebin.services.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    public Role getUserRole(){
        return roleRepository.findByName("ROLE_USER").get();
    }

}
