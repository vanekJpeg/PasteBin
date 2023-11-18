package ru.vanek.pastebin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vanek.pastebin.models.Paste;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasteRepository extends JpaRepository<Paste,Integer> {
    void deleteAllByExpirationAtBefore(Date date);
    Optional <List<Paste>> findAllByAuthorId(int id);
}
