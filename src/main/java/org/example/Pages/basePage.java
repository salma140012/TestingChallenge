package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class basePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public basePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public WebDriverWait getWait() {
        return wait;
    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }

    public void navigateToUrl(String url) {
        driver.get(url);
    }

    public void waitForPageLoad() {
        try {
            Thread.sleep(2000); // simple wait, replaceable with JS wait
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitForElementsToBePresent(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected boolean isElementPresent(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}