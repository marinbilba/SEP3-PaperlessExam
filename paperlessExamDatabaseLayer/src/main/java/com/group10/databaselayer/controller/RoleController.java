package com.group10.databaselayer.controller;

import com.group10.databaselayer.entity.Role;
import com.group10.databaselayer.entity.User;
import com.group10.databaselayer.repository.IRoleRepository;
import com.group10.databaselayer.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing roles
 *
 * @author Marin Bilba
 * @version 1.0
 */

@RestController
public class RoleController {
    @Autowired
    IRoleRepository roleRepository;

    //    Roles
    /**
     * Method will return the user filtered by id
     *
     * @return the list of all users
     */
    @RequestMapping(value = "/getRoleByName/{roleName}", method = RequestMethod.GET)
    public Role getUserById(@PathVariable(value = ("roleName")) String roleName) {
        System.out.println(roleName);
        return roleRepository.findByName(roleName);
    }


}
