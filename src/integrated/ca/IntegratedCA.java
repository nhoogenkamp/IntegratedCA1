/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integrated.ca;

import interfaces.ReportGenerator;
import Reports.CourseReportGenerator;
import Connectors.DBConnector;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Capitania * 
 * SBA23047
 * Niels Hoogenkamp
 * 
 * Main class for generating reports in my IntegratedCA system
 * This class creates a database connection, generates a course report, and handles any SQL exceptions.
 */
public class IntegratedCA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // create an instance of the DBConnector class
        DBConnector db = new DBConnector();
        try ( Connection connection = db.getConnection()) {
        // Generate Course Report in my try catch and if there is a problem e will print what the problem is.
            ReportGenerator courseReportGenerator = new CourseReportGenerator();
            courseReportGenerator.generateReport(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
