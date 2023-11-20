package ru.vanek.pastebin.utils.implementations;

import org.springframework.stereotype.Component;
import ru.vanek.pastebin.dto.PasteDTO;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.utils.PasteConverter;


@Component
public class PasteConverterImpl implements PasteConverter {
    public PasteDTO convertPasteToDto(Paste paste){
        return new PasteDTO(paste.getName(),paste.getText(),paste.getExpirationAt());
    }
    public Paste convertToPaste(PasteDTO pasteDTO){
        Paste paste = new Paste();
        paste.setName(pasteDTO.getName());
        paste.setText(pasteDTO.getText());
        paste.setExpirationAt(pasteDTO.getExpirationAt());
        return paste;
    }
    public Paste convertToPaste(PasteDTO pasteDTO, Paste paste){
        paste.setName(pasteDTO.getName());
        paste.setText(pasteDTO.getText());
        paste.setExpirationAt(pasteDTO.getExpirationAt());
        return paste;
    }
}
