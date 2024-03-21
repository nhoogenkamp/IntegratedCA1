/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrated.ca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Capitania
 */
public class DBConnector {
    private final String DB_URL = "jdbc:mysql://localhost/course_management_system";
//  variables for username and password
    private final String USER = "pooa2024";
    private final String PASSWORD = "pooa2024";
    
    //  method for creating a table in the database
    public void createTable(String tableName) {
        try {
            // Making a connection to the database        
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            // statement to create sql queries
            Statement stmt = conn.createStatement();
            // use the specified database and queries and making sure by sout printing that it was a success.
            stmt.execute("USE course_management_system;");
            stmt.execute("CREATE TABLE " + tableName + " (accountNum INT, balance INT, name VARCHAR(30));");
            System.out.println("Table sucessfully created");
            // closing the database connection.
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
}
