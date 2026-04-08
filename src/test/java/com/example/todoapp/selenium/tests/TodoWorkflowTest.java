package com.example.todoapp.selenium.tests;

import com.example.todoapp.selenium.BaseSeleniumTest;
import com.example.todoapp.selenium.pages.TodoPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Selenium UI Tests for End-to-End Workflows
 */
@DisplayName("End-to-End Workflow Tests")
public class TodoWorkflowTest extends BaseSeleniumTest {

    private TodoPage todoPage;

    @BeforeEach
    public void setupTest() {
        navigateToHome();
        todoPage = new TodoPage(driver);
    }

    @Test
    @DisplayName("TC_WF_001: Complete Todo Management Workflow")
    public void testCompleteTodoLifecycle() {
        // Step 1: Create
        todoPage.createTodo("Buy milk");
        assertTrue(todoPage.isTodoVisible("Buy milk"), "Todo should be created");

        // Step 2: Read - Verify in All and Active filters
        todoPage.clickAllFilter();
        assertTrue(todoPage.isTodoVisible("Buy milk"), "Should appear in All");
        todoPage.clickActiveFilter();
        assertTrue(todoPage.isTodoVisible("Buy milk"), "Should appear in Active");

        // Step 3: Update - Change title (simulated by creating new and deleting old)
        // Note: Direct update not available in UI, so we verify toggle instead

        // Step 4: Toggle - Mark as completed
        todoPage.toggleTodoByTitle("Buy milk");
        assertTrue(todoPage.isTodoCompleted("Buy milk"), "Should be completed");

        // Step 5: Filter - Verify appears in Completed filter only
        todoPage.clickCompletedFilter();
        assertTrue(todoPage.isTodoVisible("Buy milk"), "Should appear in Completed");
        todoPage.clickActiveFilter();
        assertTrue(todoPage.isEmptyStateVisible() || !todoPage.isTodoVisible("Buy milk"),
                "Should not appear in Active");

        // Step 6: Delete
        todoPage.clickAllFilter();
        todoPage.deleteTodoByTitle("Buy milk");

        // Step 7: Verify - Confirm deleted from all filters
        assertFalse(todoPage.isTodoVisible("Buy milk"), "Should be deleted");
        assertEquals(0, todoPage.getTotalCount(), "Total should be 0");
        assertEquals(0, todoPage.getActiveCount(), "Active should be 0");
        assertEquals(0, todoPage.getCompletedCount(), "Completed should be 0");
    }

    @Test
    @DisplayName("TC_WF_002: Managing Multiple Todos")
    public void testMultipleTodosManagement() {
        // Step 1: Create 5 todos
        String[] todos = { "Todo 1", "Todo 2", "Todo 3", "Todo 4", "Todo 5" };
        for (String todo : todos) {
            todoPage.createTodo(todo);
        }
        assertEquals(5, todoPage.getTotalCount(), "Should have 5 todos");

        // Step 2: Complete 3 of them
        todoPage.toggleTodoByTitle("Todo 1");
        todoPage.toggleTodoByTitle("Todo 2");
        todoPage.toggleTodoByTitle("Todo 3");

        // Step 3: Filter by Active - verify 2 shown
        todoPage.clickActiveFilter();
        assertEquals(2, todoPage.getVisibleTodoCount(), "Should show 2 active");

        // Step 4: Filter by Completed - verify 3 shown
        todoPage.clickCompletedFilter();
        assertEquals(3, todoPage.getVisibleTodoCount(), "Should show 3 completed");

        // Step 5: Delete 1 completed todo
        todoPage.deleteTodoByTitle("Todo 1");
        assertEquals(2, todoPage.getVisibleTodoCount(), "Should show 2 completed after delete");

        // Step 6: Clear all completed
        todoPage.clickClearCompleted();

        // Step 7: Verify only active todos remain
        todoPage.clickAllFilter();
        assertEquals(2, todoPage.getTotalCount(), "Final total: 2");
        assertEquals(2, todoPage.getActiveCount(), "Final active: 2");
        assertEquals(0, todoPage.getCompletedCount(), "Final completed: 0");
    }

    @Test
    @DisplayName("TC_WF_003: Rapid Toggle Operations")
    public void testRapidToggleOperations() {
        // Setup
        todoPage.createTodo("Toggle Test");

        // Step 1: Toggle to completed
        todoPage.toggleTodoByTitle("Toggle Test");
        assertTrue(todoPage.isTodoCompleted("Toggle Test"), "Should be completed");
        assertEquals(1, todoPage.getCompletedCount(), "Completed: 1");

        // Step 2: Immediately toggle back to active
        todoPage.toggleTodoByTitle("Toggle Test");
        assertTrue(todoPage.isTodoActive("Toggle Test"), "Should be active");
        assertEquals(0, todoPage.getCompletedCount(), "Completed: 0");

        // Step 3: Toggle to completed again
        todoPage.toggleTodoByTitle("Toggle Test");
        assertTrue(todoPage.isTodoCompleted("Toggle Test"), "Should be completed again");

        // Step 4: Refresh page
        driver.navigate().refresh();
        sleep(1000);
        todoPage = new TodoPage(driver);

        // Step 5: Verify final state
        assertTrue(todoPage.isTodoCompleted("Toggle Test"),
                "Final state should persist after refresh");
        assertEquals(1, todoPage.getCompletedCount(), "Completed count should persist");
    }

    @Test
    @DisplayName("TC_WF_004: Create, Filter, and Delete Workflow")
    public void testCreateFilterDeleteWorkflow() {
        // Step 1: Set filter to Active
        todoPage.clickActiveFilter();
        assertTrue(todoPage.isFilterActive("active"), "Active filter should be selected");

        // Step 2: Create 2 new todos (should appear immediately)
        todoPage.createTodo("Workflow Task 1");
        todoPage.createTodo("Workflow Task 2");
        assertEquals(2, todoPage.getVisibleTodoCount(), "2 todos should appear");

        // Step 3: Set filter to Completed
        todoPage.clickCompletedFilter();
        assertTrue(todoPage.isEmptyStateVisible() || todoPage.getVisibleTodoCount() == 0,
                "No completed todos yet");

        // Step 4: Mark 1 todo as completed (while on Completed filter)
        todoPage.clickAllFilter();
        todoPage.toggleTodoByTitle("Workflow Task 1");
        todoPage.clickCompletedFilter();
        assertEquals(1, todoPage.getVisibleTodoCount(), "1 completed todo should appear");

        // Step 5: Click Clear Completed
        todoPage.clickClearCompleted();

        // Step 6: Switch to All filter
        todoPage.clickAllFilter();

        // Step 7: Verify only 1 active todo remains
        assertEquals(1, todoPage.getTotalCount(), "Final total: 1");
        assertEquals(1, todoPage.getActiveCount(), "Final active: 1");
        assertTrue(todoPage.isTodoVisible("Workflow Task 2"), "Task 2 should remain");
    }

    @Test
    @DisplayName("TC_WF_EXTRA: Data Persistence After Page Refresh")
    public void testDataPersistenceAfterRefresh() {
        // Create and manipulate todos
        todoPage.createTodo("Persistent Task 1");
        todoPage.createTodo("Persistent Task 2");
        todoPage.createTodo("Persistent Task 3");

        todoPage.toggleTodoByTitle("Persistent Task 2");

        // Note initial state
        int initialTotal = todoPage.getTotalCount();
        int initialActive = todoPage.getActiveCount();
        int initialCompleted = todoPage.getCompletedCount();

        // Refresh page
        driver.navigate().refresh();
        sleep(1000);
        todoPage = new TodoPage(driver);

        // Verify all data persists
        assertEquals(initialTotal, todoPage.getTotalCount(), "Total should persist");
        assertEquals(initialActive, todoPage.getActiveCount(), "Active should persist");
        assertEquals(initialCompleted, todoPage.getCompletedCount(), "Completed should persist");

        assertTrue(todoPage.isTodoVisible("Persistent Task 1"), "Task 1 should persist");
        assertTrue(todoPage.isTodoVisible("Persistent Task 2"), "Task 2 should persist");
        assertTrue(todoPage.isTodoVisible("Persistent Task 3"), "Task 3 should persist");

        assertTrue(todoPage.isTodoCompleted("Persistent Task 2"),
                "Completion state should persist");
    }
}
