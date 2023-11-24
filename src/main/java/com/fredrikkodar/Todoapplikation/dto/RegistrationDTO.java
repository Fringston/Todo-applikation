package com.fredrikkodar.Todoapplikation.dto;

//Klass som används för att överföra data mellan olika delar av applikationen.
public class RegistrationDTO {

    private String username;
    private String password;

    //Standardkonstruktor krävs av JPA.
    public RegistrationDTO() {super();
    }

    //Konstruktor som tar emot ett användarnamn och lösenord.
    public RegistrationDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Registration info: \n" +
                "username: " + this.username + "\n" +
                "password: " + this.password + "\n";}
}

