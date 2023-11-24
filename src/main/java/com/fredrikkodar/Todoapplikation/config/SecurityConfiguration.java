package com.fredrikkodar.Todoapplikation.config;

import com.fredrikkodar.Todoapplikation.utils.RSAKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

//Klass som konfigurerar säkerheten för Spring Security
//Den är annoterad med @Configuration för att indikera att det är en konfigurationsklass för Spring Framework
@Configuration
public class SecurityConfiguration {

    //Instansvariabel till en RSAKeyProperties-instans
    private final RSAKeyProperties keys;

    //Konstruktor som tar in en RSAKeyProperties-instans som parameter och lagrar den i keys-instansvariabeln.
    public SecurityConfiguration(RSAKeyProperties keys) {
        this.keys = keys;
    }

    //Denna metod definierar en bean för PasswordEncoder, som används för att koda lösenord.
    //BCryptPasswordEncoder är en implementering av PasswordEncoder som använder BCrypt starka hashfunktion.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Denna metod definierar en bean för AuthenticationManager, som är ansvarig för att validera användarautentisering.
    //DaoAuthenticationProvider är en implementering av AuthenticationProvider som hämtar användardetaljer från en UserDetailsService.
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }

    //Denna metod definierar en bean för SecurityFilterChain, som är en kedja av säkerhetsfilter som tillämpas på inkommande HTTP-förfrågningar.
    //Den innehåller en HttpSecurity som definierar säkerhetsreglerna för applikationen.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //CSRF-skyddet inaktiveras. CSRF, eller Cross-Site Request Forgery, är en attack där en skadlig webbplats lurar en användares webbläsare att utföra en handling på en annan webbplats där användaren är autentiserad.
                .csrf(csrf -> csrf.disable())
                //Alla inkommande förfrågningar till "/auth/**" är tillåtna utan autentisering.
                //Alla förfrågningar till "/admin/**" måste vara autentiserade med rollen "ADMIN".
                //Alla förfrågningar till "/user/**" måste vara autentiserade med rollen "ADMIN" eller "USER".
                //Alla andra förfrågningar måste vara autentiserade. Om en förfrågan inte är autentiserad kommer den att avvisas.
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/user/**").hasAnyRole("ADMIN", "USER");
                    auth.anyRequest().authenticated();
                });
        //OAuth2 Resource Server stöd aktiveras och konfigureras för att använda JWTs.
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        //Sessionshanteringen konfigureras för att inte skapa några sessioner, vilket är typiskt för stateless REST API: er.
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //Konstruktionen av SecurityFilterChain är klar och instansen returneras.
        return http.build();
    }

    //Denna metod definierar en bean för JwtDecoder, som används för att avkoda JWTs.
    @Bean
    public JwtDecoder jwtDecoder() {
        //NimbusJwtDecoder.withPublicKey används för att skapa en JwtDecoder som använder den offentliga nyckeln från RSAKeyProperties för att dekodera JWTs.
        //.build() metoden används för att skapa JwtDecoder instansen.
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    //Denna metod definierar en bean för JwtEncoder, som används för att koda JWTs.
    @Bean
    public JwtEncoder jwtEncoder() {
        //En RSAKey skapas med den offentliga och privata nyckeln från RSAKeyProperties.
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        //En JWKSource skapas från den genererade RSAKey.
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        //En JwtEncoder skapas med den genererade JWKSource.
        return new NimbusJwtEncoder(jwks);
    }

    //Denna metod definierar en bean för JwtAuthenticationConverter, som används för att konvertera en JWT till en Authentication-instans.
    //Detta behövs för att autentisera användaren och tilldela behörigheter baserat på JWT.
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        //Skapar en JwtGrantedAuthoritiesConverter, som används för att konvertera JWT-anspråk till GrantedAuthority-instanser.
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        //Sätter anspråksnamnet för auktoriteter till "roles". Detta innebär att auktoriteter kommer att extraheras från "roles"-anspråket i JWT.
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        //Sätter prefixet för auktoriteter till "ROLE_". Detta innebär att alla auktoriteter som extraheras från JWT kommer att ha "ROLE_" som prefix.
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        //Skapar en JwtAuthenticationConverter, som används för att konvertera en JWT till en Authentication-instans.
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        //Sätter JwtGrantedAuthoritiesConverter för JwtAuthenticationConverter.
        //Detta innebär att JwtAuthenticationConverter kommer att använda JwtGrantedAuthoritiesConverter för att konvertera JWT-anspråk till GrantedAuthority-instanser.
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }

}
