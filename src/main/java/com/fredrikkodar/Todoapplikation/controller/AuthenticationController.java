package com.fredrikkodar.Todoapplikation.controller;

import com.fredrikkodar.Todoapplikation.dto.LoginResponseDTO;
import com.fredrikkodar.Todoapplikation.dto.RegistrationDTO;
import com.fredrikkodar.Todoapplikation.entities.User;
import com.fredrikkodar.Todoapplikation.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//AuthenticationController-klassen är en REST-Controller som hanterar alla anrop som görs till endpointen /auth
@RestController
@RequestMapping("/auth")
//Den är annoterad med @CrossOrigin("*") för att tillåta anrop från alla domäner
@CrossOrigin("*")
public class AuthenticationController {

    //@Autowired används för att automatiskt injicera en instans av AuthenticationService.
    @Autowired
    private AuthenticationService authenticationService;

    //@PostMapping("/register") mappar HTTP POST-förfrågningar till /auth/register till denna metod.
    //Denna metod tar emot ett RegistrationDTO-instans som innehåller ett användarnamn och lösenord för en användare som försöker registrera sig.
    @PostMapping("/register")
    //@ResponseBody annoteringen indikerar att parametern body ska bindas till kroppen av HTTP-förfrågningen.
    public User registerUser(@RequestBody RegistrationDTO body) {
        //Kallar på registerUser-metoden och skickar användarnamnet och lösenordet från RegistrationDTO-instansen och returnerar den nya användaren.
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }

    //@PostMapping("/login") mappar HTTP POST-förfrågningar till /auth/login till denna metod.
    //Denna metod tar emot ett RegistrationDTO-instans som innehåller ett användarnamn och lösenord för en användare som försöker logga in.
    @PostMapping("/login")
    //Kallar på loginUser metoden i AuthenticationService klassen, skickar användarnamnet och lösenordet från RegistrationDTO instansen.
    //AuthenticationService klassen autentiserar användaren, genererar en JWT för den autentiserade användaren och returnerar en LoginResponseDTO med användarinformation och JWT.
    //Om autentiseringen misslyckas (t.ex. om användarnamnet eller lösenordet är felaktigt), returnerar AuthenticationService en LoginResponseDTO med null-användare och tom JWT.
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
