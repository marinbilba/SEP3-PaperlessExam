package com.group10.paperlessexamwebservice.service.user;

import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.user.*;

import java.util.List;

public interface IUserService {

    User logInUser(User user) throws ServiceNotAvailable, PasswordException;
    User createUser(User user) throws UsernameNotMatchEmail, PasswordException, ServiceNotAvailable, UsernameFoundException, EmailException;

//    Returns the list of all stored users
    List<User> getAllUsersList();

    List<User> getUsersByFirstName(String firstName) throws ServiceNotAvailable, UserNotFound;

  User getUsersByUsername(String username) throws ServiceNotAvailable, UserNotFound;
}
