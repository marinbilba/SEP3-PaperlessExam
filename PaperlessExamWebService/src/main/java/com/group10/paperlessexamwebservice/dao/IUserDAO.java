package com.group10.paperlessexamwebservice.dao;

import com.group10.paperlessexamwebservice.model.User;

public interface IUserDAO {
    boolean usernameExists(String username);

    boolean checkPassword(String password);

    User getCashedUser();

}
