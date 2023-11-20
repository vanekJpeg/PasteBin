package ru.vanek.pastebin.utils.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.vanek.pastebin.utils.JwtTokenUtils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class JwtTokenUtilsImpl implements JwtTokenUtils {

    @Value("${jwt.secret}")
    private  String secret;
    @DurationUnit(ChronoUnit.MINUTES)
    private Duration jwtLifetime = Duration.ofMinutes(10);

    private final Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode("984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf"),
            SignatureAlgorithm.HS256.getJcaName());
    public String generateToken(UserDetails userDetails){
        Map<String ,Object> claims =new HashMap<>();
        List<String> roleList =userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        claims.put("roles",roleList);

        Date issuedDate= new Date();
        Date expiredDate = new Date(issuedDate.getTime()+jwtLifetime.toMillis());

        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(hmacKey)
                .compact();
    }

    public String getUsername(String token){
        return getAllClaimsFromToken(token).getSubject();
    }
    public List<String> getRoles(String token){
        return getAllClaimsFromToken(token).get("roles",List.class);
    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token).getBody();
    }
}
