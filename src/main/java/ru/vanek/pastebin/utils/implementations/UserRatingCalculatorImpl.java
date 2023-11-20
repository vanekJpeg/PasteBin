package ru.vanek.pastebin.utils.implementations;

import org.springframework.stereotype.Component;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.models.User;
import ru.vanek.pastebin.utils.UserRatingCalculator;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserRatingCalculatorImpl implements UserRatingCalculator {
    @Override
    public double calculateRate(User user) {
        List<Double> pasteRates = user.getPastes().stream().map(Paste::getRate).collect(Collectors.toList());
        return user.getRate()+(pasteRates.stream().reduce(1.0, Double::sum)*0.01);
    }
}
