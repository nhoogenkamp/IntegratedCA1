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
 * This interface named reportgenerator which has contracts with reports to generate reports.
 * SQLException if there is any access to my database errors 
 */
public interface ReportGenerator {
     void generateReport(Connection connection, ReportFormatter formatter) throws SQLException;
}
