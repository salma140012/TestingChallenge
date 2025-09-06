package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Utility class to read CSV test data.
 * Each row represents a test case.
 */
public class data {

    /**
     * Reads all test data from a CSV file.
     * @param filePath Path to CSV file
     * @return List of maps, each map represents one test case row
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
     * Retrieves test data for a specific test case identified by testName.
     * @param filePath Path to CSV file
     * @param testName Name of the test case (column value under "testName")
     * @return Map containing key-value pairs for that test case
     */
    public static Map<String, String> getTestData(String filePath, String testName) {
        List<Map<String, String>> allData = readCSVData(filePath);

        return allData.stream()
                .filter(row -> testName.equalsIgnoreCase(row.get("testName")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Test data not found for: " + testName));
    }
}