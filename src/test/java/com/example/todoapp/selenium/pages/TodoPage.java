package com.example.todoapp.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for Todo Application Home Page
 * Contains all elements and actions for the main todo interface
 */
public class TodoPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Input Elements
    @FindBy(id = "todoTitle")
    private WebElement titleInput;

    @FindBy(id = "todoDescription")
    private WebElement descriptionTextarea;

    // Buttons
    @FindBy(xpath = "//button[contains(text(), 'Add Todo')]")
    private WebElement addTodoButton;

    @FindBy(xpath = "//button[contains(text(), 'Clear Completed')]")
    private WebElement clearCompletedButton;

    // Filter Buttons
    @FindBy(xpath = "//button[contains(@class, 'filter-btn') and contains(text(), 'All')]")
    private WebElement allFilterButton;

    @FindBy(xpath = "//button[contains(@class, 'filter-btn') and contains(text(), 'Active')]")
    private WebElement activeFilterButton;

    @FindBy(xpath = "//button[contains(@class, 'filter-btn') and contains(text(), 'Completed')]")
    private WebElement completedFilterButton;

    // Statistics
    @FindBy(id = "totalCount")
    private WebElement totalCountElement;

    @FindBy(id = "activeCount")
    private WebElement activeCountElement;

    @FindBy(id = "completedCount")
    private WebElement completedCountElement;

    // Container
    @FindBy(id = "todosContainer")
    private WebElement todosContainer;

    public TodoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Actions - Todo Creation

    public void enterTitle(String title) {
        wait.until(ExpectedConditions.visibilityOf(titleInput));
        titleInput.clear();
        titleInput.sendKeys(title);
    }

    public void enterDescription(String description) {
        descriptionTextarea.clear();
        descriptionTextarea.sendKeys(description);
    }

    public void clickAddTodo() {
        addTodoButton.click();
        // Wait for the todo to be added
        wait.until(ExpectedConditions.stalenessOf(todosContainer));
    }

    public void createTodo(String title) {
        enterTitle(title);
        clickAddTodo();
    }

    public void createTodoWithDescription(String title, String description) {
        enterTitle(title);
        enterDescription(description);
        clickAddTodo();
    }

    // Actions - Todo Interaction

    public void toggleTodoByTitle(String title) {
        WebElement checkbox = findTodoCheckboxByTitle(title);
        checkbox.click();
        sleep(500); // Wait for animation
    }

    public void deleteTodoByTitle(String title) {
        WebElement deleteButton = findTodoDeleteButtonByTitle(title);
        deleteButton.click();
        // Handle confirmation dialog
        driver.switchTo().alert().accept();
        sleep(500); // Wait for deletion
    }

    public void cancelDeleteTodoByTitle(String title) {
        WebElement deleteButton = findTodoDeleteButtonByTitle(title);
        deleteButton.click();
        // Dismiss confirmation dialog
        driver.switchTo().alert().dismiss();
    }

    // Actions - Filtering

    public void clickAllFilter() {
        allFilterButton.click();
        sleep(300);
    }

    public void clickActiveFilter() {
        activeFilterButton.click();
        sleep(300);
    }

    public void clickCompletedFilter() {
        completedFilterButton.click();
        sleep(300);
    }

    public void clickClearCompleted() {
        clearCompletedButton.click();
        driver.switchTo().alert().accept();
        sleep(500);
    }

    // Getters - Statistics

    public int getTotalCount() {
        wait.until(ExpectedConditions.visibilityOf(totalCountElement));
        return Integer.parseInt(totalCountElement.getText());
    }

    public int getActiveCount() {
        return Integer.parseInt(activeCountElement.getText());
    }

    public int getCompletedCount() {
        return Integer.parseInt(completedCountElement.getText());
    }

    // Getters - Todo Elements

    public int getVisibleTodoCount() {
        List<WebElement> todos = driver.findElements(By.className("todo-item"));
        return todos.size();
    }

    public boolean isTodoVisible(String title) {
        try {
            findTodoElementByTitle(title);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTodoCompleted(String title) {
        WebElement todoItem = findTodoElementByTitle(title);
        String classes = todoItem.getAttribute("class");
        return classes.contains("completed");
    }

    public boolean isTodoActive(String title) {
        return !isTodoCompleted(title);
    }

    public boolean isEmptyStateVisible() {
        try {
            WebElement emptyState = driver.findElement(By.className("empty-state"));
            return emptyState.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmptyStateMessage() {
        WebElement emptyState = driver.findElement(By.className("empty-state"));
        return emptyState.getText();
    }

    public boolean isFilterActive(String filterName) {
        WebElement filterButton = null;
        switch (filterName.toLowerCase()) {
            case "all":
                filterButton = allFilterButton;
                break;
            case "active":
                filterButton = activeFilterButton;
                break;
            case "completed":
                filterButton = completedFilterButton;
                break;
        }
        if (filterButton != null) {
            String classes = filterButton.getAttribute("class");
            return classes.contains("active");
        }
        return false;
    }

    // Validation Methods

    public boolean isTitleInputEmpty() {
        return titleInput.getAttribute("value").isEmpty();
    }

    public boolean isDescriptionInputEmpty() {
        return descriptionTextarea.getAttribute("value").isEmpty();
    }

    public String getAlertText() {
        try {
            return driver.switchTo().alert().getText();
        } catch (Exception e) {
            return null;
        }
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    // Helper Methods

    private WebElement findTodoElementByTitle(String title) {
        String xpath = String.format("//div[@class='todo-title' and contains(text(), '%s')]/ancestor::div[contains(@class, 'todo-item')]", title);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    private WebElement findTodoCheckboxByTitle(String title) {
        WebElement todoItem = findTodoElementByTitle(title);
        return todoItem.findElement(By.className("checkbox"));
    }

    private WebElement findTodoDeleteButtonByTitle(String title) {
        WebElement todoItem = findTodoElementByTitle(title);
        return todoItem.findElement(By.className("delete-btn"));
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Page State Verification

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(titleInput));
            wait.until(ExpectedConditions.visibilityOf(todosContainer));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForTodoCount(int expectedCount) {
        wait.until(driver -> getVisibleTodoCount() == expectedCount);
    }

    public void waitForStatistics(int total, int active, int completed) {
        wait.until(driver -> 
            getTotalCount() == total && 
            getActiveCount() == active && 
            getCompletedCount() == completed
        );
    }
}
