/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formatters;

import interfaces.ReportFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Capitania CsvReportFormatter is responsible for formatting report
 * data and writing it to a CSV file.
 */
public class CsvReportFormatter implements ReportFormatter {

    @Override
    public void format(String[] data) {
        // Write report data to report csv file
        try ( PrintWriter writer = new PrintWriter(new FileWriter("report.csv"))) {
            // Itterate through each line of the data and writes it to the csv file.    
            for (String line : data) {
                writer.println(line);
            }
            // Print success message
            System.out.println("Report generated successfully in CSV format.");
            //catching any errors and would print the reason with message.
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
