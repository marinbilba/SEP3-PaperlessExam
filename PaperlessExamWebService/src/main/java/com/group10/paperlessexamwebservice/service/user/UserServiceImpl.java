package com.group10.paperlessexamwebservice.service.user;

import com.group10.paperlessexamwebservice.databaserequests.requests.user.IUserRequests;
import com.group10.paperlessexamwebservice.model.user.Role;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User service.
 */
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
     *
     * @param user user object
     * @return If the credentials are validated successfully the received User object from the database will be
     * returned to the Client.
     * @throws ServiceNotAvailable if there are connection problems with the third tier
     * @throws PasswordException   if credentials are incorrect.
     */
    @Override
    //todo Check user by username can be done using class private method checkUserExistsByUsername()
    public User logInUser(User user) throws ServiceNotAvailable, PasswordException, UsernameNotFoundException {
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
     * 1. Check if the username exists in the database, else exception is thrown
     * 2. Check if the username does match the substring of the email until the '@' char, else exception is thrown
     * 3. Password validation
     * 4. Get the role id by provided name from the Client
     * 5. Set the received role id to the current user object instance
     * 6. Create the user
     *
     * @param user user object that should be stored
     * @return created User object
     * @throws UsernameNotMatchEmail  the username does not match the substring of the email until the '@' char
     * @throws PasswordException      if credentials are incorrect
     * @throws ServiceNotAvailable    if there are connection problems with the third tier
     * @throws UsernameFoundException the username already exists in the database
     */
    @Override
    public User createUser(User user) throws UsernameNotMatchEmail, PasswordException, ServiceNotAvailable, UsernameFoundException, EmailException, UnexpectedError {
        if (user == null) {
            throw new UnexpectedError("Something went wrong");
        }
        // Check if the username exists in the database
        User requestedUserFromTheDatabase = userRequest.getUserByUsername(user.getUsername());
        if (requestedUserFromTheDatabase != null) {
            throw new UsernameFoundException("Username already exists");
        }
        // Check username == email before@
        String subtractedString = getEmailSubstring(user.getEmail());
        if (!user.getUsername().equals(subtractedString)) {
            throw new UsernameNotMatchEmail("Username must match the email until the '@' sign ");
        }
        //   Password validation
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new PasswordException("Password does not match Confirm Password field");
        }
        // Get role id
        Role roleId = userRequest.getRoleIdByName(user.getRole().getName());
        user.setRole(roleId);
        return userRequest.createUpdateUser(user);
    }

    /**
     * Method to subtract the String until the '@' character
     *
     * @param email email to check
     * @return the String until the '@' character
     */
    private String getEmailSubstring(String email) throws EmailException {
        if (email.contains("@")) {
            int i = email.indexOf("@");
            return email.substring(0, i);
        }
        throw new EmailException("Email must contain character '@'");
    }

    @Override
    public List<User> getAllUsersList() {
        return userRequest.getAllUsersList();
    }

    @Override
    public List<User> getUsersByFirstName(String firstName) throws ServiceNotAvailable, UserNotFound {
        List<User> usersList = userRequest.getUsersByFirstName(firstName);
        if (!usersList.isEmpty()) {
            return usersList;
        }
        throw new UserNotFound("Users by given first name were not found");
    }

    @Override
    public User getUsersByUsername(String username) throws ServiceNotAvailable, UserNotFound {
        User user = userRequest.getUserByUsername(username);
        if (user != null) {
            return user;
        } else throw new UserNotFound("Username was not found in the system");
    }

    @Override
    //  todo, check if current user.equals with the one from the database
    // todo, user already comes with role id, not necessary to find it again in db
    public User updateUser(User user) throws NullFieldUser, ServiceNotAvailable, UnexpectedError, EmailException, UsernameNotMatchEmail, PasswordException, UsernameNotFoundException, UserNotFound {
        boolean validatedSuccessful = checkNullUserFields(user);
        if (validatedSuccessful) {
            User fetchedUser = getUsersByUsername(user.getUsername());
            String subtractedString = getEmailSubstring(user.getEmail());
            if (!user.getUsername().equals(subtractedString)) {
                throw new UsernameNotMatchEmail("Username must match the email until the '@' sign ");
            } else if (!user.getPassword().equals(user.getConfirmPassword())) {
                throw new PasswordException("Password does not match Confirm Password field");
            }
            Long userID = fetchedUser.getId();
            fetchedUser = user;

            Role fetchedRole = userRequest.getRoleIdByName(user.getRole().getName());
//            Set id and role fetched from the db
            fetchedUser.setId(userID);
            fetchedUser.setRole(fetchedRole);
            userRequest.createUpdateUser(fetchedUser);
            return fetchedUser;
        }
        throw new UnexpectedError("Something went wrong");

    }

    @Override
    public User deleteUser(User user) throws ServiceNotAvailable, UsernameNotFoundException {
        User requestedUserFromTheDatabase = userRequest.getUserByUsername(user.getUsername());
        //        Check user by username
        if (requestedUserFromTheDatabase == null) {
            throw new UsernameNotFoundException("Username is incorrect");
        }
        return userRequest.deleteUser(requestedUserFromTheDatabase);
    }

    @Override
    public User getUserStudentByUsername(String username) throws ServiceNotAvailable, UserNotFound {
        User user = userRequest.getUserByUsername(username);
        if (user != null) {
            if (user.getRole().getName().equals("Teacher")) {
                throw new UserNotFound("This user is a teacher");
            }
            return user;
        } else throw new UserNotFound("Username was not found in the system");
    }

    @Override
    public List<User> getUsersStudentsByFirstName(String firstName) throws ServiceNotAvailable, UserNotFound {
        List<User> onlyStudentsList = new ArrayList<>();
        List<User> usersList = userRequest.getUsersByFirstName(firstName);
        if (!usersList.isEmpty()) {
            for (var user : usersList) {
                if (user.getRole().getName().equals("Student")) {
                    onlyStudentsList.add(user);
                }
            }
            if (onlyStudentsList.isEmpty()) {
                throw new UserNotFound("Students by given first name were not found");
            }
            return usersList;
        }
        throw new UserNotFound("Students by given first name were not found");
    }


    /**
     * Method check if user fields are not null, except id.
     *
     * @param user
     * @return
     * @throws NullFieldUser if one of the fields is null
     */
    private boolean checkNullUserFields(User user) throws NullFieldUser {
        if (user.getUsername() == null) {
            throw new NullFieldUser("Username must be entered");
        } else if (user.getEmail() == null) {
            throw new NullFieldUser("Email must be entered");
        } else if (user.getFirstName() == null) {
            throw new NullFieldUser("First name must be entered");

        } else if (user.getLastName() == null) {
            throw new NullFieldUser("Last name must be entered");

        } else if (user.getRole() == null) {
            throw new NullFieldUser("Role must be set");
        } else if (user.getPassword() == null) {
            throw new NullFieldUser("Password  must be entered");
        } else if (user.getConfirmPassword() == null) {
            throw new NullFieldUser("Confirm Password  must be entered");
        }
        return true;
    }

}
