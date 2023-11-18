package ru.vanek.pastebin.utils;

import org.springframework.stereotype.Component;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.models.User;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class PasteRatingCalculator {
    public double calculateRate(Paste paste) {

        return (paste.getRate()+paste.getViews()+paste.getAuthor().getRate())*0.1;
    }
}
