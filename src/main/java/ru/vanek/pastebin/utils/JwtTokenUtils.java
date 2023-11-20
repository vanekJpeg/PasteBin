package ru.vanek.pastebin.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public interface JwtTokenUtils {
    public String generateToken(UserDetails userDetails);

    public String getUsername(String token);

    public List<String> getRoles(String token);

    public Claims getAllClaimsFromToken(String token);
}
