package tests;

import org.example.Pages.BingeResultPage;
import org.example.utils.DataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class testscenrio extends baseTest {

    @Test
    public void testVodafoneSearchFlow() {
        // Read data from CSV
        String filePath = "src/test/resources/testData.csv";
        Map<String, String> testData = DataReader.getTestData(filePath, "VodafoneSearch");

        String baseUrl = testData.get("baseUrl");
        String searchTerm = testData.get("searchTerm");
        int expectedSections = Integer.parseInt(testData.get("expectedSections"));

        // Create Page Object
        BingeResultPage bingPage = new BingeResultPage(driver);

        // Step 1: Open Bing
        bingPage.openBing(baseUrl);

        // Step 2: Perform Search
        bingPage.search(searchTerm);

        // Step 3: Validate related searches
        try {
            boolean isValid = bingPage.validateRelatedSearches(searchTerm, expectedSections);
            Assert.assertTrue(isValid, "❌ Related searches validation failed!");
            System.out.println("✅ Related searches validation passed!");
        } catch (AssertionError e) {
            System.out.println("❌ Related searches validation failed: " + e.getMessage());
            throw e; // rethrow so TestNG marks test as failed
        }

        // Step 4: Count results on page 2
        bingPage.goToNextPage();
        bingPage.waitForResultsToLoad();
        int page2Results = bingPage.countResults();

        // Step 5: Count results on page 3
        bingPage.goToNextPage();
        bingPage.waitForResultsToLoad();
        int page3Results = bingPage.countResults();

        // Step 6.1: Assert pages have results
        try {
            Assert.assertTrue(page2Results > 0 && page3Results > 0,
                    "❌ Pages should have results!");
            System.out.println("✅ Page 2 and Page 3 both have results!");
        } catch (AssertionError e) {
            System.out.println("❌ Pages result count assertion failed: " + e.getMessage());
            throw e;
        }



        // Step 6.2: Assert page2 == page3
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