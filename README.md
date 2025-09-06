Goal: Automate Bing search validation across multiple browsers with robust reporting .

1️⃣ Components

            +--------------------+
            |   TestNG Suite     | <-- Reads testng.xml, sets up cross-browser tests
            +--------------------+
                        |
                        v
            +--------------------+
            |    baseTest.java   | <-- Initializes WebDriver (Chrome, Firefox, Edge)
            +--------------------+
                        |
                        v
            +-----------------------------+
            |   Page Classes (POM)        |
            |-----------------------------|
            | basePage.java               | <-- Common actions (click, type, wait)
            | BingeMainPage.java          | <-- Bing main page actions
            | BingeResultPage.java        | <-- Search result actions
            +-----------------------------+
                        |
                        v
            +-----------------------------+
            |      Utilities              |
            |-----------------------------|
            | DataReader.java / data.java | <-- Reads CSV test data
            | ScreenshotUtil.java         | <-- Captures screenshots
            | webDriverFactory.java       | <-- Creates browser drivers
            | ReportManager.java          | <-- Configures ExtentReports
            +-----------------------------+
                        |
                        v
            +-----------------------------+
            |       Listener              |
            |-----------------------------|
            | TestListener.java           | <-- Monitors test execution
            |                             | Captures failures & screenshots
            |                             | Updates ExtentReports
            +-----------------------------+


2️⃣ Features

Cross-browser support: Chrome, Firefox, Edge

CSV-driven tests: Easy to add new scenarios

Page Object Model (POM): Clean separation of page actions

Robust waits: Explicit waits to handle dynamic content

HTML reporting: ExtentReports with embedded screenshots

TestNG annotations: Priorities, dependencies, parameterized browser runs

Validation:

Related searches validation

Page 2 & 3 results count validation

3️⃣ Execution Flow

TestNG Suite triggers browser tests as per testng.xml.

baseTest initializes WebDriver for the specified browser.

Test Scenario (testscenrio.java) runs:

Open Bing → Search → Validate related searches → Validate page results.

Listeners capture failures, screenshots, and log events.

ReportManager / ExtentReports generates a detailed HTML report.


====================================================================


1. Overview
   
Technologies used: Java, Selenium WebDriver, TestNG, ExtentReports, Apache Commons CSV
Supported browsers: Chrome, Firefox, Edge

5. Page Classes

            basePage.java ==> Handles WebDriver actions like click, type, navigation, waits Base class for all page objects

            BingeMainPage.java ==> Represents Bing main page Actions: navigate to page, perform search

            BingeResultPage.java ==> Represents Bing search results page Actions: search, validate related searches, count results, navigate pages

4. Utilities

            DataReader.java / data.java ==> Reads test data from CSV files Returns maps of key-value pairs for test scenarios

            webDriverFactory.java ==> Creates WebDriver instances for Chrome, Firefox, Edge Supports headless mode

            ReportManager.java ==>Configures ExtentReports Generates HTML execution report

5. Test Base

            baseTest.java ==> Sets up WebDriver based on browser parameter Maximizes window and manages tearDown

6. Listeners

            TestListener.java ==> Implements ITestListener for TestNG Integrates ExtentReports Logs test start, success, failure, skip

7. Test Scenarios

testscenrio.java ==> Reads test data from CSV Executes Bing search validation Validates related searches ,Validates page results count

8. TestNG Configuration

            testng.xml ==> Cross-browser suite Registers listener Defines Chrome, Firefox, Edge tests

9. Test Data

            testData.csv ==> Columns: testName, baseUrl, searchTerm, expectedSections Each row is a separate test scenario

10. Execution Workflow

            -TestNG reads testng.xml and initializes the suite.

            -baseTest sets up WebDriver for each browser.

            -Tests in testscenrio execute:

                        1)Open Bing

                        2)Perform search

                        3)Validate related searches

                        4)Validate page results count

                        5)ExtentReports generates HTML report.



