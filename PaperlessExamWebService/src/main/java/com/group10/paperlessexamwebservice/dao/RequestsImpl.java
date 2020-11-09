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
    public User usernameExists(String username) {

        // request body parameters
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        // send POST request
        ResponseEntity<User> response = restTemplate.getForEntity(DATABASE_TIER_URI + "/getUserByUsername/" + username, User.class);
        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful");

            User tempp = new User();
            tempp = response.getBody();
            System.out.println(tempp.getUsername());

        } else {
            System.out.println("Request Failed");
        }
        return null;
    }

    @Override
    public boolean checkPassword(String password) {
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
    public String createAccount() {
        return null;
    }

    @Override
    public List<User> getAllUsersList() {
        return null;
    }
}
