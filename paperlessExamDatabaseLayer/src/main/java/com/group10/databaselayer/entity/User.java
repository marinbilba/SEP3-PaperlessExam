package com.group10.databaselayer.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

/**
 * Simple JavaBean domain object that represents a User
 *
 * @author Marin Bilba
 * @version 1.0
 */
@Entity
@Table(name = "users")
public class User {
    // Identity generation type will let the Database to generate the PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "username",unique = true)
    private String username;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "password")
    private String password;
   @OneToOne
    @NotNull
    @JoinColumn(name="fk_role_id")
    private Role role;
    public User(){

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
     * @param email
     */
    public User(Long id, String firstName, String lastName, String username, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;

    }




    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

