/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filehandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Capitania
 */
public class CSVFileHandler {

    private static final String CSV_FILE_PATH = "users.csv";

    // Method to add a new user to the CSV file
    public static void addUser(String username, String password, String role) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", true))) {
            writer.write(username + "," + password + "," + role);
            writer.newLine();
            System.out.println("User added successfully.");
        } catch (IOException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    // Method to read users from the CSV file
    public static List<String[]> readUsers() {
        List<String[]> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                users.add(userData);
            }
        } catch (IOException e) {
            System.err.println("Error reading users: " + e.getMessage());
        }
        return users;
    }
}