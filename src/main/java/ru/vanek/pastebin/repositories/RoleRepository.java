package ru.vanek.pastebin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vanek.pastebin.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role>  findByName(String name);
}
