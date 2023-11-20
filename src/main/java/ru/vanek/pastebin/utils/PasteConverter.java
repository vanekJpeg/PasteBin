package ru.vanek.pastebin.utils;

import ru.vanek.pastebin.dto.PasteDTO;
import ru.vanek.pastebin.models.Paste;
public interface PasteConverter {
    public PasteDTO convertPasteToDto(Paste paste);
    public Paste convertToPaste(PasteDTO pasteDTO);
    public Paste convertToPaste(PasteDTO pasteDTO,Paste paste);
}
