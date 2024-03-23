/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userManagement;

/**
 *
 * @author Capitania
 */
public class AccessControl {
    // Check if the user has permission to generate a Lecturer Report
    public static boolean canGenerateLecturerReport(User user) {
        return user.getRole() == Role.LECTURER;
    }
        public static boolean canGenerateReports(Role role) {
        return role == Role.OFFICE;
    }
    public static boolean canChangeUsernameAndPassword(Role role) {
        return true; 
    }
}
