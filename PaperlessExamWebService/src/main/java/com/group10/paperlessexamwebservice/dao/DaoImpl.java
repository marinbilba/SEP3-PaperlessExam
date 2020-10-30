package com.group10.paperlessexamwebservice.dao;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DaoImpl implements ILoginDao{

    private static Map<Integer, User> fakeUsersDB;
    static {
        fakeUsersDB = new HashMap<Integer, User>() {
            {
                put(1, new User(1, "Said", "Computer Science"));
                put(2, new User(2, "Alex", "Finance"));
                put(3, new User(3, "Anna", "Maths"));
            }
        };

            }
    @Override
    public User logInUser(User user){
// Find user in the db and return the object;
        User user1 =fakeUsersDB.get(user.getId());
        System.out.println(user1);
        return user1;
    }
}
