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
public class CourseReportGenerator implements ReportGenerator {

    @Override
    public void generateReport(Connection connection) throws SQLException {
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

            // Print the report header
            System.out.println("Course Report:");
            System.out.println("------------------------------------------------------");
            /**
             * Println is a simple way to print messages like course report above but with PrintF you can specify the format of the printed values
             * In this case it's used to seperate the columns better to make the print out on the console more readable.
             */
            System.out.printf("%-50s %-55s %-15s %-35s %-25s\n",
                    "Module Name", "Program Name", "Enrolled", "Lecturer", "Room");

            // Process the result set
            while (resultSet.next()) {
                // Extract data from the result set
                String moduleName = resultSet.getString("module_name");
                String programName = resultSet.getString("program_name");
                int numStudentsEnrolled = resultSet.getInt("num_students_enrolled");
                String lecturerName = resultSet.getString("lecturer_name");
                String roomName = resultSet.getString("room_name");

                // Print the report details
                System.out.printf("%-50s %-55s %-15s %-35s %-25s\n",
                        moduleName, programName, numStudentsEnrolled, lecturerName, roomName);
            }
        }
    }
}
