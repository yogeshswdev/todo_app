package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    private static final String USERNAME = "testuser";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    private Todo todo1;
    private Todo todo2;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTitle("Test Todo 1");
        todo1.setDescription("Description 1");
        todo1.setCompleted(false);
        todo1.setUsername(USERNAME);
        todo1.setCreatedAt(now);
        todo1.setUpdatedAt(now);

        todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Test Todo 2");
        todo2.setDescription("Description 2");
        todo2.setCompleted(true);
        todo2.setUsername(USERNAME);
        todo2.setCreatedAt(now);
        todo2.setUpdatedAt(now);
    }

    @Test
    void testGetAllTodos() throws Exception {
        List<Todo> todos = Arrays.asList(todo1, todo2);
        when(todoService.getAllTodos(USERNAME)).thenReturn(todos);

        mockMvc.perform(get("/api/todos").header("X-User-Name", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Test Todo 1")))
                .andExpect(jsonPath("$[1].title", is("Test Todo 2")));

        verify(todoService, times(1)).getAllTodos(USERNAME);
    }

    @Test
    void testGetTodoById_Found() throws Exception {
        when(todoService.getTodoById(1L, USERNAME)).thenReturn(Optional.of(todo1));

        mockMvc.perform(get("/api/todos/1").header("X-User-Name", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Todo 1")))
                .andExpect(jsonPath("$.completed", is(false)));

        verify(todoService, times(1)).getTodoById(1L, USERNAME);
    }

    @Test
    void testGetTodoById_NotFound() throws Exception {
        when(todoService.getTodoById(999L, USERNAME)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/todos/999").header("X-User-Name", USERNAME))
                .andExpect(status().isNotFound());

        verify(todoService, times(1)).getTodoById(999L, USERNAME);
    }

    @Test
    void testGetTodosByStatus_Active() throws Exception {
        List<Todo> activeTodos = Arrays.asList(todo1);
        when(todoService.getTodosByStatus(false, USERNAME)).thenReturn(activeTodos);

        mockMvc.perform(get("/api/todos/status/false").header("X-User-Name", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].completed", is(false)));

        verify(todoService, times(1)).getTodosByStatus(false, USERNAME);
    }

    @Test
    void testGetTodosByStatus_Completed() throws Exception {
        List<Todo> completedTodos = Arrays.asList(todo2);
        when(todoService.getTodosByStatus(true, USERNAME)).thenReturn(completedTodos);

        mockMvc.perform(get("/api/todos/status/true").header("X-User-Name", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].completed", is(true)));

        verify(todoService, times(1)).getTodosByStatus(true, USERNAME);
    }

    @Test
    void testCreateTodo() throws Exception {
        Todo newTodo = new Todo();
        newTodo.setTitle("New Todo");
        newTodo.setDescription("New Description");
        newTodo.setCompleted(false);

        when(todoService.createTodo(any(Todo.class), eq(USERNAME))).thenReturn(todo1);

        mockMvc.perform(post("/api/todos")
                .header("X-User-Name", USERNAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Test Todo 1")));

        verify(todoService, times(1)).createTodo(any(Todo.class), eq(USERNAME));
    }

    @Test
    void testUpdateTodo_Success() throws Exception {
        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Updated Title");
        updatedTodo.setDescription("Updated Description");
        updatedTodo.setCompleted(true);

        todo1.setTitle("Updated Title");
        todo1.setDescription("Updated Description");
        todo1.setCompleted(true);

        when(todoService.updateTodo(eq(1L), any(Todo.class), eq(USERNAME))).thenReturn(todo1);

        mockMvc.perform(put("/api/todos/1")
                .header("X-User-Name", USERNAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.completed", is(true)));

        verify(todoService, times(1)).updateTodo(eq(1L), any(Todo.class), eq(USERNAME));
    }

    @Test
    void testUpdateTodo_NotFound() throws Exception {
        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Updated Title");

        when(todoService.updateTodo(eq(999L), any(Todo.class), eq(USERNAME)))
                .thenThrow(new RuntimeException("Todo not found"));

        mockMvc.perform(put("/api/todos/999")
                .header("X-User-Name", USERNAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTodo)))
                .andExpect(status().isNotFound());

        verify(todoService, times(1)).updateTodo(eq(999L), any(Todo.class), eq(USERNAME));
    }

    @Test
    void testToggleTodoCompletion_Success() throws Exception {
        todo1.setCompleted(true);
        when(todoService.toggleTodoCompletion(1L, USERNAME)).thenReturn(todo1);

        mockMvc.perform(patch("/api/todos/1/toggle").header("X-User-Name", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed", is(true)));

        verify(todoService, times(1)).toggleTodoCompletion(1L, USERNAME);
    }

    @Test
    void testToggleTodoCompletion_NotFound() throws Exception {
        when(todoService.toggleTodoCompletion(999L, USERNAME))
                .thenThrow(new RuntimeException("Todo not found"));

        mockMvc.perform(patch("/api/todos/999/toggle").header("X-User-Name", USERNAME))
                .andExpect(status().isNotFound());

        verify(todoService, times(1)).toggleTodoCompletion(999L, USERNAME);
    }

    @Test
    void testDeleteTodo_Success() throws Exception {
        doNothing().when(todoService).deleteTodo(1L, USERNAME);

        mockMvc.perform(delete("/api/todos/1").header("X-User-Name", USERNAME))
                .andExpect(status().isNoContent());

        verify(todoService, times(1)).deleteTodo(1L, USERNAME);
    }

    @Test
    void testDeleteTodo_NotFound() throws Exception {
        doThrow(new RuntimeException("Todo not found")).when(todoService).deleteTodo(999L, USERNAME);

        mockMvc.perform(delete("/api/todos/999").header("X-User-Name", USERNAME))
                .andExpect(status().isNotFound());

        verify(todoService, times(1)).deleteTodo(999L, USERNAME);
    }

    @Test
    void testDeleteCompletedTodos() throws Exception {
        doNothing().when(todoService).deleteCompletedTodos(USERNAME);

        mockMvc.perform(delete("/api/todos/completed").header("X-User-Name", USERNAME))
                .andExpect(status().isNoContent());

        verify(todoService, times(1)).deleteCompletedTodos(USERNAME);
    }
}
