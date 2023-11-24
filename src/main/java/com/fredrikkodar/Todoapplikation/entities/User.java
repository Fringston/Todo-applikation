package com.fredrikkodar.Todoapplikation.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//Annoteringen @Entity indikerar att klassen är en JPA entitet, dvs att den ska mappas till en tabell i databasen.
@Entity
//Annoteringen @Table(name = "users") indikerar att entiteten ska mappas till en tabell med namnet users.
@Table(name = "users")
//Klassen User implementerar interfacet UserDetails för att kunna användas med Spring Security.
public class User implements UserDetails {

    //Annoteringen @Id markerar fältet nedan som primärnyckel.
    @Id
    //Annoteringen @GeneratedValue används för att ange hur primärnyckeln ska genereras. I detta fall autogenereras den.
    @GeneratedValue(strategy= GenerationType.AUTO)
    //Annoteringen @Column används för att ange att fältet nedan ska mappas till en kolumn med namnet user_id.
    @Column(name = "user_id")
    private Integer userId;

    //Annoteringen @Column används för att ange att username ska ha unika värden för att förhindra duplicering av användarnamn.
    @Column(unique = true)
    private String username;

    private String password;

    //@ManyToMany indikerar att det finns en många-till-många relation mellan User och Role.
    @ManyToMany(fetch=FetchType.EAGER)
    //Annoteringen @JoinTable används för att ange att det finns en kopplingstabell mellan User och Role.
    @JoinTable(
            name = "user_roles_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> authorities;

    //Standardkonstruktor som krävs av JPA
    public User() {
        super();
        this.authorities = new HashSet<Role>();
    }

    //Konstruktor som tar emot en username, ett lösenord och en uppsättning roller som parameter.
    public User(Integer userId, String username, String password, Set<Role> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public void setPassword(String password) {this.password = password;}

    public void setUserName(String username) {
        this.username = username;
    }

    //Metod som krävs av UserDetails interfacet. Den returnerar uppsättningen av roller (auktoriteter) som användaren har.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    //Metod som krävs av UserDetails interfacet. Den returnerar true om användarens konto inte har gått ut, annars false.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Metod som krävs av UserDetails interfacet. Den returnerar true om användarens konto inte är låst, annars false.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //Metod som krävs av UserDetails interfacet. Den returnerar true om användarens autentiseringsuppgifter inte har gått ut, annars false.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //Metod som krävs av UserDetails interfacet. Den returnerar true om användarens konto är aktiverat, annars false.
    @Override
    public boolean isEnabled() {
        return true;
    }
}
