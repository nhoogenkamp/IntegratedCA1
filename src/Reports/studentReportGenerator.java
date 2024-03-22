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
 * @author Capitania
 */
public class studentReportGenerator implements ReportGenerator {

    @Override
    public void generateReport(Connection connection) throws SQLException {
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
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Student Report:");
            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-30s %-55s %-55s %-10s %-15s %-10s\n",
                    "Student ID", "Student Name", "Program Name", "Module Name", "Grade", "Finishing Date", "Needs to Repeat");

            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String studentName = resultSet.getString("student_name");
                String programName = resultSet.getString("program_name");
                String moduleName = resultSet.getString("module_name");
                int grade = resultSet.getInt("grades");
                String finishingDate = resultSet.getString("finishing_date");
                String needsToRepeat = resultSet.getString("needs_to_repeat_class");

                System.out.printf("%-15s %-30s %-55s %-55s %-10s %-15s %-10s\n",
                        studentId, studentName, programName, moduleName, grade, finishingDate, needsToRepeat);
            }
        }
    }
}
