package com.group10.paperlessexamwebservice.databaserequests;

import com.group10.paperlessexamwebservice.model.Role;
import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IUserRequests {
//    Check if username exists in the database
    ResponseEntity<User> login(User username);
    //    Check if the given password matches with the bassword in the database
    boolean checkPassword(String password);
    //    Check if email exists in the database
    boolean emailExists(String email);

    User createUser(User user) throws ServiceNotAvailable;
// Return all users in the database
    List<User> getAllUsersList();

    Role getRoleIdByName(String name) throws ServiceNotAvailable;

    User getUserByUsername(String username) throws ServiceNotAvailable;

}
