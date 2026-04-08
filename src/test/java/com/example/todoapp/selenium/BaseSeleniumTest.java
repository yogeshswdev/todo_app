package com.example.todoapp.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Base class for all Selenium UI tests
 * Handles WebDriver setup and teardown
 */
public class BaseSeleniumTest {

    protected WebDriver driver;
    protected static final String BASE_URL = "http://localhost:8080";
    protected static final int IMPLICIT_WAIT_SECONDS = 10;
    protected static final int PAGE_LOAD_TIMEOUT_SECONDS = 30;

    @BeforeAll
    public static void setupClass() {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        // Uncomment for headless mode
        // options.addArguments("--headless");
        
        // Initialize WebDriver
        driver = new ChromeDriver(options);
        
        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT_SECONDS));
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Navigate to the application home page
     */
    protected void navigateToHome() {
        driver.get(BASE_URL);
    }

    /**
     * Wait for a specified number of milliseconds
     */
    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
