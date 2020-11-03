package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.dao.IUserDAO;
import com.group10.paperlessexamwebservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class PaperlessExamServiceImpl implements IUserService{

@Autowired
    private IUserDAO loginDao;


@Override
    public User logInUser(User user){
       // temp user may contain the actual user or a null value if the object was not found
        User temp= loginDao.logInUser(user);
        // Check the db response;
        if(user!=null){
            return user;
        }
        return null;
    }
}
