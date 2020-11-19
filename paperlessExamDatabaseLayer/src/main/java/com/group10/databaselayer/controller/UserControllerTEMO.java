package com.group10.databaselayer.controller;

import com.group10.databaselayer.exception.ResourceNotFoundException;
import com.group10.databaselayer.repository.IUserRepository;
import com.group10.databaselayer.entity.User;
//import com.group10.databaselayer.repository.IUserRepositoryString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing login, create, find users requests
 *
 * @author Marin Bilba
 * @version 1.2
 */

//@RestController
//@EnableJpaRepositories
@Service
public class UserControllerTEMO {
    IUserRepository userRepository;

    @Autowired
    public UserControllerTEMO(IUserRepository userRepository) {
        this.userRepository = userRepository;

    }


    public ResponseEntity<?> login(User user) {
        if (validateLogin(user.getUsername(), user.getPassword())) {
            User user1 = userRepository.findByUsername(user.getUsername());
            System.out.println(user1.getRole().getName());
            return ResponseEntity.status(HttpStatus.OK).body(user1);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sad");
    }

    /**
     * Method will return all users from the database
     *
     * @return the list of all users
     */

    public List<User> getAllUsersList() {
        System.out.println("Yess");
        return userRepository.findAll();
    }

    /**
     * Method will return the users filtered by name
     *
     * @return the list of all users
     */

    public List<User> getUsersByLastName(String lastName) {
        System.out.println(lastName);
        return userRepository.findByLastName(lastName);
    }

    /**
     * Method will return the user filtered by id
     *
     * @return the list of all users
     */

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

    }

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


    public User updateUser(User user, long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        //   existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
    }

    /**
     * Method will search the user in the database by the given {@userId}.
     *
     * @param userId user id that should be deleted
     * @return If the user
     * exists in the database a 200 OK status code will be returned to the client including a confirmation
     * String message with the id and username of the delete user in the http body.
     */
    public ResponseEntity<String> deleteUser(long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(existingUser);
        return ResponseEntity.status(HttpStatus.OK).body("The user with the id: " + existingUser.getId() +
                " and username: " + existingUser.getUsername() + " was deleted from the system");
    }


    private boolean validateLogin(String username, String password) {
        System.out.println("Username: " + username);
        User user;
        try {
            user = userRepository.findByUsername(username);

        } catch (Exception e) {
            return false;
        }

        if (user == null) {

            return false;
        }
        System.out.println(user.getPassword());
        return user.getPassword().equals(password);

    }

    public void connect() {
        System.out.println("pulamea");
    }
}
