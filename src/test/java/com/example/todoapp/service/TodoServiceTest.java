package com.example.todoapp.service;

import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    private static final String USERNAME = "testuser";

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo todo1;
    private Todo todo2;

    @BeforeEach
    void setUp() {
        todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTitle("Test Todo 1");
        todo1.setDescription("Description 1");
        todo1.setCompleted(false);
        todo1.setUsername(USERNAME);

        todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Test Todo 2");
        todo2.setDescription("Description 2");
        todo2.setCompleted(true);
        todo2.setUsername(USERNAME);
    }

    @Test
    void testGetAllTodos() {
        List<Todo> todos = Arrays.asList(todo1, todo2);
        when(todoRepository.findByUsernameOrderByCreatedAtDesc(USERNAME)).thenReturn(todos);

        List<Todo> result = todoService.getAllTodos(USERNAME);

        assertEquals(2, result.size());
        verify(todoRepository, times(1)).findByUsernameOrderByCreatedAtDesc(USERNAME);
    }

    @Test
    void testGetTodoById_Found() {
        when(todoRepository.findByIdAndUsername(1L, USERNAME)).thenReturn(Optional.of(todo1));

        Optional<Todo> result = todoService.getTodoById(1L, USERNAME);

        assertTrue(result.isPresent());
        assertEquals("Test Todo 1", result.get().getTitle());
        verify(todoRepository, times(1)).findByIdAndUsername(1L, USERNAME);
    }

    @Test
    void testGetTodoById_NotFound() {
        when(todoRepository.findByIdAndUsername(999L, USERNAME)).thenReturn(Optional.empty());

        Optional<Todo> result = todoService.getTodoById(999L, USERNAME);

        assertFalse(result.isPresent());
        verify(todoRepository, times(1)).findByIdAndUsername(999L, USERNAME);
    }

    @Test
    void testGetTodosByStatus_Active() {
        List<Todo> activeTodos = Arrays.asList(todo1);
        when(todoRepository.findByUsernameAndCompletedOrderByCreatedAtDesc(USERNAME, false)).thenReturn(activeTodos);

        List<Todo> result = todoService.getTodosByStatus(false, USERNAME);

        assertEquals(1, result.size());
        assertFalse(result.get(0).getCompleted());
        verify(todoRepository, times(1)).findByUsernameAndCompletedOrderByCreatedAtDesc(USERNAME, false);
    }

    @Test
    void testGetTodosByStatus_Completed() {
        List<Todo> completedTodos = Arrays.asList(todo2);
        when(todoRepository.findByUsernameAndCompletedOrderByCreatedAtDesc(USERNAME, true)).thenReturn(completedTodos);

        List<Todo> result = todoService.getTodosByStatus(true, USERNAME);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getCompleted());
        verify(todoRepository, times(1)).findByUsernameAndCompletedOrderByCreatedAtDesc(USERNAME, true);
    }

    @Test
    void testCreateTodo() {
        when(todoRepository.save(any(Todo.class))).thenReturn(todo1);

        Todo result = todoService.createTodo(todo1, "TestUser");

        assertNotNull(result);
        assertEquals("Test Todo 1", result.getTitle());
        assertEquals("testuser", todo1.getUsername());
        verify(todoRepository, times(1)).save(todo1);
    }

    @Test
    void testUpdateTodo_Success() {
        Todo updatedDetails = new Todo();
        updatedDetails.setTitle("Updated Title");
        updatedDetails.setDescription("Updated Description");
        updatedDetails.setCompleted(true);

        when(todoRepository.findByIdAndUsername(1L, USERNAME)).thenReturn(Optional.of(todo1));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo1);

        Todo result = todoService.updateTodo(1L, updatedDetails, USERNAME);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertTrue(result.getCompleted());
        verify(todoRepository, times(1)).findByIdAndUsername(1L, USERNAME);
        verify(todoRepository, times(1)).save(todo1);
    }

    @Test
    void testUpdateTodo_NotFound() {
        when(todoRepository.findByIdAndUsername(999L, USERNAME)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            todoService.updateTodo(999L, todo1, USERNAME);
        });

        verify(todoRepository, times(1)).findByIdAndUsername(999L, USERNAME);
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    void testToggleTodoCompletion_FromFalseToTrue() {
        when(todoRepository.findByIdAndUsername(1L, USERNAME)).thenReturn(Optional.of(todo1));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo1);

        Todo result = todoService.toggleTodoCompletion(1L, USERNAME);

        assertTrue(result.getCompleted());
        verify(todoRepository, times(1)).findByIdAndUsername(1L, USERNAME);
        verify(todoRepository, times(1)).save(todo1);
    }

    @Test
    void testToggleTodoCompletion_FromTrueToFalse() {
        when(todoRepository.findByIdAndUsername(2L, USERNAME)).thenReturn(Optional.of(todo2));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo2);

        Todo result = todoService.toggleTodoCompletion(2L, USERNAME);

        assertFalse(result.getCompleted());
        verify(todoRepository, times(1)).findByIdAndUsername(2L, USERNAME);
        verify(todoRepository, times(1)).save(todo2);
    }

    @Test
    void testToggleTodoCompletion_NotFound() {
        when(todoRepository.findByIdAndUsername(999L, USERNAME)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            todoService.toggleTodoCompletion(999L, USERNAME);
        });

        verify(todoRepository, times(1)).findByIdAndUsername(999L, USERNAME);
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    void testDeleteTodo_Success() {
        when(todoRepository.findByIdAndUsername(1L, USERNAME)).thenReturn(Optional.of(todo1));
        doNothing().when(todoRepository).delete(todo1);

        todoService.deleteTodo(1L, USERNAME);

        verify(todoRepository, times(1)).findByIdAndUsername(1L, USERNAME);
        verify(todoRepository, times(1)).delete(todo1);
    }

    @Test
    void testDeleteTodo_NotFound() {
        when(todoRepository.findByIdAndUsername(999L, USERNAME)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            todoService.deleteTodo(999L, USERNAME);
        });

        verify(todoRepository, times(1)).findByIdAndUsername(999L, USERNAME);
        verify(todoRepository, never()).delete(any(Todo.class));
    }

    @Test
    void testDeleteCompletedTodos() {
        List<Todo> completedTodos = Arrays.asList(todo2);
        when(todoRepository.findByUsernameAndCompletedOrderByCreatedAtDesc(USERNAME, true)).thenReturn(completedTodos);
        doNothing().when(todoRepository).deleteAll(completedTodos);

        todoService.deleteCompletedTodos(USERNAME);

        verify(todoRepository, times(1)).findByUsernameAndCompletedOrderByCreatedAtDesc(USERNAME, true);
        verify(todoRepository, times(1)).deleteAll(completedTodos);
    }
}
