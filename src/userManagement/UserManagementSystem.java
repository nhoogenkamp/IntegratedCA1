/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Capitania the user managementsystem class manages users including
 * authentication, modification, deleting, and addition. It also reads from and
 * writes to a CSV file to store user data like username, password and role.
 */
public class UserManagementSystem {

    //list to hold my users objects and a file path to the csv file where they users are stored.
    private List<User> users;
    private static final String CSV_FILE_PATH = "users.csv";

    /**
     * Constructor for UserManagementSystem which Initializes the list of users
     * and loads users from the CSV file.
     */
    public UserManagementSystem() {
        this.users = new ArrayList<>();
        // Load users from CSV file
        readUsersFromCSV();
        // Initial user
        users.add(new User("admin", "java", Role.ADMIN));
    }

    /**
     * Authenticates a user based on username and password. Reads user data from
     * the CSV file and checks for a match. the userauthentification is
     * successfull otherwhile will return a null.
     */
    public User authenticate(String username, String password) {
        boolean firstLine = true;
        try ( BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // I had some trouble for the file reader to read my headers and without so I opted to skip first line instead. 
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] userData = line.split(",");
                if (userData.length == 3) {
                    String csvUsername = userData[0].trim();
                    String csvPassword = userData[1].trim();
                    String csvRole = userData[2].trim();
                    // output for role value and convert to enum, when user is found return user object.
                    System.out.println("CSV Role: " + csvRole);
                    Role userRole = Role.valueOf(csvRole.toUpperCase());
                    if (csvUsername.equals(username) && csvPassword.equals(password)) {
                        return new User(username, password, userRole);
                    }
                }
            }

            /**
             * had some trouble reading from csv file so had different catch
             * method to try finding what the issue was. in the end it turned
             * out to be reading from the first line problem with my headers
             * which i have deleted and start from row 2
             */
        } catch (IOException e) {
            System.out.println("Error reading users from CSV: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role found in CSV: " + e.getMessage());
        }
        // User not found or error occurred, return null
        return null;
    }

    // modifies the password and role of the user.
    public void modifyUser(String username, String newPassword, Role newRole) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setPassword(newPassword);
                user.setRole(newRole);
                // Update CSV file
                writeUsersToCSV();
                return;
            }
        }
        // Handle case where user is not found
        System.out.println("User not found.");
    }

    // deletes a user from the csv file/system.
    public void deleteUser(String username) {
        users.removeIf(user -> user.getUsername().equals(username));
        // Update CSV file
        writeUsersToCSV();
    }

    // changing of the username by converting the getusername into oldusername and then type in new username.
    public void changeUsername(String oldUsername, String newUsername) {
        for (User user : users) {
            if (user.getUsername().equals(oldUsername)) {
                user.setUsername(newUsername);
                // Update CSV file
                writeUsersToCSV();
                return;
            }
        }
        // Handle case where user is not found
        System.out.println("User not found.");
    }
    
    // changing password by providing a username and then change password and write that in the csv file.
    public void changePassword(String username, String newPassword) {
        boolean userFound = false;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setPassword(newPassword);
                userFound = true;
                // Update CSV file
                writeUsersToCSV();
                System.out.println("Password changed successfully.");
                break;
            }
        }
        if (!userFound) {
            System.out.println("User not found.");
        }
    }
    
    // retrieves the list of users and return it.
    public List<User> getUsers() {
        return users;
    }

    // add new user and passes it onto addusertocsv to write it in the csv file.
    public void addUser(String username, String password, Role role) {
        users.add(new User(username, password, role));
        addUserToCSV(new User(username, password, role));
    }

    // adds new users to the writetocsv method.
    public void addUserToCSV(User user) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", true))) {
            String userData = user.getUsername() + "," + user.getPassword() + "," + user.getRole().toString();
            writer.write(userData);
            writer.newLine();
            System.out.println("User added successfully to CSV.");
        } catch (Exception e) {
            System.out.println("Error adding user to CSV: " + e.getMessage());
        }
    }

    // readd user data from csv file and poplates the users list.
    private void readUsersFromCSV() {
        try ( BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty or whitespace-only lines
                if (line.trim().isEmpty()) {
                    System.out.println("Empty line or whitespace found in CSV.");
                    continue;
                }
                String[] userData = line.split(",");
                if (userData.length < 3) {
                    System.out.println("Invalid data format in CSV file: " + line);
                    continue;
                }
                String username = userData[0].trim();
                String password = userData[1].trim();
                String role = userData[2].trim();
                try {
                    Role userRole = Role.valueOf(role.toUpperCase());
                    // Create a new User object
                    User newUser = new User(username, password, userRole);
                    // Add the new User object to the users list
                    users.add(newUser);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid role found in CSV: " + role);
                }
            }
            System.out.println("Users loaded successfully from CSV.");
        } catch (Exception e) {
            System.out.println("Error reading users from CSV: " + e.getMessage());
        }
    }

    // writes the list of users to the csv file.
private void writeUsersToCSV() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv"))) {
        // Write all users to the CSV file
        for (User user : users) {
            String userData = user.getUsername() + "," + user.getPassword() + "," + user.getRole().toString();
            writer.write(userData);
            writer.newLine();
        }
        System.out.println("Users updated successfully in CSV.");
    } catch (Exception e) {
        System.out.println("Error updating users in CSV: " + e.getMessage());
    }
}
}
