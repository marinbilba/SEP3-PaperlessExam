package com.group10.paperlessexamwebservice.dao;

import com.group10.paperlessexamwebservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DaoImpl implements IUserDAO {
    private static Map<Integer, User> fakeUsersDB;


    private User cashedUser;

//    static {
//        fakeUsersDB = new HashMap<Integer, User>() {
//            {
//                put(1, new User("Said", "123456", "123456", Collections.singleton(new Role() {
//                })));
//            }
//        };

    //  }


    @Override
    public boolean usernameExists(String username) {

        return false;
    }

    @Override
    public boolean checkPassword(String password) {
        return false;
    }

    @Override
    public User getCashedUser() {
        return cashedUser;
    }

    @Override
    public boolean emailExists(String email) {
        return false;
    }

    @Override
    public String createAccount() {
        return null;
    }

    @Override
    public List<User> getAllUsersList() {
        return null;
    }
}
