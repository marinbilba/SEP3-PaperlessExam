package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.dao.IUserRequests;
import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.user.EmailException;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperlessExamServiceImpl implements IUserService {

    @Autowired
    private IUserRequests userRequest;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User logInUser(User user) throws UsernameNotFoundException, PasswordNotFoundException {
//        Get user by username
        userRequest.usernameExists(user.getUsername());
//
//
//        if (userRequest.usernameExists(user.getUsername())) {
//            throw new UsernameNotFoundException("There is NO account found with that username:" + user.getUsername());
//        } else if (!userRequest.checkPassword(user.getPassword())) {
//            throw new PasswordNotFoundException("Password is incorrect");
//        } else
            return null;
    }

    @Override
    public String createUser(User user) throws EmailException {
//        if (userRequest.usernameExists(user.getUsername())) {
//            throw new UsernameNotFoundException("User with username:'" + user.getUsername() + "' already exists");
//        } else if (userRequest.emailExists(user.getEmail())) {
//            throw new EmailException("User with email:'" + user.getEmail() + "' already exists");
//        }
//        return userRequest.createAccount();
        return null;
    }

    @Override
    public List<User> getAllUsersList() {
        return userRequest.getAllUsersList();
    }

}
