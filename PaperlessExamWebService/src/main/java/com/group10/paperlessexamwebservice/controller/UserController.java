package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing login and create users requests
 *
 * @author Marin Bilba
 * @version 1.1
 */
@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    private IUserService userService;


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void loginUser(@RequestBody User user) {
        userService.logInUser(user);
        System.out.println("Call post");
    }
}
