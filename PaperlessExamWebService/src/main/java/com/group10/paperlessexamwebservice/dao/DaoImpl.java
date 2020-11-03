package com.group10.paperlessexamwebservice.dao;

import com.group10.paperlessexamwebservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DaoImpl implements IUserDAO {

    private static Map<Integer, User> fakeUsersDB;

//    static {
//        fakeUsersDB = new HashMap<Integer, User>() {
//            {
//                put(1, new User("Said", "123456", "123456", Collections.singleton(new Role() {
//                })));
//            }
//        };

  //  }

    @Override
    public User logInUser(User user) {
// Find user in the db and return the object;
        User user1 = fakeUsersDB.get(user.getId());
        System.out.println(user1);
        return user1;
    }
}
