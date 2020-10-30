package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.dao.User;
import com.group10.paperlessexamwebservice.service.IPaperlessExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private IPaperlessExamService paperlessExamService;


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void loginUser(@RequestBody User user) {
        paperlessExamService.logInUser(user);
        System.out.println("Call post");
    }
}
