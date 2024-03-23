/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userManagement;

/**
 *
 * @author Capitania
 * this javaclass user represents a user entity in my project.
 * my attributes users represent username-user, password-password of the user, role- role of the user which can only be: ADMIN, OFFICE OR LECTURER.
 * These methods allow for accessing and modifying the attributes of a User object.
 */

// represents a user with a username, password and role
public class User {
    private String username;
    private String password;
    private Role role;

    // construcs a new user object with specified username, passsword and role.
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
/*
 * all getter methods are getting the username, password or role 
 * while all setters are setting them to be used.
 */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}