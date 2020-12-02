package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordException;
import com.group10.paperlessexamwebservice.service.exceptions.user.UsernameFoundException;
import com.group10.paperlessexamwebservice.service.exceptions.user.UserNotFound;
import com.group10.paperlessexamwebservice.service.exceptions.user.UsernameNotMatchEmail;

import java.util.List;

public interface IUserService {

    User logInUser(User user) throws ServiceNotAvailable, PasswordException;
    User createUser(User user) throws UsernameNotMatchEmail, PasswordException, ServiceNotAvailable, UsernameFoundException;

//    Returns the list of all stored users
    List<User> getAllUsersList();

    List<User> getUsersByFirstName(String firstName) throws ServiceNotAvailable, UserNotFound;

  User getUsersByUsername(String username) throws ServiceNotAvailable, UserNotFound;
}
