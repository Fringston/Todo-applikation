package com.fredrikkodar.Todoapplikation.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

//Annoteringen @Entity indikerar att klassen är en JPA entitet, dvs att den ska mappas till en tabell i databasen.
@Entity
//Annoteringen @Table(name = "roles") indikerar att entiteten ska mappas till en tabell med namnet roles.
@Table(name = "roles")
//Klassen Role implementerar interfacet GrantedAuthority för att kunna användas med Spring Security.
public class Role implements GrantedAuthority {

    //Annoteringen @Id markerar fältet nedan som primärnyckel.
    @Id
    //Annoteringen @GeneratedValue används för att ange hur primärnyckeln ska genereras. I detta fall autogenereras den.
    @GeneratedValue(strategy= GenerationType.AUTO)
    //Annoteringen @Column används för att ange att fältet nedan ska mappas till en kolumn med namnet role_id.
    @Column(name = "role_id")
    private Integer roleId;

    //Variabel som representerar auktoriteten som rollen har.
    private String authority;

    //Standardkonstruktor som krävs av JPA
    public Role() {super();}

    //Konstruktor som tar emot en authority som parameter
    public Role(String authority) {
        this.authority = authority;
    }

    //Konstruktor som tar emot både en rollId och en auktoritet.
    public Role(Integer roleId, String authority) {
        this.roleId = roleId;
        this.authority = authority;
    }

    //getAuthority är en metod som krävs av GrantedAuthority interfacet.
    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
