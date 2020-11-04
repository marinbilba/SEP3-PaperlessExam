package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.dao.IUserDAO;
import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PaperlessExamServiceImpl implements IUserService {

    @Autowired
    private IUserDAO loginDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User logInUser(User user) throws UsernameNotFoundException,PasswordNotFoundException {
//        if (!loginDao.usernameExists(user.getUsername())) {
//            throw new UsernameNotFoundException("There is NO account found with that username:" + user.getUsername());
//            user.getPassword().equals()
//        }
//        else if(!loginDao.checkPasswod(user.getPassword())){
//            throw new PasswordNotFoundException("Password is incorrect");
//
////            check roles in db
//        }else
//        // temp user may contain the actual user or a null value if the object was not found
//        User tempUser=new User(user.getUsername(),user.getPassword(),user.getRoles());
//        User temp = loginDao.logInUser(user);
//        // Check the db response;
//        if (user != null) {
//            return user;
//        }
   return null;
    }
}
