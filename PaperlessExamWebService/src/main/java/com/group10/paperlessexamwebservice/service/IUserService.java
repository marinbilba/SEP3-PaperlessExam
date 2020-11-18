package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.user.DataBaseException;
import com.group10.paperlessexamwebservice.service.exceptions.user.EmailException;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;

import java.io.IOException;
import java.util.List;

public interface IUserService {

    User logInUser(User user) throws ServiceNotAvailable, PasswordNotFoundException;
    User createUser(User user) throws Exception;

//    Returns the list of all stored users
    List<User> getAllUsersList();

}
