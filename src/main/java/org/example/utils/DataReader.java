package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * Utility class to read CSV test data (alternative version).
 */
public class DataReader {
    /**
     * Reads all test data from a CSV file.
     * @param filePath Path to CSV file
     * @return List of maps, each representing a test case row
     */

    public static List<Map<String, String>> readCSVData(String filePath) {
        List<Map<String, String>> testData = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                Map<String, String> dataRow = new HashMap<>();
                for (String header : csvParser.getHeaderNames()) {
                    dataRow.put(header.trim(), record.get(header));
                }
                testData.add(dataRow);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }

        return testData;
    }
    /**
     * Gets test data for a specific test case by name.
     * @param filePath Path to CSV file
     * @param testName Name of the test case
     * @return Map containing key-value pairs for the test case
     */

    public static Map<String, String> getTestData(String filePath, String testName) {
        List<Map<String, String>> allData = readCSVData(filePath);

        return allData.stream()
                .filter(row -> testName.equalsIgnoreCase(row.get("testName")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Test data not found for: " + testName));
    }
}