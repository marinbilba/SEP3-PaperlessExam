package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.dao.IUserDAO;
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
    private IUserDAO loginDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User logInUser(User user) throws UsernameNotFoundException, PasswordNotFoundException {
        if (!loginDao.usernameExists(user.getUsername())) {
            throw new UsernameNotFoundException("There is NO account found with that username:" + user.getUsername());
        } else if (!loginDao.checkPassword(user.getPassword())) {
            throw new PasswordNotFoundException("Password is incorrect");
        } else
            return loginDao.getCashedUser();
    }

    @Override
    public String createUser(User user) throws EmailException {
        if (loginDao.usernameExists(user.getUsername())) {
            throw new UsernameNotFoundException("User with username:'" + user.getUsername() + "' already exists");
        } else if (loginDao.emailExists(user.getEmail())) {
            throw new EmailException("User with email:'" + user.getEmail() + "' already exists");
        }
        return loginDao.createAccount();
    }

    @Override
    public List<User> getAllUsersList() {
        return loginDao.getAllUsersList();
    }

}
