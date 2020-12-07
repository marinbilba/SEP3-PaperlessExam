package com.group10.paperlessexamwebservice.service.user;

import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.user.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService {

    User logInUser(User user) throws ServiceNotAvailable, PasswordException, com.group10.paperlessexamwebservice.service.exceptions.user.UsernameNotFoundException;
    User createUser(User user) throws UsernameNotMatchEmail, PasswordException, ServiceNotAvailable, UsernameFoundException, EmailException, UnexpectedError;

//    Returns the list of all stored users
    List<User> getAllUsersList();

    List<User> getUsersByFirstName(String firstName) throws ServiceNotAvailable, UserNotFound;

  User getUsersByUsername(String username) throws ServiceNotAvailable, UserNotFound;

    User updateUser(User user) throws UsernameNotFoundException, NullFieldUser, ServiceNotAvailable, UnexpectedError, EmailException, UsernameNotMatchEmail, PasswordException, com.group10.paperlessexamwebservice.service.exceptions.user.UsernameNotFoundException, UserNotFound;

    User deleteUser(User user) throws ServiceNotAvailable, com.group10.paperlessexamwebservice.service.exceptions.user.UsernameNotFoundException;
}
