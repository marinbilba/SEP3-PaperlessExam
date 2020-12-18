package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.user.*;
import com.group10.paperlessexamwebservice.service.user.IUserService;
import org.apache.http.client.HttpResponseException;
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
 * @version 1.1
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * Post Method for login user. It is processing POST request with User object in format of JSON as an argument.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/user/login
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
     * @throws PasswordException     the password exception
     * @throws HttpResponseException the http response exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUser(@RequestBody User user) throws PasswordException, HttpResponseException {
        User temp;
        try {
            temp = userService.logInUser(user);
        } catch (com.group10.paperlessexamwebservice.service.exceptions.user.UsernameNotFoundException | PasswordException e) {
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
     * http://{host}:8080/user/createUser
     *
     * <b>BODY</b>:
     * {
     * <p>
     * "firstName":"Silvestru",
     * "lastName":"Mandrila",
     * "username":"silvmandrila",
     * "email":"silvmandrila@va.cs",
     * "password":"111111",
     * "confirmPassword":"111111",
     * "role":{
     * "name":"Student"
     * }
     * }
     *
     * @param user User object in JSON format
     * @return <i>HTTP 200 - OK</i> code if the created user passes validation process
     * <i>HTTP 409 - CONFLICT</i> code if the username already exists, the username
     * does not match the substring of the email until the '@' char or email does not contain character '@'
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     * <i>HTTP 401 - UNAUTHORIZED</i> code if the password does not match with the confirm password field
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User temp = null;
        System.out.println(user.getConfirmPassword());
        try {
            temp = userService.createUser(user);
        } catch (UsernameFoundException | UsernameNotMatchEmail | EmailException | UnexpectedError e) {
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


    /**
     * Get Method that fetches the users in the system by their firstname. Firstname should be pass as path parameter in the URI
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/user/getUsersByFirstName/test123
     * </p>
     *
     * @param firstName users firstname that should be filtered from the database
     * @return <i>HTTP 200 - OK</i> with the found list of users
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     * <i>HTTP 400 - BAD_REQUEST</i> if the first name was not found in the system
     */
    @RequestMapping(value = "/getUsersByFirstName/{firstName}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUsersByFirstName(@PathVariable String firstName) {

        List<User> userList = null;
        try {
            userList = userService.getUsersByFirstName(firstName);
        } catch (ServiceNotAvailable e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (UserNotFound e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    /**
     * Get Method for user by username. Username should be pass as path parameter in the URI
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/user/getUserByUsername/test123
     * </p>
     *
     * @param username the username that should be found
     * @return <i>HTTP 200 - OK</i> with the found user object if the passed username was found in the system
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     * <i>HTTP 400 - BAD_REQUEST</i> if the username was not found in the system
     */
    @RequestMapping(value = "/getUserByUsername/{username}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        User user = null;
        try {
            user = userService.getUsersByUsername(username);
        } catch (ServiceNotAvailable e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (UserNotFound e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Get Method for user <i>STUDENTS</i> by username. Username should be pass as path parameter in the URI
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/user/getUserByUsername/test123
     * </p>
     *
     * @param username the username that should be found
     * @return <i>HTTP 200 - OK</i> with the found user object if the passed username was found in the system
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     * <i>HTTP 400 - BAD_REQUEST</i> if the username was not found in the system
     */
    @RequestMapping(value = "/getUserStudentByUsername/{username}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserStudentByUsername(@PathVariable String username) {
        User user = null;
        try {
            user = userService.getUserStudentByUsername(username);
        } catch (ServiceNotAvailable e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (UserNotFound e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Get Method that fetches only <i>STUDENT</i> users in the system by their firstname. Firstname should be pass as path parameter in the URI
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/user/getUsersStudentsByFirstName/test123
     * </p>
     *
     * @param firstName users firstname that should be filtered from the database
     * @return <i>HTTP 200 - OK</i> with the found list of users
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     * <i>HTTP 400 - BAD_REQUEST</i> if the username was not found in the system
     */
    @RequestMapping(value = "/getUsersStudentsByFirstName/{firstName}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUsersStudentsByFirstName(@PathVariable String firstName) {

        List<User> userList = null;
        try {
            userList = userService.getUsersStudentsByFirstName(firstName);
        } catch (ServiceNotAvailable e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (UserNotFound e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    /**
     * Update user. Method processed as a POST request requiring a <i>User object</i> in format of JSON
     * as an argument that should be updated.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/user/updateUser
     *
     * <b>BODY</b>:
     * {
     * <p>
     * "firstName":"Silvestru",
     * "lastName":"Mandrila",
     * "username":"silvmandrila",
     * "email":"silvmandrila@va.cs",
     * "password":"111111",
     * "confirmPassword":"111111",
     * "role":{
     * "name":"Student"
     * }
     * }
     * </p>
     *
     * @param user User object in JSON format
     * @return <i>HTTP 200 - OK</i> code if the updated user passes validation process
     * <i>HTTP 400 - BAD_REQUEST</i> if the username was not found in the system, user object fields are null
     * or any kind of unexpected error.
     * <i>HTTP 409 - CONFLICT</i> code if the username was not found in the system, the username
     * does not match the substring of the email until the '@' char or email does not contain character '@'
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        User temp = null;

        try {
            temp = userService.updateUser(user);
        } catch (UnexpectedError | NullFieldUser | com.group10.paperlessexamwebservice.service.exceptions.user.UsernameNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (UsernameNotMatchEmail | PasswordException | EmailException | UserNotFound e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(temp);
    }

    /**
     * Delete user. Method processed as a POST request requiring a <i>User object</i> in format of JSON
     * as an argument that should be deleted.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/user/deleteUser
     *
     * <b>BODY</b>:
     * {
     * <p>
     * "firstName":"Silvestru",
     * "lastName":"Mandrila",
     * "username":"silvmandrila",
     * "email":"silvmandrila@va.cs",
     * "password":"111111",
     * "confirmPassword":"111111",
     * "role":{
     * "name":"Student"
     * }
     * }
     * </p>
     *
     * @param user User object in JSON format
     * @return <i>HTTP 200 - OK</i> code if the updated user passes validation process
     * <i>HTTP 409 - CONFLICT</i> code if the username was not found in the system
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(@RequestBody User user) {
        User temp = null;
        try {
            temp = userService.deleteUser(user);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(temp);
    }


}

