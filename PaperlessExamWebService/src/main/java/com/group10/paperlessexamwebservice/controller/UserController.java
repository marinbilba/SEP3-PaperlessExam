package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.Role;
import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.IUserService;
import com.group10.paperlessexamwebservice.service.exceptions.user.DataBaseException;
import com.group10.paperlessexamwebservice.service.exceptions.user.EmailException;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
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
    public User loginUser(@RequestBody User user) throws PasswordNotFoundException, HttpResponseException {

        System.out.println("test 555");
        User temp = null;
        try {
            temp = userService.logInUser(user);
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        return temp;

    }

    /**
     *  Method handles the create request. It calls the user service passing the user object.
     * @param user
     * @return
     * @throws EmailException exception in case the user's email matches the email of another user in the database
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) throws EmailException {
        User temp = null;
        try {
            temp = userService.createUser(user);
        } catch (EmailException e) {
            e.printStackTrace();
        }
        System.out.println("createUser test");
       return temp;
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
