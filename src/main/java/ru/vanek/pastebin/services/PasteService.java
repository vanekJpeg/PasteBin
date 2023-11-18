package ru.vanek.pastebin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vanek.pastebin.exceptions.PasteNotFoundException;
import ru.vanek.pastebin.exceptions.UserNotFoundException;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.models.User;
import ru.vanek.pastebin.repositories.PasteRepository;
import ru.vanek.pastebin.repositories.UserRepository;
import ru.vanek.pastebin.utils.PasteRatingCalculator;
import ru.vanek.pastebin.utils.UserRatingCalculator;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)

public class PasteService {
    private final PasteRepository pasteRepository;
    private final UserRatingCalculator userRatingCalculator;
    private final PasteRatingCalculator pasteRatingCalculator;
    private final UserRepository userRepository;

    @Autowired
    public PasteService(PasteRepository pasteRepository, UserRatingCalculator userRatingCalculator, PasteRatingCalculator pasteRatingCalculator, UserRepository userRepository) {
        this.pasteRepository = pasteRepository;
        this.userRatingCalculator = userRatingCalculator;
        this.pasteRatingCalculator = pasteRatingCalculator;
        this.userRepository = userRepository;
    }
    public List<Paste> findAll(int page) {
        return pasteRepository.findAll(PageRequest.of(page,10, Sort.by("views"))).getContent();
    }
    @Transactional
    public Paste findOne(int id) {
        Paste paste = pasteRepository.findById(id).orElseThrow(()->new PasteNotFoundException("Данный пост не найден"));
        paste.setViews(paste.getViews()+1);
        setPasteRate(paste);
        setAuhtorRate(paste);
        return paste;
    }
    @Transactional
    public void save(Paste paste) {
        pasteRepository.save(paste);
    }
    @Transactional
    public void update(int id, Paste paste) {
        paste.setId(id);
        pasteRepository.save(paste);
    }
    @Scheduled(fixedDelay = 20000)
    @Transactional
   public void deleteExpiredPastes(){
       pasteRepository.deleteAllByExpirationAtBefore(new Date());
 }
    public List<Paste> getPastesByAuthorId(int id) {
        Optional<List<Paste>> pastes;
        if((pastes =pasteRepository.findAllByAuthorId(id)).isPresent()){
            return pastes.get();
        }
        else return Collections.emptyList() ;
    }
    public User getAuthor(int id) {
        if(pasteRepository.findById(id).isPresent())
            return pasteRepository.findById(id).get().getAuthor();
        else throw new UserNotFoundException("Данного автора не существует");
    }
    @Transactional
    public void delete(int id) {
        pasteRepository.deleteById(id);
    }
    @Transactional
    public void setAuhtorRate(Paste paste) {
        User user = userRepository.findByUsername(paste.getAuthor().getUsername()).get();
        user.setRate(userRatingCalculator.calculateRate(user));
        userRepository.save(user);
    }
    @Transactional
    public void setPasteRate(Paste paste) {
        pasteRepository.findById(paste.getId()).get().setRate(pasteRatingCalculator.calculateRate(paste));
    }
}
