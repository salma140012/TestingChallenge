package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * Page object representing the Bing main page.
 */

public class BingeMainPage extends basePage
{

    /** WebDriver instance (redundant, can use basePage.driver) */
    private WebDriver driver;
    /** Locator for the search bar. */
    private By  searchBarLocator = By.id("sb_form_q");
    /** Locator for the search button. */
    private By  searchButtonLocator = By.id("search_icon");
    /**
     * Constructor.
     * @param driver WebDriver instance
     */
    public BingeMainPage(WebDriver driver){
        super(driver);
    }
    /**
     * Navigates to the Bing main page.
     * @param url URL of the main page
     */
    public void naiviagteMainPage(String url){
        driver.get(url);
    }
    /**
     * Performs a search with the given text.
     * @param text Search term
     */
    public void Search(String text){
        type(searchBarLocator,text);
        click(searchButtonLocator);
    }
}
