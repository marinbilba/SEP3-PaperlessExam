package com.group10.paperlessexamwebservice.databaserequests.requests.user;

import com.group10.paperlessexamwebservice.model.user.Role;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserRequests {
//    Check if username exists in the database
User getUserByUsername(String username) throws ServiceNotAvailable;
    //    Check if the given password matches with the bassword in the database
    boolean checkPassword(String password);
    //    Check if email exists in the database
    boolean emailExists(String email);

    User createUser(User user) throws ServiceNotAvailable;
// Return all users in the database
    List<User> getAllUsersList();

    Role getRoleIdByName(String name) throws ServiceNotAvailable;



    List<User> getUsersByFirstName(String firstName) throws ServiceNotAvailable;
}
