package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Abstract base class for all page objects.
 * Provides common WebDriver actions and utilities.
 */
public abstract class basePage {
    /** WebDriver instance used by the page. */
    protected WebDriver driver;
    /** WebDriverWait instance for explicit waits. */
    protected WebDriverWait wait;
    /**
     * Constructor initializing WebDriver and WebDriverWait.
     * @param driver WebDriver instance to use
     */
    public basePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    /**
     * Returns the WebDriverWait instance.
     * @return WebDriverWait instance
     */

    public WebDriverWait getWait() {
        return wait;
    }

    /**
     * Clicks an element located by the given locator.
     * @param locator By locator of the element
     */
    protected void click(By locator) {
        driver.findElement(locator).click();
    }
    /**
     * Types text into an input element located by the given locator.
     * @param locator By locator of the element
     * @param text Text to type
     */

    protected void type(By locator, String text) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }
    /**
     * Navigates to a specific URL.
     * @param url URL to navigate to
     */

    public void navigateToUrl(String url) {
        driver.get(url);
    }
    /**
     * Simple wait for page load
     */
    public void waitForPageLoad() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Waits until elements located by the given locator are present.
     * @param locator By locator of the elements
     */

    public void waitForElementsToBePresent(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
    /**
     * Checks if an element is present and displayed on the page.
     * @param locator By locator of the element
     * @return true if element is present and displayed, false otherwise
     */

    protected boolean isElementPresent(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}