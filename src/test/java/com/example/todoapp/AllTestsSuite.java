package com.example.todoapp;

import com.example.todoapp.controller.TodoControllerTest;
import com.example.todoapp.model.TodoTest;
import com.example.todoapp.repository.TodoRepositoryTest;
import com.example.todoapp.service.AuthServiceTest;
import com.example.todoapp.service.TodoServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test Suite to run all unit tests in one go.
 * 
 * Run this class to execute all tests:
 * - Model tests
 * - Repository tests
 * - Service tests
 * - Controller tests
 * - Application context test
 * 
 * Usage:
 * 1. Via IDE: Right-click on this class and select "Run As JUnit Test"
 * 2. Via Maven: mvn test -Dtest=AllTestsSuite
 * 3. Via Maven: mvn test (runs all tests)
 */
@Suite
@SuiteDisplayName("Todo App - All Tests Suite")
@SelectClasses({
        TodoTest.class,
        TodoRepositoryTest.class,
        AuthServiceTest.class,
        TodoServiceTest.class,
        TodoControllerTest.class,
        TodoApplicationTest.class
})
public class AllTestsSuite {
    // This class remains empty, used only as a holder for the above annotations
}
