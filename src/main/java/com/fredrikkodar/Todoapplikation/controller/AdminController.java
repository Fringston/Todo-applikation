package com.fredrikkodar.Todoapplikation.controller;

import org.springframework.web.bind.annotation.*;

//AdminController-klassen är en REST-Controller som hanterar alla anrop som görs till endpointen /admin
@RestController
@RequestMapping("/admin")
//Den är annoterad med @CrossOrigin("*") för att tillåta anrop från alla domäner
@CrossOrigin("*")
public class AdminController {

    //Metod som returnerar en sträng som säger att användaren har tillgång till denna resurs
    //Den är annoterad med @GetMapping("/") för att indikera att den ska hantera alla HTTP GET-anrop till endpointen /admin/
    @RequestMapping("/")
    public String helloAdminController() {
        return "Admin level access granted";
    }

}
