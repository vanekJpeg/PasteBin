package ru.vanek.pastebin.utils;

import org.springframework.stereotype.Component;
import ru.vanek.pastebin.models.Paste;
import ru.vanek.pastebin.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRatingCalculator {
    public double calculateRate(User user) {
        List<Double> pasteRates = user.getPastes().stream().map(Paste::getRate).collect(Collectors.toList());
        return user.getRate()+(pasteRates.stream().reduce(1.0, Double::sum)*0.01);
    }
}
//pasteRates.stream().reduce(1.0,(a, b) -> a*b)