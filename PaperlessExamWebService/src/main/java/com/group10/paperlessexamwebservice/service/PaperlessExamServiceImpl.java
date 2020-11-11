package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.dao.IUserRequests;
import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.user.DataBaseException;
import com.group10.paperlessexamwebservice.service.exceptions.user.EmailException;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public User logInUser(User user) throws UsernameNotFoundException, PasswordNotFoundException, DataBaseException {
//        Get user by username
        ResponseEntity<User> temp = userRequest.login(user);
        if (temp.getStatusCode().is2xxSuccessful())
        {
            temp.getBody();
        }
        else if (temp.getStatusCode().isError())
        {
            throw new DataBaseException("Smth went wrong");
        }
//
//
//        if (userRequest.usernameExists(user.getUsername())) {
//            throw new UsernameNotFoundException("There is NO account found with that username:" + user.getUsername());
//        } else if (!userRequest.checkPassword(user.getPassword())) {
//            throw new PasswordNotFoundException("Password is incorrect");
//        } else
            return temp.getBody();
    }

    @Override
    public User createUser(User user) throws EmailException {
        ResponseEntity<User> temp = userRequest.createUser(user);
        if (temp.getStatusCode().is2xxSuccessful())
        {
            temp.getBody();
        }
        else
        {
            System.out.println("CreateUser went wrong");
        }
        return temp.getBody();
    }

    @Override
    public List<User> getAllUsersList() {
        return userRequest.getAllUsersList();
    }

}
