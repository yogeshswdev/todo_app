*** Settings ***
Documentation    Todo Application UI Test Suite using Robot Framework
Library          SeleniumLibrary
Suite Setup      Open Browser And Navigate To Application
Suite Teardown   Close Browser
Test Setup       Clear All Todos

*** Variables ***
${URL}              http://localhost:8080
${BROWSER}          Chrome
${TITLE_INPUT}      id:todoTitle
${DESC_INPUT}       id:todoDescription
${ADD_BUTTON}       xpath://button[contains(text(), 'Add Todo')]
${TOTAL_COUNT}      id:totalCount
${ACTIVE_COUNT}     id:activeCount
${COMPLETED_COUNT}  id:completedCount
${ALL_FILTER}       xpath://button[contains(@class, 'filter-btn') and contains(text(), 'All')]
${ACTIVE_FILTER}    xpath://button[contains(@class, 'filter-btn') and contains(text(), 'Active')]
${COMPLETED_FILTER} xpath://button[contains(@class, 'filter-btn') and contains(text(), 'Completed')]
${CLEAR_BTN}        xpath://button[contains(text(), 'Clear Completed')]

*** Keywords ***
Open Browser And Navigate To Application
    Open Browser    ${URL}    ${BROWSER}
    Maximize Browser Window
    Set Selenium Implicit Wait    10 seconds
    Wait Until Page Contains Element    ${TITLE_INPUT}

Clear All Todos
    [Documentation]    Clear all existing todos before each test
    Go To    ${URL}
    Sleep    1s
    ${has_todos}=    Run Keyword And Return Status    Page Should Contain Element    xpath://div[contains(@class, 'todo-item')]
    Run Keyword If    ${has_todos}    Delete All Todos From UI

Delete All Todos From UI
    [Documentation]    Delete all visible todos
    ${count}=    Get Element Count    xpath://button[contains(@class, 'delete-btn')]
    FOR    ${i}    IN RANGE    ${count}
        Click Element    xpath:(//button[contains(@class, 'delete-btn')])[1]
        Handle Alert    ACCEPT
        Sleep    0.5s
    END

Create Todo
    [Arguments]    ${title}
    [Documentation]    Create a new todo with the given title
    Input Text    ${TITLE_INPUT}    ${title}
    Click Element    ${ADD_BUTTON}
    Sleep    0.5s

Create Todo With Description
    [Arguments]    ${title}    ${description}
    [Documentation]    Create a new todo with title and description
    Input Text    ${TITLE_INPUT}    ${title}
    Input Text    ${DESC_INPUT}    ${description}
    Click Element    ${ADD_BUTTON}
    Sleep    0.5s

Toggle Todo
    [Arguments]    ${title}
    [Documentation]    Toggle the completion status of a todo
    ${xpath}=    Set Variable    xpath://div[@class='todo-title' and contains(text(), '${title}')]/ancestor::div[contains(@class, 'todo-item')]//div[contains(@class, 'checkbox')]
    Click Element    ${xpath}
    Sleep    0.5s

Delete Todo
    [Arguments]    ${title}
    [Documentation]    Delete a specific todo
    ${xpath}=    Set Variable    xpath://div[@class='todo-title' and contains(text(), '${title}')]/ancestor::div[contains(@class, 'todo-item')]//button[contains(@class, 'delete-btn')]
    Click Element    ${xpath}
    Handle Alert    ACCEPT
    Sleep    0.5s

Verify Todo Exists
    [Arguments]    ${title}
    [Documentation]    Verify that a todo with the given title exists
    Page Should Contain Element    xpath://div[@class='todo-title' and contains(text(), '${title}')]

Verify Todo Not Exists
    [Arguments]    ${title}
    [Documentation]    Verify that a todo with the given title does not exist
    Page Should Not Contain Element    xpath://div[@class='todo-title' and contains(text(), '${title}')]

Verify Todo Is Completed
    [Arguments]    ${title}
    [Documentation]    Verify that a todo is marked as completed
    ${xpath}=    Set Variable    xpath://div[@class='todo-title' and contains(text(), '${title}')]/ancestor::div[contains(@class, 'todo-item')]
    Element Should Contain    ${xpath}    completed    # Check class attribute

Verify Statistics
    [Arguments]    ${total}    ${active}    ${completed}
    [Documentation]    Verify the statistics counters
    Element Text Should Be    ${TOTAL_COUNT}    ${total}
    Element Text Should Be    ${ACTIVE_COUNT}    ${active}
    Element Text Should Be    ${COMPLETED_COUNT}    ${completed}

*** Test Cases ***
RF_TC_001: Create Todo With Valid Title
    [Documentation]    Test creating a todo with a valid title
    [Tags]    creation    smoke
    Create Todo    Buy groceries
    Verify Todo Exists    Buy groceries
    Verify Statistics    1    1    0

RF_TC_002: Create Todo With Title And Description
    [Documentation]    Test creating a todo with both title and description
    [Tags]    creation
    Create Todo With Description    Complete project    Include API docs
    Verify Todo Exists    Complete project
    Verify Statistics    1    1    0

RF_TC_003: Mark Todo As Completed
    [Documentation]    Test marking a todo as completed
    [Tags]    completion    smoke
    Create Todo    Test Todo
    Toggle Todo    Test Todo
    Verify Todo Is Completed    Test Todo
    Verify Statistics    1    0    1

RF_TC_004: Uncheck Completed Todo
    [Documentation]    Test unchecking a completed todo
    [Tags]    completion
    Create Todo    Test Todo
    Toggle Todo    Test Todo
    Verify Statistics    1    0    1
    Toggle Todo    Test Todo
    Verify Statistics    1    1    0

RF_TC_005: Filter Active Todos
    [Documentation]    Test filtering to show only active todos
    [Tags]    filtering
    Create Todo    Active Task 1
    Create Todo    Active Task 2
    Create Todo    Completed Task
    Toggle Todo    Completed Task
    Click Element    ${ACTIVE_FILTER}
    Sleep    0.5s
    Verify Todo Exists    Active Task 1
    Verify Todo Exists    Active Task 2
    ${count}=    Get Element Count    xpath://div[contains(@class, 'todo-item')]
    Should Be Equal As Numbers    ${count}    2

RF_TC_006: Filter Completed Todos
    [Documentation]    Test filtering to show only completed todos
    [Tags]    filtering
    Create Todo    Active Task
    Create Todo    Completed Task 1
    Create Todo    Completed Task 2
    Toggle Todo    Completed Task 1
    Toggle Todo    Completed Task 2
    Click Element    ${COMPLETED_FILTER}
    Sleep    0.5s
    Verify Todo Exists    Completed Task 1
    Verify Todo Exists    Completed Task 2
    ${count}=    Get Element Count    xpath://div[contains(@class, 'todo-item')]
    Should Be Equal As Numbers    ${count}    2

RF_TC_007: Delete Single Todo
    [Documentation]    Test deleting a single todo
    [Tags]    deletion    smoke
    Create Todo    Todo to Delete
    Verify Statistics    1    1    0
    Delete Todo    Todo to Delete
    Verify Todo Not Exists    Todo to Delete
    Verify Statistics    0    0    0

RF_TC_008: Clear All Completed Todos
    [Documentation]    Test clearing all completed todos at once
    [Tags]    deletion
    Create Todo    Active 1
    Create Todo    Completed 1
    Create Todo    Completed 2
    Toggle Todo    Completed 1
    Toggle Todo    Completed 2
    Verify Statistics    3    1    2
    Click Element    ${CLEAR_BTN}
    Handle Alert    ACCEPT
    Sleep    0.5s
    Verify Statistics    1    1    0
    Verify Todo Exists    Active 1
    Verify Todo Not Exists    Completed 1

RF_TC_009: Verify Statistics Update
    [Documentation]    Test that statistics update correctly
    [Tags]    statistics
    Verify Statistics    0    0    0
    Create Todo    Task 1
    Verify Statistics    1    1    0
    Create Todo    Task 2
    Verify Statistics    2    2    0
    Toggle Todo    Task 1
    Verify Statistics    2    1    1
    Delete Todo    Task 2
    Verify Statistics    1    0    1

RF_TC_010: Complete Workflow Test
    [Documentation]    End-to-end workflow testing
    [Tags]    workflow    smoke
    # Create multiple todos
    Create Todo    Task 1
    Create Todo    Task 2
    Create Todo    Task 3
    Verify Statistics    3    3    0
    
    # Complete some todos
    Toggle Todo    Task 1
    Toggle Todo    Task 2
    Verify Statistics    3    1    2
    
    # Filter by completed
    Click Element    ${COMPLETED_FILTER}
    Sleep    0.5s
    Verify Todo Exists    Task 1
    Verify Todo Exists    Task 2
    
    # Clear completed
    Click Element    ${CLEAR_BTN}
    Handle Alert    ACCEPT
    Sleep    0.5s
    
    # Verify final state
    Click Element    ${ALL_FILTER}
    Sleep    0.5s
    Verify Statistics    1    1    0
    Verify Todo Exists    Task 3
