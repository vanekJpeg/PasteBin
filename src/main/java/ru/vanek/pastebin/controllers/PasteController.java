package ru.vanek.pastebin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vanek.pastebin.dto.PasteDTO;
import ru.vanek.pastebin.exceptions.NotEnoughRulesException;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.models.User;
import ru.vanek.pastebin.services.PasteService;
import ru.vanek.pastebin.services.UserService;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pastes")
public class PasteController {
    private final PasteService pasteService;
    private final UserService userService;
    @Autowired
    public PasteController(PasteService pasteService, UserService userService) {
        this.pasteService = pasteService;
        this.userService = userService;
    }
    @GetMapping()
    public List<PasteDTO> getPastes(@RequestParam(value = "page",required = false, defaultValue = "0" ) int page) {

        return pasteService.findAll(page).stream().map(this::convertPasteToDto).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public PasteDTO show(@PathVariable("id") int id) {
        return convertPasteToDto(pasteService.findOne(id));
    }
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody PasteDTO pasteDTO, Principal principal) {
        Paste paste = convertToPaste(pasteDTO,principal);
        paste.setViews(0);
        paste.setExpirationAt(pasteDTO.getExpirationAt());
        paste.setCreatedAt(new Date());
        pasteService.save(paste);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public PasteDTO edit(Principal principal, @PathVariable("id") int id, @RequestBody PasteDTO pasteDTO) {
        if(isEnoughRules(id,principal.getName())){
            Paste paste = convertToPaste(pasteDTO,principal);
            pasteService.update(id,paste);
            return pasteDTO;
        } else throw new NotEnoughRulesException("У вас недостаточно прав для редактирования поста");

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id, Principal principal) {
        if(isEnoughRules(id,principal.getName())){
            pasteService.delete(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } else throw new NotEnoughRulesException("У вас недостаточно прав для удаления поста");
    }
    public boolean isEnoughRules(int postId, String username){
        return userService.findByUsername(username) == pasteService.findOne(postId).getAuthor();
    }
    public PasteDTO convertPasteToDto(Paste paste){
        return new PasteDTO(paste.getName(),paste.getText(),paste.getExpirationAt());
    }
    public Paste convertToPaste(PasteDTO pasteDTO,Principal principal){
        User author=userService.findByUsername(principal.getName());
        Paste paste = new Paste();
        paste.setName(pasteDTO.getName());
        paste.setText(pasteDTO.getText());
        paste.setAuthor(author);
        paste.setRate(author.getRate());
        return paste;
    }
}
