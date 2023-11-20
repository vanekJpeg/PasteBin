package ru.vanek.pastebin.utils;

import org.springframework.stereotype.Component;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.models.User;

import java.util.List;
import java.util.stream.Collectors;
@Component
public interface PasteRatingCalculator {
    public double calculateRate(Paste paste) ;
}
