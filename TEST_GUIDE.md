# Unit Testing Guide

## Overview

This Todo App includes comprehensive unit tests for all layers:

- **Model Tests** - Entity validation and lifecycle methods
- **Repository Tests** - Database operations using @DataJpaTest
- **Service Tests** - Business logic with Mockito
- **Controller Tests** - REST API endpoints using MockMvc
- **Application Test** - Spring context loading

## Test Coverage

### 1. TodoTest (Model Layer)

- Todo creation and initialization
- Default values and constructors
- Lifecycle callbacks (@PrePersist, @PreUpdate)
- Field getters and setters

### 2. TodoRepositoryTest (Data Layer)

- CRUD operations
- Custom query methods
- Ordering and filtering
- Data persistence verification

### 3. TodoServiceTest (Service Layer)

- Business logic validation
- Error handling
- Toggle completion functionality
- Batch operations (delete completed)

### 4. TodoControllerTest (Controller Layer)

- HTTP endpoints testing
- Request/Response validation
- Status codes verification
- JSON serialization/deserialization

### 5. TodoApplicationTest

- Spring Boot context loading
- Application startup verification

## Running Tests

### Method 1: Run All Tests via Maven (Recommended)

```bash
# Windows
mvn clean test

# Or use the batch file
run-all-tests.bat

# Linux/Mac
./run-all-tests.sh
```

### Method 2: Run Specific Test Class

```bash
# Run only controller tests
mvn test -Dtest=TodoControllerTest

# Run only service tests
mvn test -Dtest=TodoServiceTest

# Run only repository tests
mvn test -Dtest=TodoRepositoryTest
```

### Method 3: Run Test Suite

```bash
# Run all tests using the test suite
mvn test -Dtest=AllTestsSuite
```

### Method 4: Via IDE (Eclipse/IntelliJ/VS Code)

**Option A: Run individual test class**

1. Navigate to any test class
2. Right-click on the class
3. Select "Run As" → "JUnit Test"

**Option B: Run all tests via Test Suite**

1. Navigate to `AllTestsSuite.java`
2. Right-click on the class
3. Select "Run As" → "JUnit Test"

**Option C: Run all tests in project**

1. Right-click on the project
2. Select "Run As" → "Maven test"

## Test Reports

After running tests, reports are generated in:

```
target/surefire-reports/
```

View the reports:

- **XML format**: `target/surefire-reports/TEST-*.xml`
- **Text format**: `target/surefire-reports/*.txt`
- **HTML format** (with maven-surefire-report-plugin): `target/site/surefire-report.html`

## Generate HTML Test Report

Add to pom.xml and run:

```bash
mvn surefire-report:report
```

Then open: `target/site/surefire-report.html`

## Test Configuration

Tests use separate configuration:

- **File**: `src/test/resources/application-test.properties`
- **Database**: H2 in-memory (separate from main app)
- **Auto-reset**: Database recreated for each test class

## Continuous Integration

To run tests in CI/CD pipeline:

```yaml
# Example for GitHub Actions
- name: Run tests
  run: mvn clean test

# Example for Jenkins
sh 'mvn clean test'
```

## Code Coverage (Optional)

To generate code coverage report with JaCoCo:

1. Add JaCoCo plugin to pom.xml:

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

2. Run tests with coverage:

```bash
mvn clean test jacoco:report
```

3. View report:

```
target/site/jacoco/index.html
```

## Best Practices

1. **Run tests before committing code**
2. **Keep tests independent** - Each test should run in isolation
3. **Use meaningful test names** - Test method names describe what they test
4. **Mock external dependencies** - Service tests mock repository layer
5. **Test edge cases** - Include tests for error conditions
6. **Maintain test data** - Use @BeforeEach for consistent test setup

## Test Statistics

Total Test Classes: **5**
Total Test Methods: **40+**

### Breakdown:

- Model Tests: 7 tests
- Repository Tests: 8 tests
- Service Tests: 12 tests
- Controller Tests: 13 tests
- Application Test: 1 test

## Troubleshooting

### Tests failing with database errors

- Check H2 dependency in pom.xml
- Verify test properties file exists
- Ensure @DataJpaTest is used for repository tests

### MockMvc tests failing

- Verify @WebMvcTest annotation
- Check @MockBean is used for dependencies
- Ensure proper MediaType in requests

### Tests not found

- Verify test classes end with "Test"
- Check tests are in src/test/java
- Run `mvn clean` before testing

## Quick Commands Reference

```bash
# Run all tests
mvn test

# Run tests with verbose output
mvn test -X

# Skip tests (not recommended)
mvn package -DskipTests

# Run specific test method
mvn test -Dtest=TodoServiceTest#testCreateTodo

# Run tests in parallel (faster)
mvn test -T 4

# Clean and test
mvn clean test
```
