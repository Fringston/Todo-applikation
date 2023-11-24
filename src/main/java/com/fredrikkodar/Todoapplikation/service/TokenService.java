package com.fredrikkodar.Todoapplikation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.stream.Collectors;

//Service-klass som hanterar genereringen av JWT:s för autentiserade användare.
@Service
public class TokenService {

    //@Autowired används för att automatiskt injicera en instans av JwtEncoder och JwtDecoder.
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;


    //Metod som tar emot en Authentication instans som parameter och genererar en JWT för den autentiserade användaren.
    public String generateJwt(Authentication auth) {

        //Skapar en Instant instans för nuvarande tidpunkt.
        Instant now = Instant.now();

        //Samlar alla auktoriteter (roller) för den autentiserade användaren till en sträng.
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(""));

        //Skapar en JwtClaimsSet med issuer, utfärdad tidpunkt, ämne (användarnamn) och roller.
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(auth.getName())
                .claim("roles", scope)
                .build();

        //Använder jwtEncoder för att koda JwtClaimsSet till en JWT och returnerar tokenvärdet.
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
