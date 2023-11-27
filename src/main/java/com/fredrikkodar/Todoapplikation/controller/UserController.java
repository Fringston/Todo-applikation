package com.fredrikkodar.Todoapplikation.controller;

import com.fredrikkodar.Todoapplikation.entities.Todo;
import com.fredrikkodar.Todoapplikation.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//UserController-klassen är en REST-Controller som hanterar alla anrop som görs till endpointen /user
@RestController
@RequestMapping("/user")
//Den är annoterad med @CrossOrigin("*") för att tillåta anrop från alla domäner
@CrossOrigin("*")
public class UserController {

    @Autowired
    private TodoRepository todoRepository;

    //Metod som returnerar en sträng som säger att användaren har tillgång till denna resurs
    //Den är annoterad med @GetMapping("/") för att indikera att den ska hantera alla HTTP GET-anrop till endpointen /user/
    @GetMapping("/")
    public String helloUserController() {
        return "User level access granted";
    }

    //Hanterar GET-förfrågningar till "/user/todos/" för att hämta alla Todos.
    @GetMapping("/todos/")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    //Hanterar GET-förfrågningar till "/admin/todos/{1}" för att hämta en Todo.
    @GetMapping("/todos/{id}")
    public Todo getOneTodo(@PathVariable Integer id) {
        return todoRepository.findById(id).get();}

    //Hanterar POST-förfrågningar till "/user/todos/" för att skapa en ny Todo.
    @PostMapping("/todos/")
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }
}
