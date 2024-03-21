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
 * This interface is to make a connection to my database.
 * SQLException if there is any access to my database errors 
 */
public interface DatabaseConnector {
    Connection getConnection() throws SQLException;
}
