package com.example.todoapp.selenium.tests;

import com.example.todoapp.selenium.BaseSeleniumTest;
import com.example.todoapp.selenium.pages.TodoPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Selenium UI Tests for Todo Deletion Functionality
 */
@DisplayName("Todo Deletion Tests")
public class TodoDeletionTest extends BaseSeleniumTest {

    private TodoPage todoPage;

    @BeforeEach
    public void setupTest() {
        navigateToHome();
        todoPage = new TodoPage(driver);
    }

    @Test
    @DisplayName("TC_UI_012: Delete Single Todo")
    public void testDeleteSingleTodo() {
        // Setup
        todoPage.createTodo("Todo to Delete");
        assertEquals(1, todoPage.getTotalCount(), "Should have 1 todo");

        // Test Steps
        todoPage.deleteTodoByTitle("Todo to Delete");

        // Verification
        assertFalse(todoPage.isTodoVisible("Todo to Delete"), "Todo should be removed");
        assertEquals(0, todoPage.getTotalCount(), "Total count should be 0");
        assertEquals(0, todoPage.getVisibleTodoCount(), "No todos should be visible");

        // Verify persistence after refresh
        driver.navigate().refresh();
        sleep(1000);
        todoPage = new TodoPage(driver);
        assertEquals(0, todoPage.getTotalCount(), "Deletion should persist");
    }

    @Test
    @DisplayName("TC_UI_013: Cancel Todo Deletion")
    public void testCancelTodoDeletion() {
        // Setup
        todoPage.createTodo("Todo to Keep");
        int initialCount = todoPage.getTotalCount();

        // Test Steps
        todoPage.cancelDeleteTodoByTitle("Todo to Keep");
        sleep(500);

        // Verification
        assertTrue(todoPage.isTodoVisible("Todo to Keep"), "Todo should still exist");
        assertEquals(initialCount, todoPage.getTotalCount(), "Count should be unchanged");
    }

    @Test
    @DisplayName("TC_UI_014: Clear All Completed Todos")
    public void testClearAllCompletedTodos() {
        // Setup: Create 3 completed and 2 active todos
        todoPage.createTodo("Active 1");
        todoPage.createTodo("Active 2");
        todoPage.createTodo("Completed 1");
        todoPage.createTodo("Completed 2");
        todoPage.createTodo("Completed 3");

        todoPage.toggleTodoByTitle("Completed 1");
        todoPage.toggleTodoByTitle("Completed 2");
        todoPage.toggleTodoByTitle("Completed 3");
        sleep(500);

        assertEquals(5, todoPage.getTotalCount(), "Should have 5 total");
        assertEquals(2, todoPage.getActiveCount(), "Should have 2 active");
        assertEquals(3, todoPage.getCompletedCount(), "Should have 3 completed");

        // Test Steps
        todoPage.clickClearCompleted();

        // Verification
        assertEquals(2, todoPage.getTotalCount(), "Should have 2 total");
        assertEquals(2, todoPage.getActiveCount(), "Should have 2 active");
        assertEquals(0, todoPage.getCompletedCount(), "Should have 0 completed");

        assertTrue(todoPage.isTodoVisible("Active 1"), "Active 1 should remain");
        assertTrue(todoPage.isTodoVisible("Active 2"), "Active 2 should remain");
        assertFalse(todoPage.isTodoVisible("Completed 1"), "Completed 1 should be removed");
    }

    @Test
    @DisplayName("TC_UI_015: Clear Completed with No Completed Todos")
    public void testClearCompletedWithNoCompletedTodos() {
        // Setup: Only active todos
        todoPage.createTodo("Active 1");
        todoPage.createTodo("Active 2");

        assertEquals(2, todoPage.getActiveCount(), "Should have 2 active");
        assertEquals(0, todoPage.getCompletedCount(), "Should have 0 completed");

        // Test Steps
        todoPage.clickClearCompleted();

        // Verification
        assertEquals(2, todoPage.getTotalCount(), "Total should remain 2");
        assertEquals(2, todoPage.getActiveCount(), "Active should remain 2");
        assertTrue(todoPage.isTodoVisible("Active 1"), "Active todos should remain");
        assertTrue(todoPage.isTodoVisible("Active 2"), "Active todos should remain");
    }

    @Test
    @DisplayName("TC_UI_EXTRA: Delete Multiple Todos One by One")
    public void testDeleteMultipleTodosOneByOne() {
        // Setup
        todoPage.createTodo("Todo 1");
        todoPage.createTodo("Todo 2");
        todoPage.createTodo("Todo 3");

        assertEquals(3, todoPage.getTotalCount(), "Should have 3 todos");

        // Delete todos one by one
        todoPage.deleteTodoByTitle("Todo 1");
        assertEquals(2, todoPage.getTotalCount(), "Should have 2 todos");

        todoPage.deleteTodoByTitle("Todo 2");
        assertEquals(1, todoPage.getTotalCount(), "Should have 1 todo");

        todoPage.deleteTodoByTitle("Todo 3");
        assertEquals(0, todoPage.getTotalCount(), "Should have 0 todos");

        assertTrue(todoPage.isEmptyStateVisible(), "Empty state should be shown");
    }
}
