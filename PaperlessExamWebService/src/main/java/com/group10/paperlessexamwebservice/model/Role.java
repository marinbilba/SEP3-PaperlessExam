package com.group10.paperlessexamwebservice.model;

import javax.persistence.*;
import java.util.Set;


/**
 * Simple JavaBean object that represents role of {@link User}.
 *
 * @author Marin Bilba
 * @version 1.0
 */
public class Role {
    private Long id;
    private String name;

    /**
     * Instantiates a new Role.
     */
    public Role() {
    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
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
