package com.group10.databaselayer.controller;

import com.group10.databaselayer.exception.ResourceNotFoundException;
import com.group10.databaselayer.repository.IUserRepository;
import com.group10.databaselayer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing login, create, find users requests
 *
 * @author Marin Bilba
 * @version 1.0
 */
@RestController
public class UserController {
    @Autowired
    IUserRepository userRepository;

    /**
     * Method will return all users from the database
     *
     * @return the list of all users
     */
    @RequestMapping(value = "/getUsersList", method = RequestMethod.GET)
    public List<User> getAllUsersList() {
        System.out.println("Yess");
        return userRepository.findAll();
    }

    /**
     * Method will return the users filtered by name
     *
     * @return the list of all users
     */
    @RequestMapping(value = "/getUsersByLastName/{lastName}", method = RequestMethod.GET)
    public List<User> getUsersByName(@PathVariable(value = ("lastName")) String lastName) {
        System.out.println(lastName);
        return userRepository.findByLastName(lastName);
    }

    /**
     * Method will return the user filtered by id
     *
     * @return the list of all users
     */
    @RequestMapping(value = "/getUserById/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable(value = ("id")) long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

    }
    /**
     * Method will return the user filtered by id
     *
     * @return the list of all users
     */
    @RequestMapping(value = "/getUserByUsername/{username}", method = RequestMethod.GET)
    public User getUserById(@PathVariable(value = ("username")) String username) {
        System.out.println(username);
        return new User("ASD","12345");
    }

    /**
     * Method handles the create user request.
     *
     * @param user
     * @return the create user object
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @RequestMapping("/updateUser/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRoles(user.getRoles());
        return userRepository.save(existingUser);
    }

    /**
     * Method will search the user in the database by the given {@userId}.
     *
     * @param userId user id that should be deleted
     * @return If the user
     *       exists in the database a 200 OK status code will be returned to the client including a confirmation
     *       String message with the id and username of the delete user in the http body.
     */
    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(existingUser);
        return ResponseEntity.status(HttpStatus.OK).body("The user with the id: " + existingUser.getId() +
                " and username: " + existingUser.getUsername() + " was deleted from the system");
    }

}
