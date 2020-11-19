package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.IUserService;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.user.EmailException;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordException;
import com.group10.paperlessexamwebservice.service.exceptions.user.UsernameFoundException;
import com.group10.paperlessexamwebservice.service.exceptions.user.UsernameNotMatchEmail;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/login
     *
     * <b>BODY</b>:
     * {
     * "username": "Test",
     * "password": "123456"
     * }
     * </p>
     *
     * @param user User object in JSON format
     * @return the user object and <i>HTTP 200 - OK</i> code if credentials are verified.
     * @throws UsernameNotFoundException,PasswordException <i>HTTP 403 - FORBIDDEN</i> code if credentials are incorrect.
     * @throws ServiceNotAvailable                         <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUser(@RequestBody User user) throws PasswordException, HttpResponseException {
        User temp;
        try {
            temp = userService.logInUser(user);
        } catch (UsernameNotFoundException | PasswordException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ServiceNotAvailable e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(temp);
    }

    /**
     * Post Method for create user. It is processing POST request with User object in format of JSON as an argument.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/createUser
     *
     * <b>BODY</b>:
     * {
     * "id":10,
     * "firstName":"Silvestru",
     * "lastName":"Mandrila",
     * "username":"silvmandrila",
     * "email":"silvmandrila@va.cs",
     * "password":"111111",
     * "confirmPassword":"111111",
     * "role":{
     * "id":1,
     * "name":"Student"
     * }
     * }
     * </p>
     *
     * @param user User object in JSON format
     * @return <i>HTTP 200 - OK</i> code if the created user passes validation process
     * <i>HTTP 409 - CONFLICT</i> code if the username already exists or
     *               the username does not match the substring of the email until the '@' char
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     * <i>HTTP 401 - UNAUTHORIZED</i> code if the password does not match with the confirm password field
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody User user) throws EmailException {
        User temp = null;
        try {
            temp = userService.createUser(user);
        } catch (UsernameFoundException | UsernameNotMatchEmail e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ServiceNotAvailable e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (PasswordException e) {
            e.printStackTrace();
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(temp);
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
