package ru.vanek.pastebin.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vanek.pastebin.dto.PasteDTO;
import ru.vanek.pastebin.exceptions.NotEnoughRulesException;
import ru.vanek.pastebin.exceptions.PasteNotFoundException;
import ru.vanek.pastebin.exceptions.UserNotFoundException;
import ru.vanek.pastebin.exceptions.WrongIdException;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.models.User;
import ru.vanek.pastebin.repositories.PasteRepository;
import ru.vanek.pastebin.repositories.UserRepository;
import ru.vanek.pastebin.services.PasteService;
import ru.vanek.pastebin.utils.PasteConverter;
import ru.vanek.pastebin.utils.PasteRatingCalculator;
import ru.vanek.pastebin.utils.UserRatingCalculator;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PasteServiceImpl implements PasteService {
    private final PasteRepository pasteRepository;
    private final UserRatingCalculator userRatingCalculator;
    private final PasteRatingCalculator pasteRatingCalculator;
    private final UserRepository userRepository;
    private final PasteConverter pasteConverter;
    @Autowired
    public PasteServiceImpl(PasteRepository pasteRepository, UserRatingCalculator userRatingCalculator,
                        PasteRatingCalculator pasteRatingCalculator, UserRepository userRepository, PasteConverter pasteConverter) {
        this.pasteRepository = pasteRepository;
        this.userRatingCalculator = userRatingCalculator;
        this.pasteRatingCalculator = pasteRatingCalculator;
        this.userRepository = userRepository;
        this.pasteConverter = pasteConverter;
    }
    public List<PasteDTO> findAll(int page) {
        return pasteRepository.findAll(PageRequest.of(page,10, Sort.by("rate"))).getContent().stream().map(pasteConverter::convertPasteToDto).collect(Collectors.toList());
    }
    @Transactional
    public PasteDTO findOne(int id) {
        Paste paste = pasteRepository.findById(id).orElseThrow(()->new PasteNotFoundException("Данный пост не найден"));
        paste.setViews(paste.getViews()+1);
        setPasteRate(paste);
        setAuhtorRate(paste);
        return pasteConverter.convertPasteToDto(paste);
    }
    @Transactional
    public void save(PasteDTO pasteDTO,String username) {
        Paste paste = pasteConverter.convertToPaste(pasteDTO);
        paste.setViews(0);
        paste.setCreatedAt(new Date());
        paste.setAuthor(userRepository.findByUsername(username).get());
        paste.setRate(pasteRatingCalculator.calculateRate(paste));
        pasteRepository.save(paste);
    }
    @Transactional
    public void update(int id,PasteDTO pasteDTO ,String username) {
        if(isEnoughRules(id,username)){
            Paste paste = pasteRepository.findById(id).orElseThrow(()-> new WrongIdException("Поста с идентификатором: "+id+" - не существует"));
            paste.setName(pasteDTO.getName());
            paste.setText(pasteDTO.getText());
            paste.setExpirationAt(pasteDTO.getExpirationAt());
            pasteRepository.save(paste);
        } else throw new NotEnoughRulesException("У вас недостаточно прав для редактирования поста");
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
    public void delete(int id,String username) {
        if(isEnoughRules(id,username)){
            pasteRepository.deleteById(id);
        } else throw new NotEnoughRulesException("У вас недостаточно прав для удаления поста");
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
    public boolean isEnoughRules(int postId, String username){
        return userRepository.findByUsername(username).get().equals(pasteRepository.findById(postId).orElseThrow(()-> new WrongIdException("Поста с идентификатором: "+postId+" - не существует")).getAuthor());
    }
}
