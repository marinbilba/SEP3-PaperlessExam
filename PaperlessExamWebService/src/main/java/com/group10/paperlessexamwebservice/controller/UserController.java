package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.Role;
import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.IUserService;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.user.DataBaseException;
import com.group10.paperlessexamwebservice.service.exceptions.user.EmailException;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
     * Post Method for login user. It is processing POST request with User object in format of JSON as an argument.
     * <p>
     *  <b>EXAMPLE</b>:
     *
     *  http://{host}:8080/login
     *
     *  <b>BODY</b>:
     *  {
     * 	    "username": "Test",
     * 	    "password": "123456"
     *  }
     * </p>
     *
     * @param user User object in JSON format
     * @return <i>HTTP 200 - OK</i> code if credentials are verified. Returns <i>HTTP 403 - FORBIDDEN</i> if credentials are incorrect.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUser(@RequestBody User user) throws PasswordNotFoundException, HttpResponseException {
        User temp;
        try {
            temp = userService.logInUser(user);
        } catch (UsernameNotFoundException | PasswordNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ServiceNotAvailable e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(temp);
    }

    /**
     * Method handles the create request. It calls the user service passing the user object.
     *
     * @param user
     * @return
     * @throws EmailException exception in case the user's email matches the email of another user in the database
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createUser(@RequestBody User user) throws EmailException {
        System.out.println("Call post");
        System.out.println("test2");
        return null;
    }

    /**
     * Method will return all users from the database
     *
     * @return the list of all users
     */
    @RequestMapping(value = "/getUsersList", method = RequestMethod.GET)
    public List<User> getAllUsersList() {
        System.out.println("Call post");
        return userService.getAllUsersList();
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody User user) {
        return null;

    }

}
