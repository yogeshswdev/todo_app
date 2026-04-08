package com.example.todoapp.repository;

import com.example.todoapp.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TodoRepository todoRepository;

    private Todo todo1;
    private Todo todo2;
    private Todo todo3;

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();

        todo1 = new Todo();
        todo1.setTitle("First Todo");
        todo1.setDescription("First Description");
        todo1.setCompleted(false);

        todo2 = new Todo();
        todo2.setTitle("Second Todo");
        todo2.setDescription("Second Description");
        todo2.setCompleted(true);

        todo3 = new Todo();
        todo3.setTitle("Third Todo");
        todo3.setDescription("Third Description");
        todo3.setCompleted(false);
    }

    @Test
    void testSaveTodo() {
        Todo saved = todoRepository.save(todo1);

        assertNotNull(saved.getId());
        assertEquals("First Todo", saved.getTitle());
        assertEquals("First Description", saved.getDescription());
        assertFalse(saved.getCompleted());
    }

    @Test
    void testFindAllByOrderByCreatedAtDesc() {
        entityManager.persist(todo1);
        entityManager.persist(todo2);
        entityManager.persist(todo3);
        entityManager.flush();

        List<Todo> todos = todoRepository.findAllByOrderByCreatedAtDesc();

        assertEquals(3, todos.size());
    }

    @Test
    void testFindByCompletedOrderByCreatedAtDesc_Active() {
        entityManager.persist(todo1);
        entityManager.persist(todo2);
        entityManager.persist(todo3);
        entityManager.flush();

        List<Todo> activeTodos = todoRepository.findByCompletedOrderByCreatedAtDesc(false);

        assertEquals(2, activeTodos.size());
        activeTodos.forEach(todo -> assertFalse(todo.getCompleted()));
    }

    @Test
    void testFindByCompletedOrderByCreatedAtDesc_Completed() {
        entityManager.persist(todo1);
        entityManager.persist(todo2);
        entityManager.persist(todo3);
        entityManager.flush();

        List<Todo> completedTodos = todoRepository.findByCompletedOrderByCreatedAtDesc(true);

        assertEquals(1, completedTodos.size());
        assertTrue(completedTodos.get(0).getCompleted());
    }

    @Test
    void testFindById() {
        Todo saved = entityManager.persist(todo1);
        entityManager.flush();

        Todo found = todoRepository.findById(saved.getId()).orElse(null);

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals("First Todo", found.getTitle());
    }

    @Test
    void testDeleteTodo() {
        Todo saved = entityManager.persist(todo1);
        entityManager.flush();

        todoRepository.delete(saved);

        assertFalse(todoRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void testUpdateTodo() {
        Todo saved = entityManager.persist(todo1);
        entityManager.flush();

        saved.setTitle("Updated Title");
        saved.setCompleted(true);
        Todo updated = todoRepository.save(saved);

        assertEquals("Updated Title", updated.getTitle());
        assertTrue(updated.getCompleted());
    }
}
