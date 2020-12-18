package com.group10.paperlessexamwebservice.model.user;


/**
 * Simple JavaBean object that represents role of {@link User}.
 *
 * @author Marin Bilba
 * @version 1.0
 */

public class Role {
    private int id;
    private String name;

    public Role() {
    }

    /**
     * Instantiates a new Role.
     *
     * @param id
     * @param name
     */
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
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
