package com.group10.paperlessexamwebservice.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Set;


/**
 * Simple JavaBean object that represents role of {@link User}.
 *
 * @author Marin Bilba
 * @version 1.0
 */

public class Role {
    private int id;
    private String name;

    /**
     * Instantiates a new Role.
     * @param i
     * @param sda
     */
        public Role(int i, String sda) {
    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
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
