package com.group10.databaselayer.controller;

import com.group10.databaselayer.entity.User;
import com.group10.databaselayer.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserController {
    @Autowired
    IUserRepository userRepository;

    /**
     * Method will return the user filtered by id
     *
     * @return the list of all users
     */

    public User getUserByUsername(String username) {
        System.out.println("sad");
        return userRepository.getUserByUsername(username);
    }
    /**
     * Method handles the create user request.
     *
     * @param user
     * @return the create user object
     */

    public User createUser(User user) {
        System.out.println(user.getFirstName());
        System.out.println(user.getRole().getName());
        User user2= userRepository.save(user);
        System.out.println("suca");
        return user2;
    }

}
