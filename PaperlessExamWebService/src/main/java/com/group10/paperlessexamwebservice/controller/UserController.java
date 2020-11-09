package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.Role;
import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.IUserService;
import com.group10.paperlessexamwebservice.service.exceptions.user.EmailException;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing login, create, find users requests
 *
 * @author Marin Bilba
 * @version 1.1
 */
@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * Method handles the login request. It calls the user service passing the username and password.
     * @param user
     * @return user model object
     * @throws PasswordNotFoundException exception in case the user's password does not match the password from the database
     */
    
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User loginUser(@RequestBody User user) throws PasswordNotFoundException {

        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return userService.logInUser(user);

    }

    /**
     *  Method handles the create request. It calls the user service passing the user object.
     * @param user
     * @return
     * @throws EmailException exception in case the user's email matches the email of another user in the database
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createUser(@RequestBody User user) throws EmailException {
        System.out.println("Call post");
       return null;
    }

    /**
     * Method will return all users from the database
     * @return the list of all users
     */
    @RequestMapping(value = "/getUsersList", method = RequestMethod.GET)
    public List<User> getAllUsersList()  {
        System.out.println("Call post");
        return userService.getAllUsersList();
    }
@RequestMapping(value = "/updateUser",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
public User updateUser(@RequestBody User user){
        return null;

}

}
