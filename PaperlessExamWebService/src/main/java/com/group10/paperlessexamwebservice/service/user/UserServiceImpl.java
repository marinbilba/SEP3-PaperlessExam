package com.group10.paperlessexamwebservice.service.user;

import com.group10.paperlessexamwebservice.databaserequests.IUserRequests;
import com.group10.paperlessexamwebservice.model.user.Role;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

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

    /**
     * Creates and stores a user in the database. The following steps are performed
     *  1. Check if the username exists in the database, else exception is thrown
     *  2. Check if the username does match the substring of the email until the '@' char, else exception is thrown
     *  3. Password validation
     *  4. Get the role id by provided name from the Client
     *  5. Set the received role id to the current user object instance
     *  6. Create the user
     *  
     * @param user user object that should be stored
     * @return created User object
     * @throws UsernameNotMatchEmail the username does not match the substring of the email until the '@' char
     * @throws PasswordException if credentials are incorrect
     * @throws ServiceNotAvailable if there are connection problems with the third tier
     * @throws UsernameFoundException the username already exists in the database
     */
    @Override
    public User createUser(User user) throws UsernameNotMatchEmail, PasswordException, ServiceNotAvailable, UsernameFoundException, EmailException {
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
        // Get role id
        Role roleId= userRequest.getRoleIdByName(user.getRole().getName());
        user.setRole(roleId);
        return userRequest.createUser(user);
    }
    /**
     * Method to subtract the String until the '@' character
     * @param email email to check
     * @return the String until the '@' character
     */
    private String getEmailSubstring(String email) throws EmailException {
        if(email.contains("@")){
            int i =email.indexOf("@");
            return email.substring(0,i);
        }
       throw new EmailException("Email must contain character '@'");
    }

    @Override
    public List<User> getAllUsersList() {
        return userRequest.getAllUsersList();
    }

    @Override
    public List<User> getUsersByFirstName(String firstName) throws ServiceNotAvailable, UserNotFound {
        List<User> usersList= userRequest.getUsersByFirstName(firstName);
        if(!usersList.isEmpty()){
            return usersList;
        }
        throw new UserNotFound("Users by given first name were not found");
    }

    @Override
    public User getUsersByUsername(String username) throws ServiceNotAvailable, UserNotFound {
        User user=userRequest.getUserByUsername(username);
        if(user!=null){
            return user;
        }
        else throw new UserNotFound("Username was not found in the system");
    }

}
