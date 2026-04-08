# Selenium UI Testing Guide

## Overview

This guide explains how to run automated UI tests for the Todo Application using both **Selenium (Java)** and **Robot Framework (Python)**.

## Prerequisites

### For All Tests

- Application must be running on `http://localhost:8080`
- Google Chrome browser installed

### For Selenium Tests (Java)

- Java 17+
- Maven
- ChromeDriver (automatically downloaded by WebDriverManager)

### For Robot Framework Tests (Python)

- Python 3.7+
- Robot Framework and SeleniumLibrary (auto-installed by script)

## Test Framework Architecture

### Selenium Tests (Java)

**Page Object Model Structure:**

```
src/test/java/com/example/todoapp/selenium/
├── BaseSeleniumTest.java           # Base class with WebDriver setup
├── pages/
│   └── TodoPage.java               # Page Object with all UI elements and actions
├── tests/
│   ├── TodoCreationTest.java       # Tests for creating todos
│   ├── TodoCompletionTest.java     # Tests for toggling completion
│   ├── TodoFilteringTest.java      # Tests for filtering (All/Active/Completed)
│   ├── TodoDeletionTest.java       # Tests for deletion operations
│   ├── TodoStatisticsTest.java     # Tests for statistics counters
│   └── TodoWorkflowTest.java       # End-to-end workflow tests
└── SeleniumUITestSuite.java        # Test suite runner
```

**Key Features:**

- **Page Object Model**: All UI interactions encapsulated in `TodoPage.java`
- **WebDriverManager**: Automatic ChromeDriver download and setup
- **Waits**: Implicit and explicit waits for reliable element interaction
- **Assertions**: JUnit 5 assertions for comprehensive validation

### Robot Framework Tests (Python)

**Test File:**

```
todo_robot_tests.robot              # All Robot Framework test cases
```

**Key Features:**

- **Keyword-driven testing**: Reusable keywords for common actions
- **HTML Reports**: Automatic generation of detailed test reports
- **Easy to read**: Natural language-like syntax
- **SeleniumLibrary**: Built-in Selenium integration

## Running Tests

### Option 1: Selenium Tests (Java)

#### Method 1: Using Batch Script (Recommended)

```bash
# Windows
run-selenium-tests.bat

# Linux/Mac
chmod +x run-selenium-tests.sh
./run-selenium-tests.sh
```

#### Method 2: Using Maven

```bash
# Run all Selenium UI tests
mvn test -Dtest=SeleniumUITestSuite

# Run specific test class
mvn test -Dtest=TodoCreationTest

# Run in headless mode (add to BaseSeleniumTest.java)
mvn test -Dtest=SeleniumUITestSuite -Dheadless=true
```

#### Method 3: Using IDE

1. Open `SeleniumUITestSuite.java` in your IDE
2. Right-click → Run As → JUnit Test
3. Or run individual test classes

### Option 2: Robot Framework Tests (Python)

#### Method 1: Using Batch Script (Recommended)

```bash
# Windows
run-robot-tests.bat

# Linux/Mac
chmod +x run-robot-tests.sh
./run-robot-tests.sh
```

#### Method 2: Using Robot Command

```bash
# Install dependencies (first time only)
pip install robotframework
pip install robotframework-seleniumlibrary

# Run all tests
robot --outputdir robot-results todo_robot_tests.robot

# Run specific test
robot --test "RF_TC_001" todo_robot_tests.robot

# Run tests with specific tag
robot --include smoke todo_robot_tests.robot
```

## Test Coverage

### UI Test Scenarios Covered

#### 1. Todo Creation (5 tests)

- ✅ Create todo with valid title
- ✅ Create todo with title and description
- ✅ Validate empty title prevention
- ✅ Create todo with special characters
- ✅ Create multiple todos

#### 2. Todo Completion (4 tests)

- ✅ Mark todo as completed
- ✅ Uncheck completed todo
- ✅ Toggle todo multiple times
- ✅ Toggle multiple todos

#### 3. Todo Filtering (6 tests)

- ✅ Filter all todos
- ✅ Filter active todos
- ✅ Filter completed todos
- ✅ Filter with no results
- ✅ Switch between filters
- ✅ Create todo while filtered

#### 4. Todo Deletion (5 tests)

- ✅ Delete single todo
- ✅ Cancel deletion
- ✅ Clear all completed todos
- ✅ Clear completed with no completed todos
- ✅ Delete multiple todos one by one

#### 5. Statistics Display (5 tests)

- ✅ Verify total count updates
- ✅ Verify active count updates
- ✅ Verify completed count updates
- ✅ Real-time statistics updates
- ✅ Statistics after bulk operations

#### 6. End-to-End Workflows (5 tests)

- ✅ Complete todo lifecycle (CRUD)
- ✅ Managing multiple todos
- ✅ Rapid toggle operations
- ✅ Create, filter, and delete workflow
- ✅ Data persistence after page refresh

**Total: 30+ Selenium Tests + 10 Robot Framework Tests**

## Test Reports

### Selenium Test Reports

After running Selenium tests, view results in:

- Console output
- IDE test results panel
- Maven Surefire reports: `target/surefire-reports/`

### Robot Framework Reports

After running Robot tests, view detailed HTML reports:

- **Report**: `robot-results/report.html` - High-level test summary
- **Log**: `robot-results/log.html` - Detailed execution log with screenshots
- **Output**: `robot-results/output.xml` - Machine-readable results

## Troubleshooting

### Common Issues

#### 1. Application Not Running

**Error**: "Application is not running on http://localhost:8080"

**Solution**:

```bash
# Start the application first
mvn spring-boot:run

# Or run as Spring Boot App from IDE
```

#### 2. ChromeDriver Not Found

**Error**: "WebDriver executable not found"

**Solution**: WebDriverManager handles this automatically. If issues persist:

```bash
# Update dependencies
mvn clean install

# Or download ChromeDriver manually
# https://chromedriver.chromium.org/
```

#### 3. Element Not Found

**Error**: "NoSuchElementException" or "Element not found"

**Possible Causes**:

- Page not fully loaded
- Wrong locator
- Element hidden by filter

**Solution**:

- Check implicit wait settings in `BaseSeleniumTest`
- Verify element locators in `TodoPage.java`
- Ensure correct filter is active

#### 4. Robot Framework Not Installed

**Error**: "robot is not recognized as an internal or external command"

**Solution**:

```bash
# Install Python and Robot Framework
pip install robotframework
pip install robotframework-seleniumlibrary
```

#### 5. Tests Fail After UI Changes

**Solution**: Update element locators in:

- Selenium: `TodoPage.java`
- Robot: `todo_robot_tests.robot` variables section

### Debug Mode

#### Selenium Tests

Add breakpoints in test methods and run in debug mode from IDE.

#### Robot Framework Tests

```bash
# Run with verbose output
robot --loglevel DEBUG todo_robot_tests.robot

# Run with screenshots on failure
robot --listener RetryFailed todo_robot_tests.robot
```

## Best Practices

### 1. Start Application Before Testing

Always ensure the application is running:

```bash
mvn spring-boot:run
```

### 2. Run Tests in Sequence

For stable results, run tests one suite at a time.

### 3. Clean Test Data

Each test starts with a clean state (no existing todos).

### 4. Headless Mode for CI/CD

For continuous integration, enable headless mode in `BaseSeleniumTest.java`:

```java
options.addArguments("--headless");
```

### 5. Parallel Execution (Advanced)

Enable parallel test execution in `pom.xml`:

```xml
<configuration>
    <parallel>classes</parallel>
    <threadCount>4</threadCount>
</configuration>
```

## Integration with CI/CD

### Maven Profile for UI Tests

Add to `pom.xml`:

```xml
<profile>
    <id>ui-tests</id>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <includes>
                        <include>**/selenium/**/*Test.java</include>
                    </includes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</profile>
```

Run with:

```bash
mvn test -Pui-tests
```

## Next Steps

1. **Expand Test Coverage**: Add more edge cases and negative tests
2. **Add API Tests**: Combine with REST API testing
3. **Performance Testing**: Measure page load and operation times
4. **Cross-Browser Testing**: Test on Firefox, Edge, Safari
5. **Mobile Testing**: Add responsive design tests
6. **Screenshot Comparison**: Add visual regression testing

## Support

For issues or questions:

1. Check console output for error details
2. Review test reports (HTML for Robot, console for Selenium)
3. Verify application is running and accessible
4. Check browser compatibility (Chrome recommended)
