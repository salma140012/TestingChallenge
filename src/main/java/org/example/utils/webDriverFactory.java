package org.example.utils;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Factory class to create WebDriver instances for different browsers with optional proxy.
 */
public class webDriverFactory {

    /**
     * Creates a WebDriver instance based on the specified browser.
     * @param browser Browser name ("chrome", "firefox", "edge")
     * @param headless Whether to run the browser in headless mode
     * @param proxyString Proxy in format host:port (or null/empty for no proxy)
     * @return WebDriver instance
     */
    public static WebDriver createDriver(String browser, boolean headless, String proxyString) {
        WebDriver driver = null;

        // Configure proxy if provided
        Proxy proxy = null;
        if (proxyString != null && !proxyString.isEmpty()) {
            proxy = new Proxy();
            proxy.setHttpProxy(proxyString)
                    .setSslProxy(proxyString);
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless=new");
                if (proxy != null) chromeOptions.setProxy(proxy);
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (headless) ffOptions.addArguments("--headless");
                if (proxy != null) ffOptions.setProxy(proxy);
                driver = new FirefoxDriver(ffOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless=new");
                if (proxy != null) edgeOptions.setProxy(proxy);
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        return driver;
    }
}
