package com.fredrikkodar.Todoapplikation.repository;

import com.fredrikkodar.Todoapplikation.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Optional<Todo> findById(Integer todoId);
}
