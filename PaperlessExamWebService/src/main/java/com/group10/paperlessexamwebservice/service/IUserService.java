package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;

public interface IUserService {
    User logInUser(User user) throws PasswordNotFoundException;
}
