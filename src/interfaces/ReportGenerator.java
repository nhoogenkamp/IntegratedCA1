/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *  
 * @author Capitania
 * 
 * This interface named reportgenerator has currently one method which is to generate a report with my pooa2024 database connection.
 * SQLException if there is any access to my database errors 
 */
public interface ReportGenerator {
     void generateReport(Connection connection) throws SQLException;
}
