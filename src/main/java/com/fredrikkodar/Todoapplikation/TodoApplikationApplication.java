package com.fredrikkodar.Todoapplikation;

import com.fredrikkodar.Todoapplikation.entities.Role;
import com.fredrikkodar.Todoapplikation.entities.User;
import com.fredrikkodar.Todoapplikation.repository.RoleRepository;
import com.fredrikkodar.Todoapplikation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TodoApplikationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplikationApplication.class, args);
	}

	//Denna metod definierar en bean för CommandLineRunner, som används för att köra specifik kod vid applikationens uppstart.
	//Här används den för att lägga till initial data i databasen om den är tom.
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			//Kontrollerar om en roll med auktoriteten "ADMIN" redan finns i databasen.
			// Om den finns, avslutas metoden tidigt och inget mer görs.
			if (roleRepository.findByAuthority("ADMIN").isPresent()) return;

			//Skapar en ny Role-instans med auktoriteten "ADMIN" och sparar den i databasen.
			Role adminRole = roleRepository.save(new Role("ADMIN"));

			//Skapar en ny Role-instans med auktoriteten "USER" och sparar den i databasen.
			roleRepository.save(new Role("USER"));

			//Skapar en uppsättning av roller och lägger till adminRole i uppsättningen.
			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			//Skapar en ny User-instans med användarnamnet "admin", ett krypterat lösenord och rollerna.
			//Användaren sparas i databasen genom UserRepository.
			User admin = new User(1, "admin", passwordEncoder.encode("password"), roles);
			userRepository.save(admin);
		};
	}

}
