package tests;

import org.example.Pages.BingeResultPage;
import org.example.utils.DataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
/**
 * Test scenario class to validate Bing search functionality.
 */

public class testscenrio extends baseTest {

    private BingeResultPage bingPage;
    private int page2Results;
    private int page3Results;
    /**
     * Validates the related searches section on Bing search results.
     */
    @Test(priority = 1)
    public void validateRelatedSearches() {
        String filePath = "src/test/resources/testData.csv";
        Map<String, String> testData = DataReader.getTestData(filePath, "VodafoneSearch");

        String baseUrl = testData.get("baseUrl");
        String searchTerm = testData.get("searchTerm");
        int expectedSections = Integer.parseInt(testData.get("expectedSections"));

        bingPage = new BingeResultPage(driver);

        bingPage.openBing(baseUrl);
        bingPage.search(searchTerm);

        try {
            boolean isValid = bingPage.validateRelatedSearches(searchTerm, expectedSections);
            Assert.assertTrue(isValid, "❌ Related searches validation failed!");
            System.out.println("✅ Related searches validation passed!");
        } catch (AssertionError e) {
            System.out.println("❌ Related searches validation failed: " + e.getMessage());
            throw e;
        }
    }
    /**
     * Validates the results count on page 2 and page 3 of Bing search results.
     */
    @Test(priority = 2, dependsOnMethods = "validateRelatedSearches")
    public void validatePageResultsCount() {
        bingPage.goToNextPage();
        bingPage.waitForResultsToLoad();
        page2Results = bingPage.countResults();
        System.out.println("Page 2 results count: " + page2Results);

        bingPage.goToNextPage();
        bingPage.waitForResultsToLoad();
        page3Results = bingPage.countResults();
        System.out.println("Page 3 results count: " + page3Results);

        try {
            Assert.assertTrue(page2Results > 0 && page3Results > 0,
                    "❌ Pages should have results!");
            System.out.println("✅ Page 2 and Page 3 both have results!");
        } catch (AssertionError e) {
            System.out.println("❌ Pages result count assertion failed: " + e.getMessage());
            throw e;
        }

        try {
            Assert.assertEquals(page2Results, page3Results,
                    "❌ Page 2 and Page 3 results count mismatch!");
            System.out.println("✅ Page 2 and Page 3 results count match!");
        } catch (AssertionError e) {
            System.out.println("❌ Page results count comparison failed: " + e.getMessage());
            throw e;
        }
    }
}
