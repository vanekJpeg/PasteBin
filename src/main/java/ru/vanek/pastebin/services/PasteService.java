package ru.vanek.pastebin.services;

import org.springframework.stereotype.Service;
import ru.vanek.pastebin.dto.PasteDTO;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.models.User;

import java.util.List;
@Service
public interface PasteService {
    public List<PasteDTO> findAll(int page);
    public PasteDTO findOne(int id) ;
    public void save(PasteDTO pasteDTO,String username) ;
    public void update(int id,PasteDTO pasteDTO ,String username) ;
    public void deleteExpiredPastes();
    public List<Paste> getPastesByAuthorId(int id) ;
    public User getAuthor(int id) ;
    public void delete(int id,String username) ;
    public void setAuhtorRate(Paste paste) ;
    public void setPasteRate(Paste paste) ;
    public boolean isEnoughRules(int postId, String username);
}
