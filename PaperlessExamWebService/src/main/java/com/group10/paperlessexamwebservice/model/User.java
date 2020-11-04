package com.group10.paperlessexamwebservice.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a User
 *
 * @author Marin Bilba
 * @version 1.0
 */
@Entity
//@Table(name = "users")
public class User {
    // Identity generation type will let the Database to generate the PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //   @Column(name="id",updatable = true,nullable = false)
    private Long id;
    //   @Column(name="firstName")
    private String firstName;
    //   @Column(name="lastName")
    private String lastName;
    //  @Column(name = "username")
    private String username;
    //  @Column(name = "email")
    private String email;
    //@Column(name = "password")
    private String password;
    @Transient
    private String confirmPassword;
    @OneToOne(targetEntity = Role.class, cascade = CascadeType.ALL)
    private Role roles;

    public User() {
    }

    /**
     * Constructor for login functionality
     *
     * @param username
     * @param password
     * @param roles
     */
    public User(String username, String password, Role roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
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
