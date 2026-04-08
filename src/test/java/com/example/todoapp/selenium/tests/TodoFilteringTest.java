package com.example.todoapp.selenium.tests;

import com.example.todoapp.selenium.BaseSeleniumTest;
import com.example.todoapp.selenium.pages.TodoPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Selenium UI Tests for Todo Filtering Functionality
 */
@DisplayName("Todo Filtering Tests")
public class TodoFilteringTest extends BaseSeleniumTest {

    private TodoPage todoPage;

    @BeforeEach
    public void setupTest() {
        navigateToHome();
        todoPage = new TodoPage(driver);

        // Setup: Create test todos
        todoPage.createTodo("Active Task 1");
        todoPage.createTodo("Active Task 2");
        todoPage.createTodo("Task to Complete 1");
        todoPage.createTodo("Task to Complete 2");

        // Mark two as completed
        todoPage.toggleTodoByTitle("Task to Complete 1");
        todoPage.toggleTodoByTitle("Task to Complete 2");
        sleep(500);
    }

    @Test
    @DisplayName("TC_UI_008: Filter All Todos")
    public void testFilterAllTodos() {
        // Test Steps
        todoPage.clickAllFilter();

        // Verification
        assertEquals(4, todoPage.getVisibleTodoCount(), "All 4 todos should be visible");
        assertTrue(todoPage.isFilterActive("all"), "All filter should be active");
        assertEquals(4, todoPage.getTotalCount(), "Total count should be 4");

        // Verify both active and completed are shown
        assertTrue(todoPage.isTodoVisible("Active Task 1"), "Active todo should be visible");
        assertTrue(todoPage.isTodoVisible("Task to Complete 1"), "Completed todo should be visible");
    }

    @Test
    @DisplayName("TC_UI_009: Filter Active Todos")
    public void testFilterActiveTodos() {
        // Test Steps
        todoPage.clickActiveFilter();

        // Verification
        assertEquals(2, todoPage.getVisibleTodoCount(), "Only 2 active todos should be visible");
        assertTrue(todoPage.isFilterActive("active"), "Active filter should be highlighted");
        assertEquals(2, todoPage.getActiveCount(), "Active count should show 2");

        // Verify only active todos are shown
        assertTrue(todoPage.isTodoVisible("Active Task 1"), "Active Task 1 should be visible");
        assertTrue(todoPage.isTodoVisible("Active Task 2"), "Active Task 2 should be visible");

        // All visible todos should be unchecked
        assertTrue(todoPage.isTodoActive("Active Task 1"), "Should be active");
        assertTrue(todoPage.isTodoActive("Active Task 2"), "Should be active");
    }

    @Test
    @DisplayName("TC_UI_010: Filter Completed Todos")
    public void testFilterCompletedTodos() {
        // Test Steps
        todoPage.clickCompletedFilter();

        // Verification
        assertEquals(2, todoPage.getVisibleTodoCount(), "Only 2 completed todos should be visible");
        assertTrue(todoPage.isFilterActive("completed"), "Completed filter should be highlighted");
        assertEquals(2, todoPage.getCompletedCount(), "Completed count should show 2");

        // Verify only completed todos are shown
        assertTrue(todoPage.isTodoVisible("Task to Complete 1"), "Completed task 1 should be visible");
        assertTrue(todoPage.isTodoVisible("Task to Complete 2"), "Completed task 2 should be visible");

        // All visible todos should be checked
        assertTrue(todoPage.isTodoCompleted("Task to Complete 1"), "Should be completed");
        assertTrue(todoPage.isTodoCompleted("Task to Complete 2"), "Should be completed");
    }

    @Test
    @DisplayName("TC_UI_011: Filter with No Results")
    public void testFilterWithNoResults() {
        // Setup: Complete all todos
        todoPage.toggleTodoByTitle("Active Task 1");
        todoPage.toggleTodoByTitle("Active Task 2");
        sleep(500);

        // Test Steps: Filter by Active (should be none)
        todoPage.clickActiveFilter();

        // Verification
        assertTrue(todoPage.isEmptyStateVisible(), "Empty state should be displayed");
        String emptyMessage = todoPage.getEmptyStateMessage();
        assertTrue(emptyMessage.contains("No todos found") || emptyMessage.contains("No active todos"),
                "Empty state message should be shown");
        assertEquals(0, todoPage.getVisibleTodoCount(), "No todos should be visible");
    }

    @Test
    @DisplayName("TC_UI_EXTRA: Filter Switching")
    public void testFilterSwitching() {
        // Test: Switch between filters
        todoPage.clickAllFilter();
        assertEquals(4, todoPage.getVisibleTodoCount(), "All filter should show 4");

        todoPage.clickActiveFilter();
        assertEquals(2, todoPage.getVisibleTodoCount(), "Active filter should show 2");

        todoPage.clickCompletedFilter();
        assertEquals(2, todoPage.getVisibleTodoCount(), "Completed filter should show 2");

        todoPage.clickAllFilter();
        assertEquals(4, todoPage.getVisibleTodoCount(), "Back to All should show 4");
    }

    @Test
    @DisplayName("TC_UI_EXTRA: Create Todo While Filtered")
    public void testCreateTodoWhileFiltered() {
        // Set filter to Completed
        todoPage.clickCompletedFilter();
        assertEquals(2, todoPage.getVisibleTodoCount(), "Should show 2 completed");

        // Create a new todo (will be active)
        todoPage.createTodo("New Active Todo");
        sleep(500);

        // New todo should NOT appear (filter is still Completed)
        // But statistics should update
        assertEquals(3, todoPage.getActiveCount(), "Active count should increase");
        assertEquals(5, todoPage.getTotalCount(), "Total count should increase");

        // Switch to Active filter to see the new todo
        todoPage.clickActiveFilter();
        assertTrue(todoPage.isTodoVisible("New Active Todo"), "New todo should be visible in Active filter");
    }
}
