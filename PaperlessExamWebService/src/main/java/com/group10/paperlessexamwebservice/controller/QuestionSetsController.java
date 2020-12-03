package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordException;
import com.group10.paperlessexamwebservice.service.questionsets.IQuestionSetsService;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing question sets, create, find, delete requests
 *
 * @author Marin Bilba
 * @version 1.0
 */
@RestController
@RequestMapping("/questionSets")
public class QuestionSetsController {

    @Autowired
    private IQuestionSetsService questionSetsService;

//
//    @RequestMapping(value = "/createMultipleChoiceQuestionSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> createMultipleChoiceQuestionSet(@RequestBody Multiple user) throws PasswordException, HttpResponseException {
//        User temp;
//        try {
//            temp = userService.logInUser(user);
//        } catch (UsernameNotFoundException | PasswordException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
//        } catch (ServiceNotAvailable e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(temp);
//    }

}
