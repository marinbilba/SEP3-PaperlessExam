package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.databaserequests.IUserRequests;
import com.group10.paperlessexamwebservice.model.Role;
import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PaperlessExamServiceImpl implements IUserService {

    @Autowired
    private IUserRequests userRequest;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User logInUser(User user) throws ServiceNotAvailable,PasswordNotFoundException {
        //        Get the user object by username for further validation
        User requestedUserFromTheDatabase = userRequest.getUserByUsername(user.getUsername());
        //        Check user by username
        if (userRequest.getUserByUsername(user.getUsername()) == null) {
            throw new UsernameNotFoundException("Username is incorrect");
        }
        // Password validation
        if (!requestedUserFromTheDatabase.getPassword().equals(user.getPassword())) {
            throw new PasswordNotFoundException("Password is incorrect");
        }
        System.out.println("bleati");
        return requestedUserFromTheDatabase;

//
//        ResponseEntity<User> temp = userRequest.login(user);
//        if (temp.getStatusCode().is2xxSuccessful())
//        {
//            temp.getBody();
//        }
//        else if (temp.getStatusCode().isError())
//        {
//            throw new DataBaseException("Smth went wrong");
//        }
//
//
//        if (userRequest.usernameExists(user.getUsername())) {
//            throw new UsernameNotFoundException("There is NO account found with that username:" + user.getUsername());
//        } else if (!userRequest.checkPassword(user.getPassword())) {
//            throw new PasswordNotFoundException("Password is incorrect");
//        } else
    }

    @Override
    public User createUser(User user) throws Exception {
//        check username == email before@
//        password

        // get role id
        Role tempRole = userRequest.getRoleIdByName(user.getRole().getName());
        // set the recieved role to the current user
        user.setRole(tempRole);
        if (userRequest.getUserByUsername(user.getUsername())==null) {
            throw new Exception("Username");
            // throw la exceptie !!!!!
        } else {
            return userRequest.createUser(user);
        }

    }

    @Override
    public List<User> getAllUsersList() {
        return userRequest.getAllUsersList();
    }

}
