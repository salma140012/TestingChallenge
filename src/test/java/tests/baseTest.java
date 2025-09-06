package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.*;

import java.io.File;

public class baseTest {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome")String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120 Safari/537.36");

            // Optional: make it look more "human"
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }

        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().driverVersion("0.34.0").setup(); // use latest compatible version
            FirefoxOptions options = new FirefoxOptions();
            driver = new FirefoxDriver(options);
        }  else if (browser.equalsIgnoreCase("edge")) {

            System.setProperty("webdriver.edge.driver", "C:\\Drivers\\msedgedriver.exe");
            EdgeOptions options = new EdgeOptions();
            driver = new EdgeDriver(options);

        }
        else {
            throw new IllegalArgumentException("Browser \"" + browser + "\" not supported");
        }

        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}