package ru.vanek.pastebin.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vanek.pastebin.dto.PasteDTO;
import ru.vanek.pastebin.services.PasteService;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/pastebin/pastes")
public class PasteController {
    private final PasteService pasteService;
    @Autowired
    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }
    @GetMapping()
    public List<PasteDTO> getPastes(@RequestParam(value = "page",required = false, defaultValue = "0" ) int page) {
        return pasteService.findAll(page);
    }
    @GetMapping("/{author}")
    public List<PasteDTO> getPastesByAuthorId(@PathVariable String author) {
        return pasteService.getPastesByAuthor(author);
    }
    @GetMapping("/{id}")
    public PasteDTO show(@PathVariable("id") int id) {
        return pasteService.findOne(id);
    }
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody PasteDTO pasteDTO, Principal principal) {
        pasteService.save(pasteDTO,principal.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> edit(Principal principal, @PathVariable("id") int id,@Valid @RequestBody PasteDTO pasteDTO) {
       pasteService.update(id,pasteDTO,principal.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id, Principal principal) {
        pasteService.delete(id,principal.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
