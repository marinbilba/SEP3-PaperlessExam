package com.group10.paperlessexamwebservice.service;

import com.group10.paperlessexamwebservice.databaserequests.IUserRequests;
import com.group10.paperlessexamwebservice.model.Role;
import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.user.PasswordException;
import com.group10.paperlessexamwebservice.service.exceptions.user.UsernameFoundException;
import com.group10.paperlessexamwebservice.service.exceptions.user.UsernameNotMatchEmail;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Send a request that returns a User object to {@link IUserRequests}. The requested user object
     * is checked. If the user is null => the user was not found in the database. The password received from
     * the database is compared with the one provided by the client.
     * @param user user object
     * @return If the credentials are validated successfully the received User object from the database will be
     * returned to the Client.
     * @throws ServiceNotAvailable if there are connection problems with the third tier
     * @throws PasswordException if credentials are incorrect.
     */
    @Override
    public User logInUser(User user) throws ServiceNotAvailable, PasswordException {
        //        Get the user object by username for further validation
        User requestedUserFromTheDatabase = userRequest.getUserByUsername(user.getUsername());
        //        Check user by username
        if (requestedUserFromTheDatabase == null) {
            throw new UsernameNotFoundException("Username is incorrect");
        }
        // Password validation
        if (!requestedUserFromTheDatabase.getPassword().equals(user.getPassword())) {
            throw new PasswordException("Password is incorrect");
        }
        return requestedUserFromTheDatabase;
    }

    @Override
    public User createUser(User user) throws UsernameNotMatchEmail, PasswordException, ServiceNotAvailable, UsernameFoundException {
        // Check if the username exists in the database
        User requestedUserFromTheDatabase = userRequest.getUserByUsername(user.getUsername());
        if (requestedUserFromTheDatabase != null) {
            throw new UsernameFoundException("Username already exists");
        }
        // Check username == email before@
       String subtractedString= getEmailSubstring(user.getEmail());
        if(!user.getUsername().equals(subtractedString)){
            throw new UsernameNotMatchEmail("Username must match the email until the '@' sign ");
        }
        //   Password validation

        if(!user.getPassword().equals(user.getConfirmPassword())){
            throw new PasswordException("Password does not match Confirm Password field");
        }
        return userRequest.createUser(user);
    }

    /**
     * Method to subtract the String until the '@' character
     * @param email email to check
     * @return the String until the '@' character
     */
    private String getEmailSubstring(String email) {
        int i =email.indexOf("@");
        return email.substring(0,i);
    }

    @Override
    public List<User> getAllUsersList() {
        return userRequest.getAllUsersList();
    }

}
