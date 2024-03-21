/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connectors;

import interfaces.DatabaseConnector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Capitania
 */
public class DBConnector implements DatabaseConnector{
    
// URL for connecting to my database and Strings are for username and password
    private static final String DB_URL = "jdbc:mysql://localhost/course_management_system";
    private static final String USER = "pooa2024";
    private static final String PASSWORD = "pooa2024";
    
// Gets a connection to the database and with login in the return part
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
