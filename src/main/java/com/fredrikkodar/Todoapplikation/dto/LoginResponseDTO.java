package com.fredrikkodar.Todoapplikation.dto;

import com.fredrikkodar.Todoapplikation.entities.User;

//Klass som används för att överföra data mellan olika delar av applikationen.
public class LoginResponseDTO {

    //Dessa fält representerar en User-instans och en JWT (JSON Web Token).
    private User user;
    private String jwt;

    //Konstruktor som tar emot en User-instans och en JWT.
    public LoginResponseDTO(User user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
