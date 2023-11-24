package com.fredrikkodar.Todoapplikation.controller;

import com.fredrikkodar.Todoapplikation.entities.Todo;
import com.fredrikkodar.Todoapplikation.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TodoController är en REST-controller som hanterar HTTP-förfrågningar relaterade till Todo-entiteter.
@RestController
@RequestMapping("/admin/todos")
//Den är annoterad med @CrossOrigin("*") för att tillåta anrop från alla domäner
@CrossOrigin("*")
public class TodoController {

    //Injektion av TodoRepository för att utföra databasoperationer på Todo-entiteter.
    @Autowired
    private TodoRepository todoRepository;

    //Hanterar GET-förfrågningar till "/admin/todos/" för att hämta alla Todos.
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    //Hanterar POST-förfrågningar till "/admin/todos/" för att skapa en ny Todo.
    @PostMapping("/")
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    //Hanterar PUT-förfrågningar till "/admin/todos/{id}" för att uppdatera en befintlig Todo.
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todoDetails) {
        Todo todo = todoRepository.findById(id).get();
        todo.setName(todoDetails.getName());
        todo.setDescription(todoDetails.getDescription());
        todo.setCreationDate(todoDetails.getCreationDate());
        todo.setIsDone(todoDetails.getIsDone());
        return todoRepository.save(todo);
    }

    //Hanterar DELETE-förfrågningar till "/admin/todos/{id}" för att ta bort en Todo.
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Integer id) {
        todoRepository.deleteById(id);
    }
}
