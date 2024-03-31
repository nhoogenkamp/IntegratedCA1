/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integrated.ca;

import Connectors.DBConnector;
import Reports.CourseReportGenerator;
import Reports.LecturerReportGenerator;
import Reports.StudentReportGenerator;
import formatters.ConsoleReportFormatter;
import formatters.CsvReportFormatter;
import formatters.TxtReportFormatter;
import interfaces.ReportFormatter;
import interfaces.ReportGenerator;
import java.sql.Connection;
import userManagement.Role;
import userManagement.User;
import userManagement.UserManagementSystem;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Capitania * SBA23047 Niels Hoogenkamp
 * Github link: https://github.com/nhoogenkamp/IntegratedCA1.git
 *
 * Main class for generating reports in my IntegratedCA system This class
 * creates a database connection, generates a course/student/lecturer report,
 * and handles any SQL exceptions. With the main method is to entry point of the
 * program. The user can choose between generating 3 different reports depending on your role
 * change password username modify delete or add new users or exit the program.
 */
public class IntegratedCA {

    /**
     * @param args the command line arguments
     */
    private static User loggedInUser;

    public static void main(String[] args) {
        // create an instance of usermanagementSystem
        UserManagementSystem userManagementSystem = new UserManagementSystem();

        try {
            /** attempt user login
            * check user role and display right menu as every role has different needs.
            * Pass userManagementSystem to the right Menu office/lecturer/office.
            * when login fails exit program.
            */
            if (login(userManagementSystem)) {
                System.out.println("Login successful!");

                if (loggedInUser.getRole() == Role.ADMIN) {
                    adminMenu(userManagementSystem);
                } else if (loggedInUser.getRole() == Role.LECTURER) {
                    lecturerMenu(userManagementSystem);
                } else if (loggedInUser.getRole() == Role.OFFICE) {
                    officeMenu(userManagementSystem);
                } else {
                    System.out.println("Login failed. Exiting program...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
            /** method for login
            * it will prompt you with a username and password question and have 3 attempts in a loop to login otherwise the program will exit.
            * it will get the authenticifation from userManagementSystem.authenticate the user and password
            * 
            */
    
    private static boolean login(UserManagementSystem userManagementSystem) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            attempts++;
            System.out.println("Welcome! Please login (Attempts left: " + (maxAttempts - attempts) + "):");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            loggedInUser = userManagementSystem.authenticate(username, password);

            if (loggedInUser != null) {
                return true;
            } else {
                System.out.println("Wrong username or password. Please try again.");
            }
        }

        System.out.println("Maximum login attempts reached. Exiting program...");
        return false;
    }

    
    /* method for admin menu as the requirements were that it can only add, modify and delete a user, can change their own username and password 
    it is not allowed to generate reports. I used a switch case to display the menu options and with scanner you can give your option.
    from there you get to usermanagementsystem where they handle all the other methods for adding, modifying deleting etc.
    */
    private static void adminMenu(UserManagementSystem userManagementSystem) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add User");
            System.out.println("2. Modify User");
            System.out.println("3. Delete User");
            System.out.println("4. Change Own Password");
            System.out.println("5. Change Own Username");
            System.out.println("6. Exit Program");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addUser(userManagementSystem);
                    break;
                case 2:
                    modifyUser(userManagementSystem);
                    break;
                case 3:
                    deleteUser(userManagementSystem);
                    break;
                case 4:
                    changePassword(userManagementSystem);
                    break;
                case 5:
                    changeUsername(userManagementSystem);
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 6.");
            }
        }
    }
    /* method for office menu as the requirements were that it can only generate all reports and change password and username. 
    I used a switch case to display the menu options and with scanner you can give your option.
    from there you get to usermanagementsystem where they handle the methods for change username or password. the generatereports will go to that method more below.
    */
    private static void officeMenu(UserManagementSystem userManagementSystem) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Office Menu:");
            System.out.println("1. Generate Reports");
            System.out.println("2. Change Own Password");
            System.out.println("3. Change Own Username");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    generateReports();
                    break;
                case 2:
                    System.out.println("Enter your username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter your new password:");
                    String newPassword = scanner.nextLine();
                    userManagementSystem.changePassword(username, newPassword);
                    break;
                case 3:
                    System.out.println("Enter your current username:");
                    String oldUsername = scanner.nextLine();
                    System.out.println("Enter your new username:");
                    String newUsername = scanner.nextLine();
                    userManagementSystem.changeUsername(oldUsername, newUsername);
                    break;
                case 4:
                    System.out.println("Exiting Office Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 3.");
            }
        }
    }

    /* method for lecturer menu as the requirements were that it can only generate lecturer reports and change password and username. 
    I used a switch case to display the menu options and with scanner you can give your option.
    from there you get to usermanagementsystem where they handle the methods for change username or password. the generateLecturerReport will go to a specific 
    method lecturereport more below.
    */
    private static void lecturerMenu(UserManagementSystem userManagementSystem) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Lecturer Menu:");
            System.out.println("1. Generate Lecturer Report");
            System.out.println("2. Change Own Password");
            System.out.println("3. Change Own Username");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    generateLecturerReport();
                    break;
                case 2:
                    changePassword(userManagementSystem);
                    break;
                case 3:
                    changeUsername(userManagementSystem);
                    break;
                case 4:
                    System.out.println("Exiting Lecturer Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 4.");
            }
        }
    }

    /* method generate all reports only for office as they are the only ones they can have acces at the moment.
    I used a switch case to display the menu options and with scanner you can give your option.
    I used scanner again for an input if they want output in txt, csv or console.
    based on the user input instantiate the approprate formatter in my formatters package.
    */
    private static void generateReports() throws SQLException {
        try {
            // Create an instance of DBConnector
            DBConnector dbConnector = new DBConnector();
            // Get the connection using the instance
            Connection connection = dbConnector.getConnection();
            if (connection == null) {
                System.out.println("Failed to establish database connection. Exiting...");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            ReportFormatter formatter = null;

            System.out.println("Select output format:");
            System.out.println("1. TXT");
            System.out.println("2. CSV");
            System.out.println("3. Console");
            int formatChoice = scanner.nextInt();
            scanner.nextLine(); 

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
                    System.out.println("Invalid choice. Returning to main menu.");
                    return;

            }

      /* if the above is true the user get's promped with another question which report they would like again, with an input of the scanner
            it will call the appropriate report in my reports package.
    */
      while (true) {
                // Options the user can pick from
                System.out.println("Choose a report to generate:");
                System.out.println("1. Course Report");
                System.out.println("2. Student Report");
                System.out.println("3. Lecturer Report");
                System.out.println("4. Exit Program");

                int choice = scanner.nextInt();
                ReportGenerator reportGenerator = null;

                // Call the appropriate report generator depending on what the user chooses.
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
                // Generate the report using the selected generator and formatter
                reportGenerator.generateReport(connection, formatter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error establishing database connection or executing SQL query.");
        }

    }

        /* method generate lecturersreport as they are only having access to their own report.
    I used a switch case to display the menu options and with scanner you can give your option.
    I used scanner again for an input if they want output in txt, csv or console.
    based on the user input instantiate the approprate formatter in my formatters package.
    */
    private static void generateLecturerReport() {
        try {
            // Create an instance of DBConnector
            DBConnector dbConnector = new DBConnector();
            // Get the connection using the instance
            Connection connection = dbConnector.getConnection();
            if (connection == null) {
                System.out.println("Failed to establish database connection. Exiting...");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            ReportFormatter formatter = null;

            // Select output format
            System.out.println("Select output format:");
            System.out.println("1. TXT");
            System.out.println("2. CSV");
            System.out.println("3. Console");
            int formatChoice = scanner.nextInt();
            scanner.nextLine(); 

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
                    System.out.println("Invalid choice. Returning to main menu.");
                    return;
            }

            while (true) {
                // Options the user can pick from
                System.out.println("Choose a report to generate:");
                System.out.println("1. Lecturer Report");
                System.out.println("2. Exit Program");

                int choice = scanner.nextInt();
                ReportGenerator reportGenerator = null;

                // Call the appropriate report generator depending on what the user chooses.
                switch (choice) {
                    case 1:
                        reportGenerator = new LecturerReportGenerator();
                        break;
                    case 2:
                        System.out.println("Exiting program...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please choose a number between 1 and 2.");
                        continue;
                }

                reportGenerator.generateReport(connection, formatter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error establishing database connection or executing SQL query.");
        }
    }
    /* methods for adding users, modify users, delete, change password and change username are all using scanner to input the data and called
    to a method in usermanagemenstsystem to act accordingly eg change password, add user etc.
    */
    private static void addUser(UserManagementSystem userManagementSystem) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter role (ADMIN, OFFICE, LECTURER):");
        Role role = Role.valueOf(scanner.nextLine().toUpperCase());

        userManagementSystem.addUser(username, password, role);
    }
    // modify user is to change the password and Role only as when someone will move department there is no need for a new username.
    private static void modifyUser(UserManagementSystem userManagementSystem) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username of the user to modify:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        
        // authoenticate user to make sure the user exsists.
        User user = userManagementSystem.authenticate(username, password);
        
        // when user exists enter a new password and new role but we are keeping the username.    
        if (user != null) {
            System.out.println("Enter new password:");
            String newPassword = scanner.nextLine();
            System.out.println("Enter new role (ADMIN, OFFICE, LECTURER):");
            Role newRole = Role.valueOf(scanner.nextLine().toUpperCase());

            userManagementSystem.modifyUser(username, newPassword, newRole);
            System.out.println("User modified successfully.");
        } else {
            System.out.println("Authentication failed. User not found or incorrect password.");
        }
    }

    // method to delete a user.    
    private static void deleteUser(UserManagementSystem userManagementSystem) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username of the user to delete:");
        String username = scanner.nextLine();

        userManagementSystem.deleteUser(username);
    }

    // method to change a password.
private static void changePassword(UserManagementSystem userManagementSystem) {
    Scanner scanner = new Scanner(System.in);
    String loggedInUsername = loggedInUser.getUsername(); // Get the username of the logged-in user
    System.out.println("Enter your new password:");
    String newPassword = scanner.nextLine();

    userManagementSystem.changePassword(loggedInUsername, newPassword); // Pass only the username of the logged-in user
}

private static void changeUsername(UserManagementSystem userManagementSystem) {
    Scanner scanner = new Scanner(System.in);
    String loggedInUsername = loggedInUser.getUsername(); // Get the username of the logged-in user
    System.out.println("Enter your new username:");
    String newUsername = scanner.nextLine();

    userManagementSystem.changeUsername(loggedInUsername, newUsername); // Pass only the username of the logged-in user
}

}
