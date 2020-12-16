package com.group10.databaselayer.dataaccessobject;

import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.repositories.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserDAO {
    @Autowired
    IUserRepository userRepository;

    /**
     * Method will return the user filtered by id
     *
     * @return the list of all users
     */

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
    /**
     * Create/update user.
     *
     * @param user
     * @return the created/updated user object
     */

    public User createUpdateUser(User user) {
        System.out.println(user.getFirstName());
        System.out.println(user.getRole().getName());
        return userRepository.save(user);
    }

    public List<User> getUsersListByFirstName(String firstNameDeserialized) {
       return userRepository.findByFirstNameIgnoreCaseContaining(firstNameDeserialized);
    }

    public void deleteUser(User userToDelete) {
         userRepository.delete(userToDelete);
    }

    public User getUserById(long parsedTeacherId) {
        return userRepository.getOne(parsedTeacherId);
    }
}
