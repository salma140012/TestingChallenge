package Listener;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * TestNG listener for logging test execution and capturing screenshots on failure.
 */
public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static final String REPORTS_DIR = "test-output/reports";
    private static final String SCREENSHOT_DIR = "test-output/screenshots";

    /**
     * Returns a  ExtentReports instance.
     */
    private ExtentReports getExtent() {
        if (extent == null) {
            try {
                Path reportDir = Paths.get(REPORTS_DIR);
                if (!Files.exists(reportDir)) Files.createDirectories(reportDir);

                ExtentSparkReporter spark = new ExtentSparkReporter(REPORTS_DIR + "/ExtentReport.html");
                spark.config().setReportName("Execution Report");
                spark.config().setDocumentTitle("Automation Report");

                extent = new ExtentReports();
                extent.attachReporter(spark);
                extent.setSystemInfo("Project", "TestingChallenge");
                extent.setSystemInfo("Automation", "Selenium WebDriver");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return extent;
    }
    /**
     * Captures a screenshot and returns the file path.
     */
    private String takeScreenshot(WebDriver driver, String testName) {
        try {
            Path dir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(dir)) Files.createDirectories(dir);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = testName + "_" + timestamp + ".png";
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(dir.toFile(), filename);
            FileUtils.copyFile(srcFile, destFile);
            return destFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = getExtent().createTest(result.getMethod().getMethodName());
        testThread.set(test);
        test.info("Test started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        String errorMsg = throwable != null ? throwable.toString() : "";

        WebDriver driver = null;
        try {
            Field f = result.getInstance().getClass().getDeclaredField("driver");
            f.setAccessible(true);
            driver = (WebDriver) f.get(result.getInstance());
        } catch (Exception ignored) {}

        if (driver != null) {
            String screenshotPath = takeScreenshot(driver, result.getMethod().getMethodName());
            try {
                testThread.get()
                        .fail("Test failed: " + errorMsg)
                        .addScreenCaptureFromPath(screenshotPath); // embeds screenshot in report
            } catch (Exception e) {
                testThread.get().fail("Test failed: " + errorMsg + " (screenshot attach failed)");
            }
        } else {
            testThread.get().fail("Test failed: " + errorMsg + " (WebDriver not found)");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        getExtent().flush(); // flush report at end
    }

    @Override public void onStart(ITestContext context) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
}
