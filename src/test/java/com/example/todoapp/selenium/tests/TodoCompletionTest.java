package com.example.todoapp.selenium.tests;

import com.example.todoapp.selenium.BaseSeleniumTest;
import com.example.todoapp.selenium.pages.TodoPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Selenium UI Tests for Todo Completion/Toggle Functionality
 */
@DisplayName("Todo Completion Tests")
public class TodoCompletionTest extends BaseSeleniumTest {

    private TodoPage todoPage;

    @BeforeEach
    public void setupTest() {
        navigateToHome();
        todoPage = new TodoPage(driver);

        // Setup: Create a test todo
        todoPage.createTodo("Test Todo");
        sleep(500);
    }

    @Test
    @DisplayName("TC_UI_005: Mark Todo as Completed")
    public void testMarkTodoAsCompleted() {
        // Initial state
        assertEquals(1, todoPage.getActiveCount(), "Should have 1 active todo");
        assertEquals(0, todoPage.getCompletedCount(), "Should have 0 completed todos");

        // Test Steps
        todoPage.toggleTodoByTitle("Test Todo");

        // Verification
        assertTrue(todoPage.isTodoCompleted("Test Todo"),
                "Todo should be marked as completed");
        assertEquals(0, todoPage.getActiveCount(), "Active count should decrease to 0");
        assertEquals(1, todoPage.getCompletedCount(), "Completed count should increase to 1");
        assertEquals(1, todoPage.getTotalCount(), "Total count should remain 1");
    }

    @Test
    @DisplayName("TC_UI_006: Uncheck Completed Todo")
    public void testUncheckCompletedTodo() {
        // Setup: Mark todo as completed first
        todoPage.toggleTodoByTitle("Test Todo");
        assertTrue(todoPage.isTodoCompleted("Test Todo"), "Todo should be completed");

        // Test Steps: Uncheck the todo
        todoPage.toggleTodoByTitle("Test Todo");

        // Verification
        assertTrue(todoPage.isTodoActive("Test Todo"),
                "Todo should be marked as active");
        assertEquals(1, todoPage.getActiveCount(), "Active count should increase to 1");
        assertEquals(0, todoPage.getCompletedCount(), "Completed count should decrease to 0");
        assertEquals(1, todoPage.getTotalCount(), "Total count should remain 1");
    }

    @Test
    @DisplayName("TC_UI_007: Toggle Todo Multiple Times")
    public void testToggleTodoMultipleTimes() {
        // Test Steps
        // Toggle 1: Active -> Completed
        todoPage.toggleTodoByTitle("Test Todo");
        assertTrue(todoPage.isTodoCompleted("Test Todo"), "Should be completed");
        assertEquals(1, todoPage.getCompletedCount(), "Completed count should be 1");

        // Toggle 2: Completed -> Active
        todoPage.toggleTodoByTitle("Test Todo");
        assertTrue(todoPage.isTodoActive("Test Todo"), "Should be active");
        assertEquals(0, todoPage.getCompletedCount(), "Completed count should be 0");

        // Toggle 3: Active -> Completed
        todoPage.toggleTodoByTitle("Test Todo");
        assertTrue(todoPage.isTodoCompleted("Test Todo"), "Should be completed again");

        // Refresh page and verify state persists
        driver.navigate().refresh();
        sleep(1000);
        todoPage = new TodoPage(driver);

        assertTrue(todoPage.isTodoCompleted("Test Todo"),
                "Final state should persist after refresh");
        assertEquals(1, todoPage.getCompletedCount(), "Completed count should persist");
    }

    @Test
    @DisplayName("TC_UI_EXTRA: Toggle Multiple Todos")
    public void testToggleMultipleTodos() {
        // Create additional todos
        todoPage.createTodo("Task 2");
        todoPage.createTodo("Task 3");

        assertEquals(3, todoPage.getActiveCount(), "Should have 3 active todos");

        // Complete two todos
        todoPage.toggleTodoByTitle("Test Todo");
        todoPage.toggleTodoByTitle("Task 2");

        // Verification
        assertEquals(1, todoPage.getActiveCount(), "Should have 1 active todo");
        assertEquals(2, todoPage.getCompletedCount(), "Should have 2 completed todos");
        assertEquals(3, todoPage.getTotalCount(), "Total should be 3");

        assertTrue(todoPage.isTodoCompleted("Test Todo"), "Test Todo should be completed");
        assertTrue(todoPage.isTodoCompleted("Task 2"), "Task 2 should be completed");
        assertTrue(todoPage.isTodoActive("Task 3"), "Task 3 should be active");
    }
}
