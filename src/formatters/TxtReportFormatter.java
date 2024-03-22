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
 * @author Capitania TxtReportFormatter is responsible for formatting report
 * data and writing it to a text file with properly aligned columns for
 * readability
 */
public class TxtReportFormatter implements ReportFormatter {

    @Override
    public void format(String[] data) {
        // Write report data to report text file
        try ( PrintWriter writer = new PrintWriter(new FileWriter("report.txt"))) {
            // Determine column widths based on the maximum width of each column in the report data
            int[] columnWidths = new int[data[0].split(",").length];
            // Itterate through each line of the data, split each line into parts and update column width if necesary
            for (String line : data) {
                String[] parts = line.split(",");
                for (int i = 0; i < parts.length; i++) {
                    columnWidths[i] = Math.max(columnWidths[i], parts[i].length());
                }
            }

            // Print report data with columns for text file readability and split each line into parts based on commas
            for (String line : data) {
                String[] parts = line.split(",");

                // Print each part with specified width for each column
                for (int i = 0; i < parts.length; i++) {
                    // Add 2 characters padding in each column to make sure there is more readability and no overlapping.
                    writer.printf("%-" + (columnWidths[i] + 2) + "s", parts[i]);
                }
                // Move to the next line after printing each line
                writer.println();
            }
            // Print message indicating successful printing           
            System.out.println("Report generated successfully in TXT format.");
            //catching any errors and would print the reason with message.
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
