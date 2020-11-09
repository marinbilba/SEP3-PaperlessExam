package com.group10.paperlessexamwebservice.dao;

import com.group10.paperlessexamwebservice.model.User;

import java.util.List;

public interface IUserRequests {
//    Check if username exists in the database
    User usernameExists(String username);
    //    Check if the given password matches with the bassword in the database
    boolean checkPassword(String password);

    User getCashedUser();
    //    Check if email exists in the database
    boolean emailExists(String email);

    String createAccount();
// Return all users in the database
    List<User> getAllUsersList();

}
