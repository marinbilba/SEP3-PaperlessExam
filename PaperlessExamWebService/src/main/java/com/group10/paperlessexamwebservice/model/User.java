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
 //   @Column(name="id",updatable = false,nullable = false)
    private Long id;
    //  @Column(name = "username")
    private String username;
    //@Column(name = "password")
    private String password;
    @Transient
    private String confirmPassword;
@OneToOne(targetEntity = Role.class, cascade = CascadeType.ALL)
 private Role roles;

    public User() {
    }

    public User(String username, String password, String confirmPassword, Role role) {

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

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }
}
