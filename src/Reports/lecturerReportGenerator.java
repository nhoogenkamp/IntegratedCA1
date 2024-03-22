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
public class lecturerReportGenerator implements ReportGenerator {

    @Override
    public void generateReport(Connection connection) throws SQLException {
        // SQL query for Course Report
        String sql = "SELECT l.lecturer_name, l.job_role, m.module_name, COUNT(e.student_id) AS num_students_currently, l.teaching_classes AS type_class_they_can_teach "
                + "FROM enrollment e "
                + "JOIN Students s ON e.student_id = s.student_id "
                + "JOIN program p ON s.program_name = p.program_name "
                + "JOIN Modules m ON p.program_name = m.program_name "
                + "JOIN lecturers l ON m.module_id = l.module_id "
                + "WHERE e.finishing_date > '2024-03-21' "
                + "GROUP BY l.lecturer_name, l.job_role, m.module_name, l.teaching_classes "
                + "ORDER BY l.lecturer_name ASC";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            // Execute the query
            ResultSet resultSet = statement.executeQuery();
            // Print the report header
            System.out.println("Lecturer Report:");
            System.out.println("------------------------------------------------------");
            /**
             * Println is a simple way to print messages like course report
             * above but with PrintF you can specify the format of the printed
             * values In this case it's used to seperate the columns better to
             * make the print out on the console more readable.
             */
            System.out.printf("%-30s %-30s %-50s %-30s %-30s\n",
                    "Lecturer Name", "Job Role", "Module Name", "Num Students Currently", "Type Class They Can Teach");
            // Process the result set
            while (resultSet.next()) {
                // Extract data from the result set
                String lecturerName = resultSet.getString("lecturer_name");
                String jobRole = resultSet.getString("job_role");
                String moduleName = resultSet.getString("module_name");
                int numStudents = resultSet.getInt("num_students_currently");
                String typeClass = resultSet.getString("type_class_they_can_teach");
                // Print the report details
                System.out.printf("%-30s %-30s %-50s %-30d %-30s\n",
                        lecturerName, jobRole, moduleName, numStudents, typeClass);
            }
        }
    }
}
