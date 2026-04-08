package com.example.todoapp.service;

import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    private String normalizeUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "guest";
        }
        return username.trim().toLowerCase();
    }

    // Get all todos
    public List<Todo> getAllTodos(String username) {
        return todoRepository.findByUsernameOrderByCreatedAtDesc(normalizeUsername(username));
    }

    // Get todo by ID
    public Optional<Todo> getTodoById(Long id, String username) {
        return todoRepository.findByIdAndUsername(id, normalizeUsername(username));
    }

    // Get todos by completion status
    public List<Todo> getTodosByStatus(Boolean completed, String username) {
        return todoRepository.findByUsernameAndCompletedOrderByCreatedAtDesc(normalizeUsername(username), completed);
    }

    // Create new todo
    public Todo createTodo(Todo todo, String username) {
        todo.setUsername(normalizeUsername(username));
        return todoRepository.save(todo);
    }

    // Update todo
    public Todo updateTodo(Long id, Todo todoDetails, String username) {
        Todo todo = todoRepository.findByIdAndUsername(id, normalizeUsername(username))
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.getCompleted());

        return todoRepository.save(todo);
    }

    // Toggle todo completion status (check/uncheck)
    public Todo toggleTodoCompletion(Long id, String username) {
        Todo todo = todoRepository.findByIdAndUsername(id, normalizeUsername(username))
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todo.setCompleted(!todo.getCompleted());

        return todoRepository.save(todo);
    }

    // Delete todo
    public void deleteTodo(Long id, String username) {
        Todo todo = todoRepository.findByIdAndUsername(id, normalizeUsername(username))
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todoRepository.delete(todo);
    }

    // Delete all completed todos
    public void deleteCompletedTodos(String username) {
        List<Todo> completedTodos = todoRepository
                .findByUsernameAndCompletedOrderByCreatedAtDesc(normalizeUsername(username), true);
        todoRepository.deleteAll(completedTodos);
    }
}
