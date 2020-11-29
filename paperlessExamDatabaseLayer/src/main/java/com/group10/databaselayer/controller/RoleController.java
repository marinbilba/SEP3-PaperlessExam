package com.group10.databaselayer.controller;

import com.group10.databaselayer.entity.user.Role;
import com.group10.databaselayer.repositories.user.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for managing roles
 *
 * @author Marin Bilba
 * @version 1.0
 */

@Component
public class RoleController {
    @Autowired
    IRoleRepository roleRepository;

    //    Roles
    /**
     * Method will return the user filtered by id
     *
     * @return the list of all users
     * @param roleDeserialized
     */
    public Role getRoleByName(String roleDeserialized) {
        return roleRepository.findByName(roleDeserialized);
    }
}
