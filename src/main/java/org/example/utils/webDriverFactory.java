package org.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class webDriverFactory {
    public static WebDriver createDriver(String browser, boolean headless){
        WebDriver driver = null;

        switch(browser){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions CHoptions = new ChromeOptions();
                if(headless) CHoptions.addArguments("--headless");
                driver = new ChromeDriver(CHoptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions FFoptions = new FirefoxOptions();
                if(headless) FFoptions.addArguments("--headless");
                driver = new FirefoxDriver(FFoptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless=new");
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        return driver;
    }
}