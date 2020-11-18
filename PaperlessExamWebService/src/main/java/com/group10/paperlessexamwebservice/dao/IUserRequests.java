package com.group10.paperlessexamwebservice.dao;

import com.group10.paperlessexamwebservice.model.Role;
import com.group10.paperlessexamwebservice.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserRequests {
//    Check if username exists in the database
    ResponseEntity<User> login(User username);
    //    Check if the given password matches with the bassword in the database
    boolean checkPassword(String password);

    User getCashedUser();
    //    Check if email exists in the database
    boolean emailExists(String email);

    User createUser(User user);
// Return all users in the database
    List<User> getAllUsersList();

    Role getRoleIdByName(String name);

    boolean usernameExist(String username);
}
