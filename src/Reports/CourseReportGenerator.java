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
 * @author Capitania CourseReportGenerator generates a report of the courses The
 * SQL query retrieves data including module name, program name, number of
 * students enrolled, lecturer name, and room name, from various tables and
 * joins them appropriately. The retrieved data is then formatted into a list of
 * strings and passed to the provided formatter for further processing.
 */
public class CourseReportGenerator implements ReportGenerator {

    @Override
    public void generateReport(Connection connection, ReportFormatter formatter) throws SQLException {
        // Lock the tables before executing the query
        lockTables(connection);
        
        // SQL query for Course Report
        String sql = "SELECT m.module_name, p.program_name, COUNT(DISTINCT e.student_id) AS num_students_enrolled, l.lecturer_name, r.room_name "
                + "FROM Modules m "
                + "JOIN program p ON m.program_name = p.program_name "
                + "LEFT JOIN Students s ON m.program_name = s.program_name "
                + "LEFT JOIN enrollment e ON s.student_id = e.student_id "
                + "LEFT JOIN lecturers l ON m.lecturer_id = l.lecturer_id "
                + "LEFT JOIN rooms r ON m.room_id = r.room_id "
                + "GROUP BY p.program_name, m.module_name, l.lecturer_name, r.room_name";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            // Initialize list to hold report data
            List<String> reportData = new ArrayList<>();
            // Add column headers
            reportData.add("Module Name,Program Name,Enrolled,Lecturer,Room"); 
            while (resultSet.next()) {
                // Extract data from the result set
                String moduleName = resultSet.getString("module_name");
                String programName = resultSet.getString("program_name");
                int numStudentsEnrolled = resultSet.getInt("num_students_enrolled");
                String lecturerName = resultSet.getString("lecturer_name");
                String roomName = resultSet.getString("room_name");

                // Construct formatted string for the row
                String rowData = String.format("%s,%s,%d,%s,%s", moduleName, programName, numStudentsEnrolled, lecturerName, roomName);
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
        String lockSql = "LOCK TABLES Modules m READ, program p READ, Students s READ, enrollment e READ, lecturers l READ, rooms r READ";
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