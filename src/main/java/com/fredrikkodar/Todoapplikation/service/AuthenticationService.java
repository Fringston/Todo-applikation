package com.fredrikkodar.Todoapplikation.service;

import com.fredrikkodar.Todoapplikation.dto.LoginResponseDTO;
import com.fredrikkodar.Todoapplikation.entities.Role;
import com.fredrikkodar.Todoapplikation.entities.User;
import com.fredrikkodar.Todoapplikation.repository.RoleRepository;
import com.fredrikkodar.Todoapplikation.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//Service-klass som hanterar registrering av användare genom att interagera med UserRepository och RoleRepository.
@Service
//Annotation som indikerar att alla metoder i klassen ska köras inom en transaktion.
//Det innebär att om något går fel när man sparar en användare till databasen kommer alla ändringar som gjorts inom metoden att rullas tillbaka.
@Transactional
public class AuthenticationService {

    //@Autowired används för att automatiskt injicera en instans av UserRepository, RoleRepository, PasswordEncoder, AuthenticationManager och TokenService.
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    //Metode som tar emot ett användarnamn och lösenord, krypterar lösenordet, skapar en ny User-instans och sparar den i databasen.
    public User registerUser(String username, String password) {

        //Lösenordet krypteras med hjälp av PasswordEncoder.
        String encodedPassword = passwordEncoder.encode(password);

        //En roll med auktoriteten "USER" hämtas från RoleRepository.
        Role userRole = roleRepository.findByAuthority("USER").get();

        //En uppsättning av roller skapas och userRole läggs till i uppsättningen.
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        //En ny User-instans skapas med det angivna användarnamnet, det krypterade lösenordet och rollerna. Användaren sparas i databasen genom UserRepository och returneras.
        return userRepository.save(new User(0, username, encodedPassword, authorities));
    }


    //Metod som används för att autentisera en användare med ett angivet användarnamn och lösenord,
    //generera en JWT för den autentiserade användaren och returnera en LoginResponseDTO med användarnamnet och JWT.
    public LoginResponseDTO loginUser(String username, String password) {
        try {
            //Använder AuthenticationManager för att autentisera användaren med det angivna användarnamnet och lösenordet.
            //Om autentiseringen lyckas, returneras en Authentication-instans som representerar den autentiserade användaren.
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            //Använder TokenService för att generera en JWT för den autentiserade användaren.
            String token = tokenService.generateJwt(auth);

            //Returnerar en LoginResponseDTO med användarinformation och JWT.
            //Användarinformationen hämtas från UserRepository med hjälp av det angivna användarnamnet.
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
        } catch (AuthenticationException e) {
            //Om autentiseringen misslyckas, returneras en LoginResponseDTO med null-användare och tom JWT.
            return new LoginResponseDTO(null, "");
        }
    }

}