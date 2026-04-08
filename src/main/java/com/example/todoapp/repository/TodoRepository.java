package com.example.todoapp.repository;

import com.example.todoapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUsernameOrderByCreatedAtDesc(String username);

    List<Todo> findByUsernameAndCompletedOrderByCreatedAtDesc(String username, Boolean completed);

    java.util.Optional<Todo> findByIdAndUsername(Long id, String username);

    List<Todo> findByCompletedOrderByCreatedAtDesc(Boolean completed);

    List<Todo> findAllByOrderByCreatedAtDesc();
}
