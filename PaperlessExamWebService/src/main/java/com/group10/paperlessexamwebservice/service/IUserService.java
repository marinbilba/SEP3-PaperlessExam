package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.user.EmailException;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;

import java.util.List;

public interface IUserService {
    User logInUser(User user) throws PasswordNotFoundException;
    String createUser(User user) throws EmailException;

//    Returns the list of all stored users
    List<User> getAllUsersList();

}
