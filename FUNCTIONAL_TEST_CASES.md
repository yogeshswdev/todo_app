# Functional Test Cases - Todo Application

## Document Information

| **Project Name** | Todo Application - Spring Boot |
| ---------------- | ------------------------------ |
| **Version**      | 1.0                            |
| **Test Type**    | Functional Testing             |
| **Date**         | November 18, 2025              |
| **Author**       | QA Team                        |
| **Environment**  | Development/Testing            |

---

## Table of Contents

1. [Introduction](#introduction)
2. [Test Environment](#test-environment)
3. [Test Case Template](#test-case-template)
4. [User Interface Test Cases](#user-interface-test-cases)
5. [API Functional Test Cases](#api-functional-test-cases)
6. [Business Logic Test Cases](#business-logic-test-cases)
7. [Data Validation Test Cases](#data-validation-test-cases)
8. [Workflow Test Cases](#workflow-test-cases)
9. [Test Execution Summary](#test-execution-summary)

---

## Introduction

### Purpose

This document contains detailed functional test cases for the Todo Application. The purpose is to verify that all features and functionalities work as per requirements.

### Scope

- User Interface functionality
- REST API operations
- Business logic validation
- Data persistence and retrieval
- User workflows

### Out of Scope

- Performance testing
- Security testing
- Load testing
- Browser compatibility (covered in separate document)

---

## Test Environment

### Application Details

- **Application URL**: http://localhost:8080
- **API Base URL**: http://localhost:8080/api/todos
- **Database**: H2 In-Memory Database
- **Backend**: Spring Boot 3.2.0, Java 17
- **Frontend**: HTML5, CSS3, JavaScript

### Test Data

- Clean database before each test execution
- Predefined test data available in test fixtures

---

## Test Case Template

Each test case follows this structure:

- **Test Case ID**: Unique identifier
- **Test Case Name**: Descriptive name
- **Module**: Feature/Module being tested
- **Priority**: Critical/High/Medium/Low
- **Pre-conditions**: Setup required before test
- **Test Steps**: Detailed steps to execute
- **Test Data**: Input data required
- **Expected Result**: Expected outcome
- **Actual Result**: To be filled during execution
- **Status**: Pass/Fail/Blocked/Not Executed
- **Remarks**: Additional notes

---

## User Interface Test Cases

### Module: Todo Creation

#### TC_UI_001: Create Todo with Valid Title

| **Field**          | **Details**                          |
| ------------------ | ------------------------------------ |
| **Test Case ID**   | TC_UI_001                            |
| **Test Case Name** | Create Todo with Valid Title         |
| **Module**         | Todo Creation                        |
| **Priority**       | Critical                             |
| **Pre-conditions** | Application is loaded and accessible |

**Test Steps:**

1. Open the application in browser (http://localhost:8080)
2. Locate the "What needs to be done?" input field
3. Enter "Buy groceries" in the title field
4. Click the "Add Todo" button

**Test Data:**

- Title: "Buy groceries"

**Expected Result:**

- Todo is created successfully
- New todo appears in the todo list
- Input field is cleared
- Total count increases by 1
- Active count increases by 1
- Todo appears unchecked

**Actual Result:** _[To be filled during execution]_

**Status:** ⬜ Not Executed

---

#### TC_UI_002: Create Todo with Title and Description

| **Field**          | **Details**                            |
| ------------------ | -------------------------------------- |
| **Test Case ID**   | TC_UI_002                              |
| **Test Case Name** | Create Todo with Title and Description |
| **Module**         | Todo Creation                          |
| **Priority**       | High                                   |
| **Pre-conditions** | Application is loaded                  |

**Test Steps:**

1. Navigate to the application
2. Enter "Complete project documentation" in the title field
3. Enter "Include API docs, user guide, and test cases" in the description field
4. Click "Add Todo" button

**Test Data:**

- Title: "Complete project documentation"
- Description: "Include API docs, user guide, and test cases"

**Expected Result:**

- Todo created with both title and description
- Description is visible in the todo item
- Todo appears in the list
- Statistics updated correctly

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_003: Create Todo with Empty Title

| **Field**          | **Details**                                  |
| ------------------ | -------------------------------------------- |
| **Test Case ID**   | TC_UI_003                                    |
| **Test Case Name** | Create Todo with Empty Title - Negative Test |
| **Module**         | Todo Creation                                |
| **Priority**       | High                                         |
| **Pre-conditions** | Application is loaded                        |

**Test Steps:**

1. Navigate to the application
2. Leave the title field empty
3. Click "Add Todo" button

**Test Data:**

- Title: "" (empty string)

**Expected Result:**

- Alert message: "Please enter a todo title"
- Todo is NOT created
- No changes to todo list
- Statistics remain unchanged

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_004: Create Todo with Special Characters

| **Field**          | **Details**                         |
| ------------------ | ----------------------------------- |
| **Test Case ID**   | TC_UI_004                           |
| **Test Case Name** | Create Todo with Special Characters |
| **Module**         | Todo Creation                       |
| **Priority**       | Medium                              |
| **Pre-conditions** | Application is loaded               |

**Test Steps:**

1. Navigate to the application
2. Enter "Buy coffee ☕ & tea 🍵" in title field
3. Click "Add Todo" button

**Test Data:**

- Title: "Buy coffee ☕ & tea 🍵"

**Expected Result:**

- Todo created successfully
- Special characters and emojis display correctly
- No encoding issues

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

### Module: Todo Completion

#### TC_UI_005: Mark Todo as Completed

| **Field**          | **Details**                                   |
| ------------------ | --------------------------------------------- |
| **Test Case ID**   | TC_UI_005                                     |
| **Test Case Name** | Mark Todo as Completed                        |
| **Module**         | Todo Completion                               |
| **Priority**       | Critical                                      |
| **Pre-conditions** | At least one active (uncompleted) todo exists |

**Test Steps:**

1. Identify an active todo in the list
2. Click the checkbox next to the todo item
3. Observe the visual changes

**Test Data:**

- Existing todo with completed = false

**Expected Result:**

- Checkbox becomes checked
- Todo title shows strikethrough
- Todo background becomes slightly faded
- Active count decreases by 1
- Completed count increases by 1
- Total count remains same

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_006: Mark Completed Todo as Active

| **Field**          | **Details**                        |
| ------------------ | ---------------------------------- |
| **Test Case ID**   | TC_UI_006                          |
| **Test Case Name** | Uncheck Completed Todo             |
| **Module**         | Todo Completion                    |
| **Priority**       | Critical                           |
| **Pre-conditions** | At least one completed todo exists |

**Test Steps:**

1. Identify a completed todo (with checkbox checked)
2. Click the checkbox to uncheck it
3. Observe the changes

**Test Data:**

- Existing todo with completed = true

**Expected Result:**

- Checkbox becomes unchecked
- Strikethrough removed from title
- Todo background returns to normal
- Active count increases by 1
- Completed count decreases by 1
- Total count remains same

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_007: Toggle Todo Multiple Times

| **Field**          | **Details**                           |
| ------------------ | ------------------------------------- |
| **Test Case ID**   | TC_UI_007                             |
| **Test Case Name** | Toggle Todo Completion Multiple Times |
| **Module**         | Todo Completion                       |
| **Priority**       | Medium                                |
| **Pre-conditions** | One todo exists                       |

**Test Steps:**

1. Click checkbox to mark as completed
2. Click checkbox again to mark as active
3. Click checkbox once more to mark as completed
4. Refresh the page
5. Verify the final state

**Test Data:**

- Any existing todo

**Expected Result:**

- Each click toggles the state correctly
- Visual feedback is immediate
- Final state (completed) persists after page refresh
- Statistics update correctly after each toggle

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

### Module: Todo Filtering

#### TC_UI_008: Filter All Todos

| **Field**          | **Details**                                      |
| ------------------ | ------------------------------------------------ |
| **Test Case ID**   | TC_UI_008                                        |
| **Test Case Name** | Display All Todos                                |
| **Module**         | Todo Filtering                                   |
| **Priority**       | High                                             |
| **Pre-conditions** | Multiple todos exist (both completed and active) |

**Test Steps:**

1. Create 2 active todos
2. Create 2 completed todos
3. Click "All" filter button
4. Count the visible todos

**Test Data:**

- 2 Active todos
- 2 Completed todos

**Expected Result:**

- All 4 todos are visible
- "All" button is highlighted/active
- Both completed and active todos shown
- Total count shows 4

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_009: Filter Active Todos

| **Field**          | **Details**                             |
| ------------------ | --------------------------------------- |
| **Test Case ID**   | TC_UI_009                               |
| **Test Case Name** | Display Only Active Todos               |
| **Module**         | Todo Filtering                          |
| **Priority**       | High                                    |
| **Pre-conditions** | Mix of active and completed todos exist |

**Test Steps:**

1. Ensure database has 3 active and 2 completed todos
2. Click "Active" filter button
3. Verify displayed todos

**Test Data:**

- 3 Active todos
- 2 Completed todos

**Expected Result:**

- Only 3 active todos visible
- Completed todos are hidden
- "Active" button is highlighted
- Active count shows 3
- All displayed todos have unchecked checkboxes

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_010: Filter Completed Todos

| **Field**          | **Details**                             |
| ------------------ | --------------------------------------- |
| **Test Case ID**   | TC_UI_010                               |
| **Test Case Name** | Display Only Completed Todos            |
| **Module**         | Todo Filtering                          |
| **Priority**       | High                                    |
| **Pre-conditions** | Mix of active and completed todos exist |

**Test Steps:**

1. Ensure database has 3 active and 2 completed todos
2. Click "Completed" filter button
3. Verify displayed todos

**Test Data:**

- 3 Active todos
- 2 Completed todos

**Expected Result:**

- Only 2 completed todos visible
- Active todos are hidden
- "Completed" button is highlighted
- Completed count shows 2
- All displayed todos have checked checkboxes

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_011: Filter with No Results

| **Field**          | **Details**                                  |
| ------------------ | -------------------------------------------- |
| **Test Case ID**   | TC_UI_011                                    |
| **Test Case Name** | Filter Shows Empty State                     |
| **Module**         | Todo Filtering                               |
| **Priority**       | Medium                                       |
| **Pre-conditions** | Only active todos exist (no completed todos) |

**Test Steps:**

1. Create 3 active todos (do not complete any)
2. Click "Completed" filter button
3. Observe the display

**Test Data:**

- 3 Active todos only

**Expected Result:**

- Empty state message displayed: "No todos found!"
- Sub-message: "No completed todos"
- Icon/illustration shown
- No todo items in the list

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

### Module: Todo Deletion

#### TC_UI_012: Delete Single Todo

| **Field**          | **Details**              |
| ------------------ | ------------------------ |
| **Test Case ID**   | TC_UI_012                |
| **Test Case Name** | Delete Individual Todo   |
| **Module**         | Todo Deletion            |
| **Priority**       | Critical                 |
| **Pre-conditions** | At least one todo exists |

**Test Steps:**

1. Note the total count of todos
2. Click "Delete" button on a specific todo
3. Click "OK" on confirmation dialog
4. Verify the todo is removed

**Test Data:**

- Any existing todo

**Expected Result:**

- Confirmation dialog appears: "Are you sure you want to delete this todo?"
- After clicking OK, todo is removed from the list
- Total count decreases by 1
- Appropriate counter (active or completed) decreases
- Change persists after page refresh

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_013: Cancel Todo Deletion

| **Field**          | **Details**               |
| ------------------ | ------------------------- |
| **Test Case ID**   | TC_UI_013                 |
| **Test Case Name** | Cancel Deletion Operation |
| **Module**         | Todo Deletion             |
| **Priority**       | Medium                    |
| **Pre-conditions** | At least one todo exists  |

**Test Steps:**

1. Note the current todo count
2. Click "Delete" button on a todo
3. Click "Cancel" on confirmation dialog
4. Verify todo still exists

**Test Data:**

- Any existing todo

**Expected Result:**

- Confirmation dialog appears
- After clicking Cancel, dialog closes
- Todo remains in the list
- No changes to statistics
- No data modified

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_014: Clear All Completed Todos

| **Field**          | **Details**                    |
| ------------------ | ------------------------------ |
| **Test Case ID**   | TC_UI_014                      |
| **Test Case Name** | Bulk Delete Completed Todos    |
| **Module**         | Todo Deletion                  |
| **Priority**       | High                           |
| **Pre-conditions** | Multiple completed todos exist |

**Test Steps:**

1. Create and complete 3 todos
2. Create 2 active todos
3. Click "Clear Completed" button
4. Confirm the deletion
5. Verify the results

**Test Data:**

- 3 Completed todos
- 2 Active todos

**Expected Result:**

- Confirmation dialog appears
- All 3 completed todos are removed
- 2 active todos remain
- Completed count becomes 0
- Active count remains 2
- Total count becomes 2

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_015: Clear Completed with No Completed Todos

| **Field**          | **Details**                     |
| ------------------ | ------------------------------- |
| **Test Case ID**   | TC_UI_015                       |
| **Test Case Name** | Clear Completed When None Exist |
| **Module**         | Todo Deletion                   |
| **Priority**       | Low                             |
| **Pre-conditions** | Only active todos exist         |

**Test Steps:**

1. Create 2 active todos
2. Ensure no completed todos exist
3. Click "Clear Completed" button

**Test Data:**

- 2 Active todos only

**Expected Result:**

- Confirmation dialog appears
- After confirmation, no todos are deleted
- Active todos remain unchanged
- Statistics unchanged

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

### Module: Statistics Display

#### TC_UI_016: Verify Total Count

| **Field**          | **Details**           |
| ------------------ | --------------------- |
| **Test Case ID**   | TC_UI_016             |
| **Test Case Name** | Total Count Accuracy  |
| **Module**         | Statistics            |
| **Priority**       | High                  |
| **Pre-conditions** | Application is loaded |

**Test Steps:**

1. Note the initial total count (should be 0)
2. Add 5 todos
3. Complete 2 todos
4. Delete 1 active todo
5. Verify total count at each step

**Test Data:**

- 5 New todos

**Expected Result:**

- Initial: Total = 0
- After adding 5: Total = 5
- After completing 2: Total = 5
- After deleting 1: Total = 4
- Count updates in real-time

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_017: Verify Active Count

| **Field**          | **Details**           |
| ------------------ | --------------------- |
| **Test Case ID**   | TC_UI_017             |
| **Test Case Name** | Active Count Accuracy |
| **Module**         | Statistics            |
| **Priority**       | High                  |
| **Pre-conditions** | Application is loaded |

**Test Steps:**

1. Add 5 todos (all active)
2. Mark 2 as completed
3. Uncheck 1 completed todo
4. Delete 1 active todo
5. Verify active count at each step

**Test Data:**

- 5 New todos

**Expected Result:**

- After adding 5: Active = 5
- After completing 2: Active = 3
- After unchecking 1: Active = 4
- After deleting 1 active: Active = 3

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_018: Verify Completed Count

| **Field**          | **Details**              |
| ------------------ | ------------------------ |
| **Test Case ID**   | TC_UI_018                |
| **Test Case Name** | Completed Count Accuracy |
| **Module**         | Statistics               |
| **Priority**       | High                     |
| **Pre-conditions** | Application is loaded    |

**Test Steps:**

1. Add 5 todos
2. Complete 3 todos
3. Uncheck 1 completed todo
4. Delete 1 completed todo
5. Verify completed count at each step

**Test Data:**

- 5 New todos

**Expected Result:**

- Initially: Completed = 0
- After completing 3: Completed = 3
- After unchecking 1: Completed = 2
- After deleting 1 completed: Completed = 1

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

### Module: Data Persistence

#### TC_UI_019: Data Persists After Page Refresh

| **Field**          | **Details**                        |
| ------------------ | ---------------------------------- |
| **Test Case ID**   | TC_UI_019                          |
| **Test Case Name** | Verify Data Persistence on Refresh |
| **Module**         | Data Persistence                   |
| **Priority**       | Critical                           |
| **Pre-conditions** | Application is running             |

**Test Steps:**

1. Add 3 todos
2. Complete 1 todo
3. Note all todo titles and states
4. Refresh the browser (F5)
5. Verify all todos are still present

**Test Data:**

- Todo 1: "Task A" (Active)
- Todo 2: "Task B" (Completed)
- Todo 3: "Task C" (Active)

**Expected Result:**

- All 3 todos appear after refresh
- Completion states preserved
- Order maintained
- Statistics accurate
- No data loss

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_UI_020: Data Persists After Browser Close/Reopen

| **Field**          | **Details**                             |
| ------------------ | --------------------------------------- |
| **Test Case ID**   | TC_UI_020                               |
| **Test Case Name** | Verify Data Persistence Across Sessions |
| **Module**         | Data Persistence                        |
| **Priority**       | High                                    |
| **Pre-conditions** | Application is running                  |

**Test Steps:**

1. Add 2 todos and complete 1
2. Note the todo details
3. Close the browser completely
4. Reopen browser and navigate to application
5. Verify todos are present

**Test Data:**

- Todo 1: "Persistent Task 1" (Active)
- Todo 2: "Persistent Task 2" (Completed)

**Expected Result:**

- Both todos appear after reopening
- Completion states maintained
- H2 database retains data (if configured for file-based storage)

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed
**Note:** _May fail with H2 in-memory DB (expected behavior)_

---

## API Functional Test Cases

### Module: GET Endpoints

#### TC_API_001: Get All Todos - Success

| **Field**          | **Details**                |
| ------------------ | -------------------------- |
| **Test Case ID**   | TC_API_001                 |
| **Test Case Name** | Retrieve All Todos via API |
| **Module**         | API - GET                  |
| **Priority**       | Critical                   |
| **Pre-conditions** | Database has sample todos  |

**Test Steps:**

1. Send GET request to http://localhost:8080/api/todos
2. Verify response status code
3. Verify response body structure
4. Validate data types

**Test Data:**

- Endpoint: GET /api/todos

**Expected Result:**

- HTTP Status: 200 OK
- Response: JSON array of todo objects
- Each object contains: id, title, description, completed, createdAt, updatedAt
- Content-Type: application/json

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

**Sample Response:**

```json
[
  {
    "id": 1,
    "title": "Sample Todo",
    "description": "Description here",
    "completed": false,
    "createdAt": "2025-11-18T10:30:00",
    "updatedAt": "2025-11-18T10:30:00"
  }
]
```

---

#### TC_API_002: Get Todo by ID - Valid ID

| **Field**          | **Details**                  |
| ------------------ | ---------------------------- |
| **Test Case ID**   | TC_API_002                   |
| **Test Case Name** | Retrieve Specific Todo by ID |
| **Module**         | API - GET                    |
| **Priority**       | Critical                     |
| **Pre-conditions** | Todo with ID=1 exists        |

**Test Steps:**

1. Send GET request to http://localhost:8080/api/todos/1
2. Verify response

**Test Data:**

- Endpoint: GET /api/todos/1

**Expected Result:**

- HTTP Status: 200 OK
- Response: Single todo object with id=1
- All fields populated correctly

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_003: Get Todo by ID - Invalid ID

| **Field**          | **Details**                      |
| ------------------ | -------------------------------- |
| **Test Case ID**   | TC_API_003                       |
| **Test Case Name** | Request Non-Existent Todo        |
| **Module**         | API - GET                        |
| **Priority**       | High                             |
| **Pre-conditions** | Todo with ID=9999 does not exist |

**Test Steps:**

1. Send GET request to http://localhost:8080/api/todos/9999
2. Verify error response

**Test Data:**

- Endpoint: GET /api/todos/9999

**Expected Result:**

- HTTP Status: 404 Not Found
- Response body: Empty or error message

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_004: Get Active Todos

| **Field**          | **Details**                             |
| ------------------ | --------------------------------------- |
| **Test Case ID**   | TC_API_004                              |
| **Test Case Name** | Filter Todos by Active Status           |
| **Module**         | API - GET                               |
| **Priority**       | High                                    |
| **Pre-conditions** | Mix of active and completed todos exist |

**Test Steps:**

1. Send GET request to http://localhost:8080/api/todos/status/false
2. Verify all returned todos have completed=false

**Test Data:**

- Endpoint: GET /api/todos/status/false

**Expected Result:**

- HTTP Status: 200 OK
- Response: Array of todos
- All todos have "completed": false
- Completed todos not included

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_005: Get Completed Todos

| **Field**          | **Details**                             |
| ------------------ | --------------------------------------- |
| **Test Case ID**   | TC_API_005                              |
| **Test Case Name** | Filter Todos by Completed Status        |
| **Module**         | API - GET                               |
| **Priority**       | High                                    |
| **Pre-conditions** | Mix of active and completed todos exist |

**Test Steps:**

1. Send GET request to http://localhost:8080/api/todos/status/true
2. Verify all returned todos have completed=true

**Test Data:**

- Endpoint: GET /api/todos/status/true

**Expected Result:**

- HTTP Status: 200 OK
- Response: Array of todos
- All todos have "completed": true
- Active todos not included

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

### Module: POST Endpoints

#### TC_API_006: Create Todo - Valid Data

| **Field**          | **Details**             |
| ------------------ | ----------------------- |
| **Test Case ID**   | TC_API_006              |
| **Test Case Name** | Create New Todo via API |
| **Module**         | API - POST              |
| **Priority**       | Critical                |
| **Pre-conditions** | None                    |

**Test Steps:**

1. Send POST request to http://localhost:8080/api/todos
2. Include valid JSON body
3. Verify response

**Test Data:**

```json
{
  "title": "API Created Todo",
  "description": "Created via REST API",
  "completed": false
}
```

**Expected Result:**

- HTTP Status: 201 Created
- Response: Created todo object with generated ID
- Timestamps auto-populated
- Todo retrievable via GET

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_007: Create Todo - Without Description

| **Field**          | **Details**                 |
| ------------------ | --------------------------- |
| **Test Case ID**   | TC_API_007                  |
| **Test Case Name** | Create Todo with Only Title |
| **Module**         | API - POST                  |
| **Priority**       | High                        |
| **Pre-conditions** | None                        |

**Test Steps:**

1. Send POST request with only title field
2. Verify response

**Test Data:**

```json
{
  "title": "Simple Todo",
  "completed": false
}
```

**Expected Result:**

- HTTP Status: 201 Created
- Todo created with null/empty description
- Other fields populated

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_008: Create Todo - Missing Required Field

| **Field**          | **Details**                          |
| ------------------ | ------------------------------------ |
| **Test Case ID**   | TC_API_008                           |
| **Test Case Name** | Create Todo Without Title - Negative |
| **Module**         | API - POST                           |
| **Priority**       | High                                 |
| **Pre-conditions** | None                                 |

**Test Steps:**

1. Send POST request without title field
2. Verify error response

**Test Data:**

```json
{
  "description": "Only description",
  "completed": false
}
```

**Expected Result:**

- HTTP Status: 400 Bad Request (if validation exists)
- Or 500 Internal Server Error
- Error message indicating missing title

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_009: Create Todo - Invalid JSON

| **Field**          | **Details**                     |
| ------------------ | ------------------------------- |
| **Test Case ID**   | TC_API_009                      |
| **Test Case Name** | Create Todo with Malformed JSON |
| **Module**         | API - POST                      |
| **Priority**       | Medium                          |
| **Pre-conditions** | None                            |

**Test Steps:**

1. Send POST request with invalid JSON
2. Verify error response

**Test Data:**

```
{
  "title": "Invalid JSON"
  "completed": false
}
```

(Missing comma)

**Expected Result:**

- HTTP Status: 400 Bad Request
- Error message about JSON parsing

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

### Module: PUT Endpoints

#### TC_API_010: Update Todo - Valid Data

| **Field**          | **Details**           |
| ------------------ | --------------------- |
| **Test Case ID**   | TC_API_010            |
| **Test Case Name** | Update Existing Todo  |
| **Module**         | API - PUT             |
| **Priority**       | Critical              |
| **Pre-conditions** | Todo with ID=1 exists |

**Test Steps:**

1. Send PUT request to http://localhost:8080/api/todos/1
2. Include updated data
3. Verify response
4. Verify via GET request

**Test Data:**

```json
{
  "title": "Updated Title",
  "description": "Updated Description",
  "completed": true
}
```

**Expected Result:**

- HTTP Status: 200 OK
- Response: Updated todo object
- updatedAt timestamp changed
- createdAt unchanged
- Changes persisted

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_011: Update Todo - Non-Existent ID

| **Field**          | **Details**                      |
| ------------------ | -------------------------------- |
| **Test Case ID**   | TC_API_011                       |
| **Test Case Name** | Update Non-Existent Todo         |
| **Module**         | API - PUT                        |
| **Priority**       | High                             |
| **Pre-conditions** | Todo with ID=9999 does not exist |

**Test Steps:**

1. Send PUT request to http://localhost:8080/api/todos/9999
2. Include valid update data
3. Verify error response

**Test Data:**

- ID: 9999
- Valid JSON body

**Expected Result:**

- HTTP Status: 404 Not Found
- Error message indicating todo not found

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

### Module: PATCH Endpoints

#### TC_API_012: Toggle Todo Completion - Active to Completed

| **Field**          | **Details**                                    |
| ------------------ | ---------------------------------------------- |
| **Test Case ID**   | TC_API_012                                     |
| **Test Case Name** | Toggle Active Todo to Completed                |
| **Module**         | API - PATCH                                    |
| **Priority**       | Critical                                       |
| **Pre-conditions** | Active todo with ID=1 exists (completed=false) |

**Test Steps:**

1. Verify todo is active via GET
2. Send PATCH to http://localhost:8080/api/todos/1/toggle
3. Verify response
4. Confirm via GET that completed=true

**Test Data:**

- Todo ID: 1 (completed=false)

**Expected Result:**

- HTTP Status: 200 OK
- Response: Todo with completed=true
- updatedAt timestamp changed

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_013: Toggle Todo Completion - Completed to Active

| **Field**          | **Details**                                      |
| ------------------ | ------------------------------------------------ |
| **Test Case ID**   | TC_API_013                                       |
| **Test Case Name** | Toggle Completed Todo to Active                  |
| **Module**         | API - PATCH                                      |
| **Priority**       | Critical                                         |
| **Pre-conditions** | Completed todo with ID=1 exists (completed=true) |

**Test Steps:**

1. Verify todo is completed via GET
2. Send PATCH to http://localhost:8080/api/todos/1/toggle
3. Verify response shows completed=false

**Test Data:**

- Todo ID: 1 (completed=true)

**Expected Result:**

- HTTP Status: 200 OK
- Response: Todo with completed=false
- updatedAt timestamp changed

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_014: Toggle Non-Existent Todo

| **Field**          | **Details**                      |
| ------------------ | -------------------------------- |
| **Test Case ID**   | TC_API_014                       |
| **Test Case Name** | Toggle Todo That Doesn't Exist   |
| **Module**         | API - PATCH                      |
| **Priority**       | High                             |
| **Pre-conditions** | Todo with ID=9999 does not exist |

**Test Steps:**

1. Send PATCH to http://localhost:8080/api/todos/9999/toggle
2. Verify error response

**Test Data:**

- Todo ID: 9999

**Expected Result:**

- HTTP Status: 404 Not Found
- Error message

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

### Module: DELETE Endpoints

#### TC_API_015: Delete Todo - Valid ID

| **Field**          | **Details**           |
| ------------------ | --------------------- |
| **Test Case ID**   | TC_API_015            |
| **Test Case Name** | Delete Existing Todo  |
| **Module**         | API - DELETE          |
| **Priority**       | Critical              |
| **Pre-conditions** | Todo with ID=1 exists |

**Test Steps:**

1. Verify todo exists via GET
2. Send DELETE to http://localhost:8080/api/todos/1
3. Verify response
4. Confirm deletion via GET (should return 404)

**Test Data:**

- Todo ID: 1

**Expected Result:**

- HTTP Status: 204 No Content
- Empty response body
- Subsequent GET returns 404

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_016: Delete Todo - Invalid ID

| **Field**          | **Details**                      |
| ------------------ | -------------------------------- |
| **Test Case ID**   | TC_API_016                       |
| **Test Case Name** | Delete Non-Existent Todo         |
| **Module**         | API - DELETE                     |
| **Priority**       | High                             |
| **Pre-conditions** | Todo with ID=9999 does not exist |

**Test Steps:**

1. Send DELETE to http://localhost:8080/api/todos/9999
2. Verify error response

**Test Data:**

- Todo ID: 9999

**Expected Result:**

- HTTP Status: 404 Not Found
- Error message

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_017: Delete All Completed Todos

| **Field**          | **Details**                    |
| ------------------ | ------------------------------ |
| **Test Case ID**   | TC_API_017                     |
| **Test Case Name** | Bulk Delete Completed Todos    |
| **Module**         | API - DELETE                   |
| **Priority**       | High                           |
| **Pre-conditions** | Multiple completed todos exist |

**Test Steps:**

1. Create 3 completed and 2 active todos
2. Note total count
3. Send DELETE to http://localhost:8080/api/todos/completed
4. Verify response
5. Confirm via GET that only active todos remain

**Test Data:**

- 3 Completed todos
- 2 Active todos

**Expected Result:**

- HTTP Status: 204 No Content
- All completed todos deleted
- Active todos unchanged
- GET /api/todos returns only 2 todos

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_API_018: Delete Completed When None Exist

| **Field**          | **Details**                              |
| ------------------ | ---------------------------------------- |
| **Test Case ID**   | TC_API_018                               |
| **Test Case Name** | Delete Completed With No Completed Todos |
| **Module**         | API - DELETE                             |
| **Priority**       | Medium                                   |
| **Pre-conditions** | Only active todos exist                  |

**Test Steps:**

1. Ensure no completed todos exist
2. Send DELETE to /api/todos/completed
3. Verify response

**Test Data:**

- 0 Completed todos
- 2 Active todos

**Expected Result:**

- HTTP Status: 204 No Content
- No todos deleted
- Active todos remain

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

## Business Logic Test Cases

### Module: Todo State Management

#### TC_BL_001: Default Completion Status

| **Field**          | **Details**                    |
| ------------------ | ------------------------------ |
| **Test Case ID**   | TC_BL_001                      |
| **Test Case Name** | Verify Default Completed Value |
| **Module**         | Business Logic                 |
| **Priority**       | High                           |
| **Pre-conditions** | None                           |

**Test Steps:**

1. Create a todo without specifying completed field
2. Retrieve the todo
3. Check the completed value

**Test Data:**

```json
{
  "title": "Test Todo"
}
```

**Expected Result:**

- completed field defaults to false
- Todo appears in active filter

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_BL_002: Timestamp Auto-Generation on Create

| **Field**          | **Details**                       |
| ------------------ | --------------------------------- |
| **Test Case ID**   | TC_BL_002                         |
| **Test Case Name** | Verify Timestamps Set on Creation |
| **Module**         | Business Logic                    |
| **Priority**       | High                              |
| **Pre-conditions** | None                              |

**Test Steps:**

1. Create a new todo
2. Verify createdAt is set
3. Verify updatedAt is set
4. Verify both timestamps are equal

**Test Data:**

- Any valid todo

**Expected Result:**

- createdAt is auto-populated
- updatedAt is auto-populated
- createdAt equals updatedAt on creation
- Timestamps in ISO 8601 format

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_BL_003: Timestamp Update on Modification

| **Field**          | **Details**                        |
| ------------------ | ---------------------------------- |
| **Test Case ID**   | TC_BL_003                          |
| **Test Case Name** | Verify updatedAt Changes on Update |
| **Module**         | Business Logic                     |
| **Priority**       | High                               |
| **Pre-conditions** | Todo exists                        |

**Test Steps:**

1. Create a todo and note timestamps
2. Wait 2 seconds
3. Update the todo
4. Compare timestamps

**Test Data:**

- Existing todo

**Expected Result:**

- createdAt remains unchanged
- updatedAt is later than createdAt
- updatedAt reflects modification time

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_BL_004: Toggle Maintains Other Fields

| **Field**          | **Details**                         |
| ------------------ | ----------------------------------- |
| **Test Case ID**   | TC_BL_004                           |
| **Test Case Name** | Toggle Only Changes Completed Field |
| **Module**         | Business Logic                      |
| **Priority**       | High                                |
| **Pre-conditions** | Todo exists                         |

**Test Steps:**

1. Note all fields of a todo
2. Toggle completion
3. Verify only completed and updatedAt changed

**Test Data:**

- Todo with title, description, etc.

**Expected Result:**

- Only completed field toggles
- updatedAt changes
- title, description, id unchanged
- createdAt unchanged

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

### Module: Data Validation

#### TC_BL_005: Title Cannot Be Null

| **Field**          | **Details**                   |
| ------------------ | ----------------------------- |
| **Test Case ID**   | TC_BL_005                     |
| **Test Case Name** | Validate Required Title Field |
| **Module**         | Business Logic                |
| **Priority**       | Critical                      |
| **Pre-conditions** | None                          |

**Test Steps:**

1. Attempt to create todo with null title
2. Verify validation error

**Test Data:**

```json
{
  "title": null,
  "description": "Some description"
}
```

**Expected Result:**

- Validation error thrown
- HTTP 400 Bad Request
- Error message about required title

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed
**Note:** _Requires validation implementation_

---

#### TC_BL_006: Description Can Be Null

| **Field**          | **Details**             |
| ------------------ | ----------------------- |
| **Test Case ID**   | TC_BL_006               |
| **Test Case Name** | Description Is Optional |
| **Module**         | Business Logic          |
| **Priority**       | Medium                  |
| **Pre-conditions** | None                    |

**Test Steps:**

1. Create todo without description
2. Verify todo is created successfully

**Test Data:**

```json
{
  "title": "Todo without description",
  "completed": false
}
```

**Expected Result:**

- Todo created successfully
- description is null or empty
- No validation errors

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

## Workflow Test Cases

### Complete User Workflows

#### TC_WF_001: Complete Todo Management Workflow

| **Field**          | **Details**                        |
| ------------------ | ---------------------------------- |
| **Test Case ID**   | TC_WF_001                          |
| **Test Case Name** | End-to-End Todo Lifecycle          |
| **Module**         | Workflow                           |
| **Priority**       | Critical                           |
| **Pre-conditions** | Clean database, application loaded |

**Test Steps:**

1. **Create**: Add todo "Buy milk"
2. **Read**: Verify it appears in All and Active filters
3. **Update**: Change title to "Buy organic milk"
4. **Toggle**: Mark as completed
5. **Filter**: Verify appears in Completed filter only
6. **Delete**: Remove the todo
7. **Verify**: Confirm it's gone from all filters

**Test Data:**

- Initial: "Buy milk"
- Updated: "Buy organic milk"

**Expected Result:**

- Each step succeeds
- Statistics update correctly at each step
- Final state: No todos, all counts = 0

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_WF_002: Multiple Todos Management

| **Field**          | **Details**             |
| ------------------ | ----------------------- |
| **Test Case ID**   | TC_WF_002               |
| **Test Case Name** | Managing Multiple Todos |
| **Module**         | Workflow                |
| **Priority**       | High                    |
| **Pre-conditions** | Clean database          |

**Test Steps:**

1. Create 5 todos with different titles
2. Complete 3 of them
3. Filter by Active - verify 2 shown
4. Filter by Completed - verify 3 shown
5. Delete 1 completed todo
6. Clear all completed
7. Verify only active todos remain

**Test Data:**

- 5 Unique todos

**Expected Result:**

- All operations successful
- Correct filtering at each step
- Final state: 2 active todos
- Statistics: Total=2, Active=2, Completed=0

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_WF_003: Rapid Toggle Operations

| **Field**          | **Details**                   |
| ------------------ | ----------------------------- |
| **Test Case ID**   | TC_WF_003                     |
| **Test Case Name** | Quick Multiple Toggle Actions |
| **Module**         | Workflow                      |
| **Priority**       | Medium                        |
| **Pre-conditions** | 1 todo exists                 |

**Test Steps:**

1. Toggle todo to completed
2. Immediately toggle back to active
3. Toggle to completed again
4. Refresh page
5. Verify final state

**Test Data:**

- Single todo

**Expected Result:**

- Each toggle responds immediately
- No lag or errors
- Final state (completed) persists
- Statistics accurate throughout

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

#### TC_WF_004: Create, Filter, and Delete Workflow

| **Field**          | **Details**                      |
| ------------------ | -------------------------------- |
| **Test Case ID**   | TC_WF_004                        |
| **Test Case Name** | Filtering During CRUD Operations |
| **Module**         | Workflow                         |
| **Priority**       | Medium                           |
| **Pre-conditions** | Clean database                   |

**Test Steps:**

1. Set filter to "Active"
2. Create 2 new todos (should appear immediately)
3. Set filter to "Completed"
4. Mark 1 todo as completed (should appear in view)
5. Click "Clear Completed"
6. Switch to "All" filter
7. Verify only 1 active todo remains

**Test Data:**

- 2 New todos

**Expected Result:**

- Todos appear in correct filter views
- Filter updates dynamically
- Operations work regardless of active filter
- Final: 1 active todo

**Actual Result:** _[To be filled]_

**Status:** ⬜ Not Executed

---

## Test Execution Summary

### Test Summary Template

| **Metric**       | **Count** |
| ---------------- | --------- |
| Total Test Cases | 54        |
| Executed         | 0         |
| Passed           | 0         |
| Failed           | 0         |
| Blocked          | 0         |
| Not Executed     | 54        |

### Test Coverage

| **Module**            | **Total Tests** | **Pass** | **Fail** | **Coverage %** |
| --------------------- | --------------- | -------- | -------- | -------------- |
| UI - Todo Creation    | 4               | 0        | 0        | 0%             |
| UI - Todo Completion  | 3               | 0        | 0        | 0%             |
| UI - Todo Filtering   | 4               | 0        | 0        | 0%             |
| UI - Todo Deletion    | 4               | 0        | 0        | 0%             |
| UI - Statistics       | 3               | 0        | 0        | 0%             |
| UI - Data Persistence | 2               | 0        | 0        | 0%             |
| API - GET             | 5               | 0        | 0        | 0%             |
| API - POST            | 4               | 0        | 0        | 0%             |
| API - PUT             | 2               | 0        | 0        | 0%             |
| API - PATCH           | 3               | 0        | 0        | 0%             |
| API - DELETE          | 4               | 0        | 0        | 0%             |
| Business Logic        | 6               | 0        | 0        | 0%             |
| Workflows             | 4               | 0        | 0        | 0%             |

### Priority Distribution

| **Priority** | **Count** | **Percentage** |
| ------------ | --------- | -------------- |
| Critical     | 15        | 27.8%          |
| High         | 26        | 48.1%          |
| Medium       | 11        | 20.4%          |
| Low          | 2         | 3.7%           |

---

## Test Execution Guidelines

### Pre-Execution Checklist

- [ ] Application is running on http://localhost:8080
- [ ] Database is accessible
- [ ] Test data is prepared
- [ ] Browser is ready (for UI tests)
- [ ] API client is configured (Postman/curl)

### Execution Instructions

1. Execute tests in order by priority (Critical → High → Medium → Low)
2. Execute UI tests before API tests for the same feature
3. Reset database between test executions
4. Document actual results immediately
5. Take screenshots for failed tests
6. Log defects in tracking system

### Pass Criteria

- All expected results match actual results
- No unexpected errors or warnings
- Data integrity maintained
- Response times acceptable (< 2 seconds for UI, < 500ms for API)

### Failure Reporting

For each failure, document:

- Test Case ID
- Steps to reproduce
- Actual vs Expected result
- Screenshots/logs
- Environment details
- Severity (Critical/High/Medium/Low)

---

## Tools Required

### For Manual Testing

- **Browser**: Chrome/Firefox/Edge (latest version)
- **API Client**: Postman, Insomnia, or curl
- **Database Client**: H2 Console (http://localhost:8080/h2-console)
- **Screen Capture**: For documenting failures

### For Automated Testing

- **JUnit 5**: Unit and integration tests
- **MockMvc**: Controller testing
- **REST Assured**: API testing (if implemented)
- **Selenium**: UI automation (if implemented)

---

## Glossary

| **Term**           | **Definition**                                     |
| ------------------ | -------------------------------------------------- |
| **Active Todo**    | A todo with completed=false                        |
| **Completed Todo** | A todo with completed=true                         |
| **Toggle**         | Switch between active and completed states         |
| **Filter**         | Display subset of todos based on criteria          |
| **CRUD**           | Create, Read, Update, Delete operations            |
| **API**            | Application Programming Interface (REST endpoints) |

---

## Document Revision History

| **Version** | **Date**   | **Author** | **Changes**               |
| ----------- | ---------- | ---------- | ------------------------- |
| 1.0         | 2025-11-18 | QA Team    | Initial document creation |

---

## Approval

| **Role**        | **Name**       | **Signature**  | **Date**     |
| --------------- | -------------- | -------------- | ------------ |
| QA Lead         | ****\_\_\_**** | ****\_\_\_**** | **\_\_\_\_** |
| Project Manager | ****\_\_\_**** | ****\_\_\_**** | **\_\_\_\_** |
| Developer Lead  | ****\_\_\_**** | ****\_\_\_**** | **\_\_\_\_** |

---

**End of Document**
