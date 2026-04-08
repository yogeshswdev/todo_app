package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    private String resolveUsername(String usernameHeader, HttpSession session) {
        Object sessionUsername = session.getAttribute("username");
        if (sessionUsername != null && !String.valueOf(sessionUsername).trim().isEmpty()) {
            return String.valueOf(sessionUsername);
        }
        if (usernameHeader == null || usernameHeader.trim().isEmpty()) {
            return "guest";
        }
        return usernameHeader;
    }

    // Get all todos
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(
            @RequestHeader(value = "X-User-Name", required = false) String usernameHeader,
            HttpSession session) {
        List<Todo> todos = todoService.getAllTodos(resolveUsername(usernameHeader, session));
        return ResponseEntity.ok(todos);
    }

    // Get todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id,
            @RequestHeader(value = "X-User-Name", required = false) String usernameHeader,
            HttpSession session) {
        return todoService.getTodoById(id, resolveUsername(usernameHeader, session))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get todos by status
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Todo>> getTodosByStatus(@PathVariable Boolean completed,
            @RequestHeader(value = "X-User-Name", required = false) String usernameHeader,
            HttpSession session) {
        List<Todo> todos = todoService.getTodosByStatus(completed, resolveUsername(usernameHeader, session));
        return ResponseEntity.ok(todos);
    }

    // Create new todo
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo,
            @RequestHeader(value = "X-User-Name", required = false) String usernameHeader,
            HttpSession session) {
        Todo createdTodo = todoService.createTodo(todo, resolveUsername(usernameHeader, session));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    // Update todo
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id,
            @RequestBody Todo todoDetails,
            @RequestHeader(value = "X-User-Name", required = false) String usernameHeader,
            HttpSession session) {
        try {
            Todo updatedTodo = todoService.updateTodo(id, todoDetails, resolveUsername(usernameHeader, session));
            return ResponseEntity.ok(updatedTodo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Toggle todo completion (check/uncheck)
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggleTodoCompletion(@PathVariable Long id,
            @RequestHeader(value = "X-User-Name", required = false) String usernameHeader,
            HttpSession session) {
        try {
            Todo toggledTodo = todoService.toggleTodoCompletion(id, resolveUsername(usernameHeader, session));
            return ResponseEntity.ok(toggledTodo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete todo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id,
            @RequestHeader(value = "X-User-Name", required = false) String usernameHeader,
            HttpSession session) {
        try {
            todoService.deleteTodo(id, resolveUsername(usernameHeader, session));
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete all completed todos
    @DeleteMapping("/completed")
    public ResponseEntity<Void> deleteCompletedTodos(
            @RequestHeader(value = "X-User-Name", required = false) String usernameHeader,
            HttpSession session) {
        todoService.deleteCompletedTodos(resolveUsername(usernameHeader, session));
        return ResponseEntity.noContent().build();
    }
}
