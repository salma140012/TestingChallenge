package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BingeResultPage extends basePage {

    private final By searchBoxLocator = By.name("q");
    private final By searchResultsLocator = By.cssSelector("li.b_algo");
    private final By relatedSearchesHeaderLocator = By.xpath("//h2[contains(text(), 'Related searches')]");
    private final By relatedSearchesItemsLocator = By.xpath("//div[contains(@class, 'b_rs')]//a");
    private final By nextPageButtonLocator = By.cssSelector("a[aria-label='Next page']");
    private By searchResults = By.cssSelector("li.b_algo");

    public BingeResultPage(WebDriver driver) {
        super(driver);
    }

    public void openBing(String url) {
        navigateToUrl(url);
        waitForPageLoad();
    }

    public void search(String term) {
        WebElement searchBox = driver.findElement(searchBoxLocator);
        searchBox.clear();
        searchBox.sendKeys(term, Keys.ENTER);
        //waitForElementsToBePresent(searchResultsLocator);
        waitForResultsToLoad();
    }

    public boolean validateRelatedSearches(String term, int expectedSections) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(relatedSearchesItemsLocator));

        List<WebElement> items = driver.findElements(relatedSearchesItemsLocator);

        // Debugging - print out what Bing actually returned
        System.out.println("Related searches found:");
        items.forEach(i -> System.out.println(" - " + i.getText()));

        // Validation: at least expectedSections
        return items.size() >= expectedSections;
    }

    public int countResults() {
        return driver.findElements(searchResultsLocator).size();
    }
    public void waitForResultsToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchResults));
    }

    public void goToNextPage() {
        List<WebElement> nextButtons = driver.findElements(nextPageButtonLocator);
        if (!nextButtons.isEmpty()) {
            WebElement nextBtn = nextButtons.get(0);
            nextBtn.click();
            waitForResultsToLoad();
            // Wait for old element to become stale
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.stalenessOf(nextBtn));
        }
    }

}