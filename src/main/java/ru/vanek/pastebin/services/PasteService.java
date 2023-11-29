package ru.vanek.pastebin.services;


import org.springframework.stereotype.Service;
import ru.vanek.pastebin.dto.PasteDTO;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.models.User;

import java.util.List;
@Service
public interface PasteService {
     List<PasteDTO>  findAll(int page);
     PasteDTO findOne(int id) ;
     void save(PasteDTO pasteDTO,String username) ;
     void update(int id,PasteDTO pasteDTO ,String username) ;
     void deleteExpiredPastes();
     List<PasteDTO> getPastesByAuthor(String author) ;
     User getAuthor(int id) ;
     void delete(int id,String username) ;
     void setAuhtorRate(Paste paste) ;
     void setPasteRate(Paste paste) ;
     boolean isEnoughRules(int postId, String username);
}
