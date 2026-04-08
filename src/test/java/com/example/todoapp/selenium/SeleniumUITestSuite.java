package com.example.todoapp.selenium;

import com.example.todoapp.selenium.tests.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Selenium UI Test Suite - Runs All UI Automation Tests
 * 
 * This test suite executes all Selenium-based UI tests for the Todo
 * Application.
 * It opens a browser, navigates to the application, and performs automated
 * testing
 * of all UI scenarios including:
 * - Todo Creation
 * - Todo Completion/Toggle
 * - Todo Filtering
 * - Todo Deletion
 * - Statistics Display
 * - End-to-End Workflows
 * 
 * Prerequisites:
 * 1. Application must be running on http://localhost:8080
 * 2. ChromeDriver will be automatically downloaded by WebDriverManager
 * 
 * To run all UI tests:
 * - Via Maven: mvn test -Dtest=SeleniumUITestSuite
 * - Via IDE: Right-click and select "Run As JUnit Test"
 * - Via script: run-selenium-tests.bat
 */
@Suite
@SuiteDisplayName("Todo App - Selenium UI Automation Test Suite")
@SelectClasses({
        TodoCreationTest.class,
        TodoCompletionTest.class,
        TodoFilteringTest.class,
        TodoDeletionTest.class,
        TodoStatisticsTest.class,
        TodoWorkflowTest.class
})
public class SeleniumUITestSuite {
    // This class remains empty
    // Used only as a holder for the above annotations
}
