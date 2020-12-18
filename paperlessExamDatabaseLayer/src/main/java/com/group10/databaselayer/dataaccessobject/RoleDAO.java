package com.group10.databaselayer.dataaccessobject;

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
public class RoleDAO {
    @Autowired
    IRoleRepository roleRepository;

    //    Roles

    /**
     * Method will return the user filtered by id
     *
     * @param roleDeserialized
     * @return the list of all users
     */
    public Role getRoleByName(String roleDeserialized) {
        return roleRepository.findByName(roleDeserialized);
    }
}
