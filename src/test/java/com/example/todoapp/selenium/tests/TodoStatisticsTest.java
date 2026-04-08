package com.example.todoapp.selenium.tests;

import com.example.todoapp.selenium.BaseSeleniumTest;
import com.example.todoapp.selenium.pages.TodoPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Selenium UI Tests for Statistics Display
 */
@DisplayName("Statistics Display Tests")
public class TodoStatisticsTest extends BaseSeleniumTest {

    private TodoPage todoPage;

    @BeforeEach
    public void setupTest() {
        navigateToHome();
        todoPage = new TodoPage(driver);
    }

    @Test
    @DisplayName("TC_UI_016: Verify Total Count")
    public void testTotalCount() {
        // Initial state
        assertEquals(0, todoPage.getTotalCount(), "Initial total should be 0");

        // Add 5 todos
        for (int i = 1; i <= 5; i++) {
            todoPage.createTodo("Todo " + i);
        }
        assertEquals(5, todoPage.getTotalCount(), "Total should be 5 after adding");

        // Complete 2 todos
        todoPage.toggleTodoByTitle("Todo 1");
        todoPage.toggleTodoByTitle("Todo 2");
        assertEquals(5, todoPage.getTotalCount(), "Total should remain 5 after completing");

        // Delete 1 active todo
        todoPage.deleteTodoByTitle("Todo 3");
        assertEquals(4, todoPage.getTotalCount(), "Total should be 4 after deleting");
    }

    @Test
    @DisplayName("TC_UI_017: Verify Active Count")
    public void testActiveCount() {
        // Add 5 todos (all active)
        for (int i = 1; i <= 5; i++) {
            todoPage.createTodo("Task " + i);
        }
        assertEquals(5, todoPage.getActiveCount(), "Active should be 5");

        // Mark 2 as completed
        todoPage.toggleTodoByTitle("Task 1");
        todoPage.toggleTodoByTitle("Task 2");
        assertEquals(3, todoPage.getActiveCount(), "Active should be 3 after completing 2");

        // Uncheck 1 completed todo
        todoPage.toggleTodoByTitle("Task 1");
        assertEquals(4, todoPage.getActiveCount(), "Active should be 4 after unchecking 1");

        // Delete 1 active todo
        todoPage.deleteTodoByTitle("Task 3");
        assertEquals(3, todoPage.getActiveCount(), "Active should be 3 after deleting 1");
    }

    @Test
    @DisplayName("TC_UI_018: Verify Completed Count")
    public void testCompletedCount() {
        // Add 5 todos
        for (int i = 1; i <= 5; i++) {
            todoPage.createTodo("Item " + i);
        }
        assertEquals(0, todoPage.getCompletedCount(), "Completed should be 0 initially");

        // Complete 3 todos
        todoPage.toggleTodoByTitle("Item 1");
        todoPage.toggleTodoByTitle("Item 2");
        todoPage.toggleTodoByTitle("Item 3");
        assertEquals(3, todoPage.getCompletedCount(), "Completed should be 3");

        // Uncheck 1 completed todo
        todoPage.toggleTodoByTitle("Item 1");
        assertEquals(2, todoPage.getCompletedCount(), "Completed should be 2 after unchecking");

        // Delete 1 completed todo
        todoPage.deleteTodoByTitle("Item 2");
        assertEquals(1, todoPage.getCompletedCount(), "Completed should be 1 after deleting");
    }

    @Test
    @DisplayName("TC_UI_EXTRA: Statistics Update in Real-Time")
    public void testStatisticsUpdateRealTime() {
        // Verify statistics update immediately after each action
        todoPage.createTodo("Real-time Test");
        todoPage.waitForStatistics(1, 1, 0);

        todoPage.toggleTodoByTitle("Real-time Test");
        todoPage.waitForStatistics(1, 0, 1);

        todoPage.toggleTodoByTitle("Real-time Test");
        todoPage.waitForStatistics(1, 1, 0);

        todoPage.deleteTodoByTitle("Real-time Test");
        todoPage.waitForStatistics(0, 0, 0);
    }

    @Test
    @DisplayName("TC_UI_EXTRA: Statistics After Bulk Operations")
    public void testStatisticsAfterBulkOperations() {
        // Create multiple todos
        for (int i = 1; i <= 6; i++) {
            todoPage.createTodo("Bulk " + i);
        }

        // Complete half
        todoPage.toggleTodoByTitle("Bulk 1");
        todoPage.toggleTodoByTitle("Bulk 2");
        todoPage.toggleTodoByTitle("Bulk 3");

        // Verify statistics
        assertEquals(6, todoPage.getTotalCount(), "Total: 6");
        assertEquals(3, todoPage.getActiveCount(), "Active: 3");
        assertEquals(3, todoPage.getCompletedCount(), "Completed: 3");

        // Clear completed
        todoPage.clickClearCompleted();

        // Verify statistics after bulk delete
        assertEquals(3, todoPage.getTotalCount(), "Total: 3");
        assertEquals(3, todoPage.getActiveCount(), "Active: 3");
        assertEquals(0, todoPage.getCompletedCount(), "Completed: 0");
    }
}
