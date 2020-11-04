package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.IUserService;
import com.group10.paperlessexamwebservice.service.exceptions.user.EmailNotFoundException;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        System.out.println("Call post");
       return userService.logInUser(user);

    }

    /**
     *  Method handles the create request. It calls the user service passing the user object.
     * @param user
     * @return
     * @throws EmailNotFoundException exception in case the user's email matches the email of another user in the database
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createUser(@RequestBody User user) throws EmailNotFoundException {
        System.out.println("Call post");
       return userService.createUser(user);
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


}
