package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
/**
 * Page object representing the Binge search results page.
 */

public class BingeResultPage extends basePage {
    /** Locator for the search box. */
    private final By searchBoxLocator = By.name("q");
    /** Locator for search result items. */
    private final By searchResultsLocator = By.cssSelector("li.b_algo");
    /** Locator for the related searches header. */
    private final By relatedSearchesHeaderLocator = By.xpath("//h2[contains(text(), 'Related searches')]");
    /** Locator for related search items. */
    private final By relatedSearchesItemsLocator = By.xpath("//div[contains(@class, 'b_rs')]//a");
    /** Locator for the next page button. */
    private final By nextPageButtonLocator = By.cssSelector("a[aria-label='Next page']");
    /** Another reference for search results (used in waits). */
    private By searchResults = By.cssSelector("li.b_algo");
    /**
     * Constructor.
     * @param driver WebDriver instance
     */
    public BingeResultPage(WebDriver driver) {
        super(driver);
    }
    /**
     * Opens Bing homepage and waits for page load.
     * @param url URL of Bing
     */

    public void openBing(String url) {
        navigateToUrl(url);
        waitForPageLoad();
    }
    /**
     * Performs a search with the given term.
     * @param term Search term
     */
    public void search(String term) {
        WebElement searchBox = driver.findElement(searchBoxLocator);
        searchBox.clear();
        searchBox.sendKeys(term, Keys.ENTER);
        //waitForElementsToBePresent(searchResultsLocator);
        waitForResultsToLoad();
    }
    /**
     * Validates the presence of related search sections.
     * @param term Search term
     * @param expectedSections Expected number of sections
     * @return true if validation passes, false otherwise
     */

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
    /**
     * Counts the number of search results.
     * @return Number of results
     */

    public int countResults() {
        return driver.findElements(searchResultsLocator).size();
    }
    /**
     * Waits for search results to be visible.
     */
    public void waitForResultsToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchResults));
    }
    /**
     * Navigates to the next results page if available.
     */

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