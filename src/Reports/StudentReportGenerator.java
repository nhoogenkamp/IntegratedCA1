/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reports;

import interfaces.ReportGenerator;
import interfaces.ReportFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Capitania StudentReportGenerator generates a report of students
 * including student ID, student name, program name, module name, grades,
 * finishing date, and whether the student needs to repeat a class. The
 * retrieved data is then formatted into a list of strings and passed to the
 * provided formatter for further processing.
 */
public class StudentReportGenerator implements ReportGenerator {

    @Override
    public void generateReport(Connection connection, ReportFormatter formatter) throws SQLException {
        // Lock the tables before executing the query
        lockTables(connection);

        // SQL query for Student Report
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

            // Initialize list to hold report data
            // Process the result set
            List<String> reportData = new ArrayList<>();
            // Add column headers
            reportData.add("Student ID,Student Name,Program Name,Module Name,Grades,Finishing Date,Needs to Repeat Class");
            while (resultSet.next()) {
                // Extract data from the result set
                String studentId = resultSet.getString("student_id");
                String studentName = resultSet.getString("student_name");
                String programName = resultSet.getString("program_name");
                String moduleName = resultSet.getString("module_name");
                int grades = resultSet.getInt("grades");
                String finishingDate = resultSet.getString("finishing_date");
                String needsToRepeat = resultSet.getString("needs_to_repeat_class");

                // Construct formatted string for the row
                String rowData = String.format("%s, %s, %s, %s, %d, %s, %s",
                        studentId, studentName, programName, moduleName, grades, finishingDate, needsToRepeat);
                reportData.add(rowData);
            }

            // Pass the formatted data to the formatter
            formatter.format(reportData.toArray(new String[0]));
        } finally {
            // Unlock the tables after executing the query
            unlockTables(connection);
        }
    }

    // Method to lock tables
    private void lockTables(Connection connection) throws SQLException {
        String lockSql = "LOCK TABLES enrollment e READ, Students s READ, program p READ, Modules m READ, grades g READ";
        try (PreparedStatement lockStatement = connection.prepareStatement(lockSql)) {
            lockStatement.execute();
        }
    }

    // Method to unlock tables
    private void unlockTables(Connection connection) throws SQLException {
        String unlockSql = "UNLOCK TABLES";
        try (PreparedStatement unlockStatement = connection.prepareStatement(unlockSql)) {
            unlockStatement.execute();
        }
    }
}
