package org.example.utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReportManager {
    private static ExtentReports extent;
    private static final String REPORTS_DIR = "test-output/reports";
    private static final String REPORT_FILE = REPORTS_DIR + "/ExecutionReport.html";

    public synchronized static ExtentReports getInstance() {
        if (extent == null) {
            try {
                Path dir = Paths.get(REPORTS_DIR);
                if (!Files.exists(dir)) Files.createDirectories(dir);

                ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_FILE);
                spark.config().setReportName("Execution Report");
                spark.config().setDocumentTitle("Automation Report");

                extent = new ExtentReports();
                extent.attachReporter(spark);
                extent.setSystemInfo("Project", "TestingChallenge");
                extent.setSystemInfo("Automation", "Selenium/WebDriver");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return extent;
    }
}
