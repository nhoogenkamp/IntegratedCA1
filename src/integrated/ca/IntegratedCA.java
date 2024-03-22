/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integrated.ca;

import interfaces.ReportGenerator;
import interfaces.ReportFormatter;
import Reports.CourseReportGenerator;
import Reports.StudentReportGenerator;
import Reports.LecturerReportGenerator;
import formatters.TxtReportFormatter;
import formatters.ConsoleReportFormatter;
import formatters.CsvReportFormatter;
import Connectors.DBConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Capitania * SBA23047 Niels Hoogenkamp
 *
 * Main class for generating reports in my IntegratedCA system This class
 * creates a database connection, generates a course/student/lecturer report, and handles any SQL
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

    Scanner scanner = new Scanner(System.in);
    System.out.println("Choose the output format for the report:");
    System.out.println("1. TXT");
    System.out.println("2. CSV");
    System.out.println("3. Console");

    int formatChoice = scanner.nextInt();
            ReportFormatter formatter = null;
            switch (formatChoice) {
                case 1:
                    formatter = new TxtReportFormatter();
                    break;
                case 2:
                    formatter = new CsvReportFormatter();
                    break;
                case 3:
                    formatter = new ConsoleReportFormatter();
                    break;
                default:
                    System.out.println("Invalid choice. Defaulting to console output.");
                    formatter = new ConsoleReportFormatter();
                    break;
            }
            
                        while (true) {
                // options the user can pick from
                System.out.println("Choose a report to generate:");
                System.out.println("1. Course Report");
                System.out.println("2. Student Report");
                System.out.println("3. Lecturer Report");
                System.out.println("4. Exit Program");

                int choice = scanner.nextInt();
                                ReportGenerator reportGenerator = null;

                
                // call the appropriate report generator depending what the user chooses.
                switch (choice) {
                    case 1:
                        reportGenerator = new CourseReportGenerator();
                        break;
                    case 2:
                        reportGenerator = new StudentReportGenerator();
                        break;
                    case 3:
                        reportGenerator = new LecturerReportGenerator();
                        break;
                    case 4:
                        System.out.println("Exiting program...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please choose a number between 1 and 4.");
                        continue;
                }
                reportGenerator.generateReport(connection, formatter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
