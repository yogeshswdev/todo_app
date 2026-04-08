package com.example.todoapp.selenium.tests;

import com.example.todoapp.selenium.BaseSeleniumTest;
import com.example.todoapp.selenium.pages.TodoPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Selenium UI Tests for Todo Creation Functionality
 */
@DisplayName("Todo Creation Tests")
public class TodoCreationTest extends BaseSeleniumTest {

    private TodoPage todoPage;

    @BeforeEach
    public void setupTest() {
        navigateToHome();
        todoPage = new TodoPage(driver);
        assertTrue(todoPage.isPageLoaded(), "Todo page should be loaded");
    }

    @Test
    @DisplayName("TC_UI_001: Create Todo with Valid Title")
    public void testCreateTodoWithValidTitle() {
        // Test Steps
        todoPage.createTodo("Buy groceries");

        // Verification
        assertTrue(todoPage.isTodoVisible("Buy groceries"), "Todo should be visible");
        assertTrue(todoPage.isTitleInputEmpty(), "Input field should be cleared");
        assertEquals(1, todoPage.getTotalCount(), "Total count should be 1");
        assertEquals(1, todoPage.getActiveCount(), "Active count should be 1");
        assertEquals(0, todoPage.getCompletedCount(), "Completed count should be 0");
        assertTrue(todoPage.isTodoActive("Buy groceries"), "Todo should be active (unchecked)");
    }

    @Test
    @DisplayName("TC_UI_002: Create Todo with Title and Description")
    public void testCreateTodoWithTitleAndDescription() {
        // Test Steps
        todoPage.createTodoWithDescription(
            "Complete project documentation",
            "Include API docs, user guide, and test cases"
        );

        // Verification
        assertTrue(todoPage.isTodoVisible("Complete project documentation"), 
            "Todo should be visible with title");
        assertTrue(todoPage.isTitleInputEmpty(), "Title input should be cleared");
        assertTrue(todoPage.isDescriptionInputEmpty(), "Description input should be cleared");
        assertEquals(1, todoPage.getTotalCount(), "Total count should be 1");
        assertEquals(1, todoPage.getActiveCount(), "Active count should be 1");
    }

    @Test
    @DisplayName("TC_UI_003: Create Todo with Empty Title - Negative Test")
    public void testCreateTodoWithEmptyTitle() {
        // Test Steps
        todoPage.enterTitle("");
        todoPage.clickAddTodo();

        // Verification
        String alertText = todoPage.getAlertText();
        assertNotNull(alertText, "Alert should appear");
        assertEquals("Please enter a todo title", alertText, "Alert message should match");
        
        todoPage.acceptAlert();
        
        assertEquals(0, todoPage.getTotalCount(), "No todo should be created");
        assertEquals(0, todoPage.getVisibleTodoCount(), "No todos should be visible");
    }

    @Test
    @DisplayName("TC_UI_004: Create Todo with Special Characters")
    public void testCreateTodoWithSpecialCharacters() {
        // Test Steps
        String specialTitle = "Buy coffee ☕ & tea 🍵";
        todoPage.createTodo(specialTitle);

        // Verification
        assertTrue(todoPage.isTodoVisible("Buy coffee"), 
            "Todo with special characters should be created");
        assertEquals(1, todoPage.getTotalCount(), "Total count should be 1");
    }

    @Test
    @DisplayName("TC_UI_EXTRA: Create Multiple Todos")
    public void testCreateMultipleTodos() {
        // Create 3 todos
        todoPage.createTodo("Task 1");
        todoPage.createTodo("Task 2");
        todoPage.createTodo("Task 3");

        // Verification
        assertEquals(3, todoPage.getVisibleTodoCount(), "Should have 3 visible todos");
        assertEquals(3, todoPage.getTotalCount(), "Total count should be 3");
        assertEquals(3, todoPage.getActiveCount(), "All should be active");
        
        assertTrue(todoPage.isTodoVisible("Task 1"), "Task 1 should be visible");
        assertTrue(todoPage.isTodoVisible("Task 2"), "Task 2 should be visible");
        assertTrue(todoPage.isTodoVisible("Task 3"), "Task 3 should be visible");
    }
}
