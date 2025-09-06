package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BingeMainPage extends basePage
{

    private WebDriver driver;

    private By  searchBarLocator = By.id("sb_form_q");
    private By  searchButtonLocator = By.id("search_icon");

    public BingeMainPage(WebDriver driver){
        super(driver);
    }
    public void naiviagteMainPage(String url){
        driver.get(url);
    }
    public void Search(String text){
        type(searchBarLocator,text);
        click(searchButtonLocator);
    }
}
