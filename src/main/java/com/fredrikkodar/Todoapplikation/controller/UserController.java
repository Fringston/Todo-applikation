package com.fredrikkodar.Todoapplikation.controller;

import org.springframework.web.bind.annotation.*;

//UserController-klassen är en REST-Controller som hanterar alla anrop som görs till endpointen /user
@RestController
@RequestMapping("/user")
//Den är annoterad med @CrossOrigin("*") för att tillåta anrop från alla domäner
@CrossOrigin("*")
public class UserController {

    //Metod som returnerar en sträng som säger att användaren har tillgång till denna resurs
    //Den är annoterad med @GetMapping("/") för att indikera att den ska hantera alla HTTP GET-anrop till endpointen /user/
    @GetMapping("/")
    public String helloUserController() {
        return "User level access granted";
    }
}
