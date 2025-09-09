package Listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * TestNG listener for logging test execution (without screenshots).
 */
public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static final String REPORTS_DIR = "test-output/reports";

    /**
     * Returns an ExtentReports instance.
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
        testThread.get().fail("Test failed: " + errorMsg);
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
