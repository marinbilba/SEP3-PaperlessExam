package com.group10.databaselayer.controller;

import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.repositories.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<User> getUsersListByFirstName(String firstNameDeserialized) {
       return userRepository.findByFirstNameIgnoreCaseContaining(firstNameDeserialized);
    }
}
