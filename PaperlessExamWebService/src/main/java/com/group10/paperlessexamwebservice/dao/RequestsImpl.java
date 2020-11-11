package com.group10.paperlessexamwebservice.dao;

import com.group10.paperlessexamwebservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class sends and receives HTTP requests to the database tier
 */
@Service
public class RequestsImpl implements IUserRequests {
    private static final String DATABASE_TIER_URI = "http://localhost:8091";
    @Autowired
    private User cashedUser;
    private RestTemplate restTemplate;

    public RequestsImpl() {
        // create an instance of RestTemplate
        restTemplate = new RestTemplate();

    }

    @Override
    public ResponseEntity<User> login(User user) {
        User temp;
        // request body parameters
        Map<String, String> map = new HashMap<>();
       // map.put("user", user);
        // send POST request
        ResponseEntity<User> response = restTemplate.postForEntity(DATABASE_TIER_URI + "/login", user, User.class);
        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful");
            temp = response.getBody();
            System.out.println(temp.getUsername());


        } else {
            System.out.println("Request Failed");
        }

        return response;
    }

    @Override
    public boolean checkPassword(String password)
    {
        return true;
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
    public ResponseEntity<User> createUser(User user)
    {
        User temp;
        Map<String, String> map = new HashMap<>();
        ResponseEntity<User> response = null;
        ResponseEntity<User> responseCheck = restTemplate.postForEntity(DATABASE_TIER_URI + "/login", user, User.class);
        if (responseCheck.getStatusCode() != HttpStatus.OK)
        {
            if (user.getPassword().equals(user.getConfirmPassword()))
            {
            System.out.println("User is available");
            response = restTemplate.postForEntity(DATABASE_TIER_URI + "/login", user, User.class);
            if (response.getStatusCode() == HttpStatus.OK)
            {
                temp = response.getBody();
                System.out.println("User "+temp.getUsername()+" successfully created");
            }
            }
            else
            {
                System.out.println("password and confirm password does not mach");
            }

        }
        else
        {
            System.out.println(": User is already in the system");
        }


        return response;
    }

    @Override
    public List<User> getAllUsersList() {
        return null;
    }
}
