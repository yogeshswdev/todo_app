package com.example.todoapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {

    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo();
    }

    @Test
    void testTodoCreation() {
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
        todo.setCompleted(false);

        assertEquals("Test Todo", todo.getTitle());
        assertEquals("Test Description", todo.getDescription());
        assertFalse(todo.getCompleted());
    }

    @Test
    void testTodoWithAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Todo newTodo = new Todo(1L, "Test Title", "Test Description", true, "testuser", now, now);

        assertEquals(1L, newTodo.getId());
        assertEquals("Test Title", newTodo.getTitle());
        assertEquals("Test Description", newTodo.getDescription());
        assertTrue(newTodo.getCompleted());
        assertEquals("testuser", newTodo.getUsername());
        assertEquals(now, newTodo.getCreatedAt());
        assertEquals(now, newTodo.getUpdatedAt());
    }

    @Test
    void testDefaultCompletedValue() {
        todo.setTitle("Test");
        todo.onCreate();

        assertFalse(todo.getCompleted());
        assertEquals("guest", todo.getUsername());
        assertNotNull(todo.getCreatedAt());
        assertNotNull(todo.getUpdatedAt());
    }

    @Test
    void testOnCreateSetsTimestamps() {
        todo.onCreate();

        assertNotNull(todo.getCreatedAt());
        assertNotNull(todo.getUpdatedAt());
        assertEquals(todo.getCreatedAt(), todo.getUpdatedAt());
    }

    @Test
    void testOnUpdateSetsUpdatedAt() throws InterruptedException {
        todo.onCreate();
        LocalDateTime createdAt = todo.getCreatedAt();

        Thread.sleep(10);
        todo.onUpdate();

        assertEquals(createdAt, todo.getCreatedAt());
        assertTrue(todo.getUpdatedAt().isAfter(createdAt));
    }

    @Test
    void testToggleCompletion() {
        todo.setCompleted(false);
        assertFalse(todo.getCompleted());

        todo.setCompleted(true);
        assertTrue(todo.getCompleted());
    }
}
