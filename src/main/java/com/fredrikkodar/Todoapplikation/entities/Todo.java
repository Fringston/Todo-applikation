package com.fredrikkodar.Todoapplikation.entities;

import jakarta.persistence.*;

import java.util.Date;

//Annoteringen @Entity indikerar att klassen är en JPA entitet, dvs att den ska mappas till en tabell i databasen.
@Entity
//Annoteringen @Table(name = "todos") indikerar att entiteten ska mappas till en tabell med namnet roles.
@Table(name = "todos")
public class Todo {

    //Primärnyckel för Todo-entiteten, genereras automatiskt.
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "todo_id")
    private Integer todoId;

    private String name;
    private String description;

    //Annoteringen @Temporal(TemporalType.TIMESTAMP) innebär att creationDate ska hanteras som en tidsstämpel i databasen.
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    //Annoteringen @PrePersist används för att specificera en callback-metod som ska köras innan en entitet sparas i databasen.
    @PrePersist
    //Metoden sätter creantionDate till det aktuella datumet och tiden precis innan Todo-entiteten sparas i databasen.
    protected void onCreate() {
        creationDate = new Date();
    }

    private Boolean isDone;


    public Integer getTodoId() {
        return todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean done) {
        isDone = done;
    }
}
