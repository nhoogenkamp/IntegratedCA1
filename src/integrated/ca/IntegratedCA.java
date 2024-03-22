/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integrated.ca;

import interfaces.ReportGenerator;
import Reports.CourseReportGenerator;
import Reports.studentReportGenerator;
import Reports.lecturerReportGenerator;
import Connectors.DBConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Capitania * SBA23047 Niels Hoogenkamp
 *
 * Main class for generating reports in my IntegratedCA system This class
 * creates a database connection, generates a course report, and handles any SQL
 * exceptions. With the main method is to entry point of the program. The user
 * can choose between generating 3 different reports or exit the program.
 *
 */
public class IntegratedCA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // create an instance of the DBConnector class
        DBConnector db = new DBConnector();

        try ( Connection connection = db.getConnection()) {
            // Initialize choice variable
            int choice = 0;
            while (true) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Choose the output format for the report:");
    System.out.println("1. TXT");
    System.out.println("2. CSV");
    System.out.println("3. Console");

    int formatChoice = scanner.nextInt();
    String outputFormat;
    switch (formatChoice) {
        case 1:
            outputFormat = "txt";
            break;
        case 2:
            outputFormat = "csv";
            break;
        case 3:
            outputFormat = "console";
            break;
        default:
            System.out.println("Invalid choice. Defaulting to console output.");
            outputFormat = "console";
            break;
    }
                // options the user can pick from
                System.out.println("Choose a report to generate:");
                System.out.println("1. Course Report");
                System.out.println("2. Student Report");
                System.out.println("3. Lecturer Report");
                System.out.println("4. Exit Program");

                choice = scanner.nextInt();
                // call the appropriate report generator depending what the user chooses.
                switch (choice) {
                    case 1:
                        generateCourseReport(connection, outputFormat);
                        break;
                    case 2:
                        generateStudentReport(connection, outputFormat);
                        break;
                    case 3:
                        generateLecturerReport(connection, outputFormat);
                        break;
                    case 4:
                        System.out.println("Exiting program...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please choose a number between 1 and 4.");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
         // Generate Course/student/lecturers Report by creating an instance of coursereportgenerator and calling it's generatereport method. 
        //try catch e will print what the problem is.

    private static void generateCourseReport(Connection connection, String outputFormat) throws SQLException {
        // Generate the course report
        ReportGenerator CourseReportGenerator = new CourseReportGenerator(outputFormat);
        CourseReportGenerator.generateReport(connection);
    }

    private static void generateStudentReport(Connection connection, String outputFormat) throws SQLException {
        // Generate the student report
        ReportGenerator studentReportGenerator = new studentReportGenerator(outputFormat);
        studentReportGenerator.generateReport(connection);
    }

    private static void generateLecturerReport(Connection connection, String outputFormat) throws SQLException {
        // Generate the lecturer report
        ReportGenerator lecturerReportGenerator = new lecturerReportGenerator(outputFormat);
        lecturerReportGenerator.generateReport(connection);
    }
}
