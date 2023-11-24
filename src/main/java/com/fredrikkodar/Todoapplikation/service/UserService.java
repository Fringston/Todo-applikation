package com.fredrikkodar.Todoapplikation.service;

import com.fredrikkodar.Todoapplikation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//Service-klass som implementerar UserDetailsService interfacet från Spring Security.
//Denna klass används för att ladda användaruppgifter under autentiseringsprocessen.
@Service
public class UserService implements UserDetailsService {

    //@Autowired används för att automatiskt injicera en instans av PasswordEncoder.
    @Autowired
    private PasswordEncoder encoder;

    //@Autowired används för att automatiskt injicera en instans av UserRepository.
    @Autowired
    private UserRepository userRepository;

    //loadUserByUsername är en metod som krävs av UserDetailsService interfacet.
    //Denna metod används för att ladda en användares detaljer baserat på deras användarnamn.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In the user details service");

        //Hämtar användaren från databasen baserat på användarnamnet. Om användaren inte finns kastas ett UsernameNotFoundException.
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }
}
