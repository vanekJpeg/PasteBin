package ru.vanek.pastebin.utils.implementations;

import org.springframework.stereotype.Component;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.utils.PasteRatingCalculator;
@Component
public class PasteRatingCalculatorImpl implements PasteRatingCalculator {
    public double calculateRate(Paste paste) {
        return (paste.getRate()+paste.getViews()+paste.getAuthor().getRate())*0.1;
    }
}
