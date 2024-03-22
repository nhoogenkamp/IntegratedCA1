/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reports;

import interfaces.ReportGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Capitania Implements reportgenerator interface to create a report.
 */
public class studentReportGenerator implements ReportGenerator {

    private String outputFormat;

    public studentReportGenerator(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    @Override
    public void generateReport(Connection connection) throws SQLException {
        // SQL query for Course Report        
        String sql = "SELECT s.student_id, s.student_name, p.program_name, m.module_name, g.grades, e.finishing_date, "
                + "CASE WHEN g.grades < 40 THEN 'Y' ELSE 'N' END AS needs_to_repeat_class "
                + "FROM enrollment e "
                + "JOIN Students s ON e.student_id = s.student_id "
                + "JOIN program p ON s.program_name = p.program_name "
                + "JOIN Modules m ON p.program_name = m.program_name "
                + "LEFT JOIN grades g ON e.student_id = g.student_id AND m.module_id = g.module_id "
                + "WHERE g.grades IS NOT NULL "
                + "ORDER BY s.student_id, m.module_name";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            // Execute the query            
            ResultSet resultSet = statement.executeQuery();

            // Print the report based on the output format
            if (outputFormat.equals("console")) {
                printConsoleReport(resultSet);
            } else if (outputFormat.equals("txt")) {
                saveTxtReport(resultSet);
            } else if (outputFormat.equals("csv")) {
                saveCsvReport(resultSet);
            } else {
                System.out.println("Invalid output format.");
            }
        }
    }

    private void printConsoleReport(ResultSet resultSet) throws SQLException {
        // Print the report header
        System.out.println("Student Report:");
        System.out.println("------------------------------------------------------");
        /**
         * Println is a simple way to print messages like course report above
         * but with PrintF you can specify the format of the printed values In
         * this case it's used to seperate the columns better to make the print
         * out on the console more readable.
         */
        System.out.printf("%-15s %-30s %-55s %-55s %-10s %-15s %-10s\n",
                "Student ID", "Student Name", "Program Name", "Module Name", "Grade", "Finishing Date", "Needs to Repeat");

        // Process the result set
        while (resultSet.next()) {
            // Extract data from the result set
            String studentId = resultSet.getString("student_id");
            String studentName = resultSet.getString("student_name");
            String programName = resultSet.getString("program_name");
            String moduleName = resultSet.getString("module_name");
            int grade = resultSet.getInt("grades");
            String finishingDate = resultSet.getString("finishing_date");
            String needsToRepeat = resultSet.getString("needs_to_repeat_class");

            // Print the report details
            System.out.printf("%-15s %-30s %-55s %-55s %-10s %-15s %-10s\n",
                    studentId, studentName, programName, moduleName, grade, finishingDate, needsToRepeat);
        }
    }

    private void saveTxtReport(ResultSet resultSet) {
        // Logic to save the report as a text file
        // Not implemented in this example
        System.out.println("TXT report generation is not implemented yet.");
    }

    private void saveCsvReport(ResultSet resultSet) {
        // Logic to save the report as a CSV file
        // Not implemented in this example
        System.out.println("CSV report generation is not implemented yet.");
    }
}
