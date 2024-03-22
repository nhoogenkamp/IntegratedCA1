/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formatters;

import interfaces.ReportFormatter;

/**
 *
 * @author Capitania ConsoleReportFormatter is responsible for formatting the
 * report data to be printed to the console with properly aligned columns for
 * readability
 */
public class ConsoleReportFormatter implements ReportFormatter {

    @Override
    public void format(String[] data) {
        // Determine column widths based on the maximum width of each column in the report data
        int[] columnWidths = new int[data[0].split(",").length];
        // Itterate through each line of the data
        for (String line : data) {

            // Split each line into parts based on commas and go through each part.
            String[] parts = line.split(",");
            for (int i = 0; i < parts.length; i++) {
                //change column width if necesarry 
                columnWidths[i] = Math.max(columnWidths[i], parts[i].length());
            }
        }

        // Print report data with columns for console readability
        for (String line : data) {
            String[] parts = line.split(",");

            // Print each part with specified width for each column
            for (int i = 0; i < parts.length; i++) {
                // Add 2 characters padding in each column to make sure there is more readability and no overlapping.
                System.out.printf("%-" + (columnWidths[i] + 2) + "s", parts[i]); // Add 2 for padding
            }
            // Move to the next line after printing each line
            System.out.println();
        }
        // Print message indicating successful printing
        System.out.println("Report printed to console.");
    }
}
