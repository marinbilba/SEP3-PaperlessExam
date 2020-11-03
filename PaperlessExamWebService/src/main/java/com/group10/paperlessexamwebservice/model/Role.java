package com.group10.paperlessexamwebservice.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean object that represents role of {@link User}.
 *
 * @author Marin Bilba
 * @version 1.0
 */

@Entity
//@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name="id",updatable = false,nullable = false)
    private Long id;
    //  @Column(name = "name")
    private String name;


    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
