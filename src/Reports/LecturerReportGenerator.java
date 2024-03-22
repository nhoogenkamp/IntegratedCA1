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
 * @author Capitania LecturerReportGenerator generates a report of lecturers
 * including lecturer name, job role, module name, number of students currently,
 * and type of classes they can teach. The retrieved data is then formatted into
 * a list of strings and passed to the provided formatter for further
 * processing.
 */
public class LecturerReportGenerator implements ReportGenerator {

    @Override
    public void generateReport(Connection connection, ReportFormatter formatter) throws SQLException {
        
        // SQL query for Lecturer Report
        String sql = "SELECT l.lecturer_name, l.job_role, m.module_name, COUNT(e.student_id) AS num_students_currently, "
                + "l.teaching_classes AS type_class_they_can_teach "
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

            // Process the result set
            // Initialize list to hold report data
            List<String> reportData = new ArrayList<>();
            
            // Add column headers
            reportData.add("Lecturer Name,Job Role,Module Name,Num Students Currently,Type Class They Can Teach");
            while (resultSet.next()) {
                
                // Extract data from the result set
                String lecturerName = resultSet.getString("lecturer_name");
                String jobRole = resultSet.getString("job_role");
                String moduleName = resultSet.getString("module_name");
                int numStudentsCurrently = resultSet.getInt("num_students_currently");
                String typeClass = resultSet.getString("type_class_they_can_teach");

                // Construct CSV-formatted string for the row
                String rowData = String.format("%s,%s,%s,%d,%s", lecturerName, jobRole, moduleName, numStudentsCurrently, typeClass);
                reportData.add(rowData);
            }
            // Pass the formatted data to the formatter
            formatter.format(reportData.toArray(new String[0]));
        }
    }
}
