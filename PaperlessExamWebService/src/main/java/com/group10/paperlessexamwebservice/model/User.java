package com.group10.paperlessexamwebservice.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a User
 *
 * @author Marin Bilba
 * @version 1.0
 */
@Component
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    private Role roles;

    public User() {
    }

    /**
     * Constructor for login functionality
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor for create user functionality
     *
     * @param username
     * @param password
     * @param confirmPassword
     * @param role
     */
    public User(String firstName, String lastName, String username, String password, String confirmPassword, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }
}
