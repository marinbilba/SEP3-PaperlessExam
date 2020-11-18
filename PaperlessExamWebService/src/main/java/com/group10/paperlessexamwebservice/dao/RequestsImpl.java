package com.group10.paperlessexamwebservice.dao;

import com.group10.paperlessexamwebservice.model.Role;
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
        user=new User("1111","222","3333","4444","Clar",new Role(1,"Student"));
        System.out.println(user.getRole().getName());
        ResponseEntity<User> response = restTemplate.postForEntity(DATABASE_TIER_URI + "/createUser", user, User.class);
        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful");
            temp = response.getBody();
            System.out.println(temp.getUsername());


        } else if(response.getStatusCode() == HttpStatus.BAD_REQUEST){
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
    public User createUser(User user)
    {
        User temp;
        ResponseEntity<User> response = restTemplate.postForEntity(DATABASE_TIER_URI + "/createUser", user, User.class);
        temp = response.getBody();

        return temp;
    }

    @Override
    public List<User> getAllUsersList() {
        return null;
    }

    @Override
    public Role getRoleIdByName(String name)
    {
        Role temp;
        ResponseEntity<Role> response = restTemplate.getForEntity(DATABASE_TIER_URI + "/getRoleByName/"+ name , Role.class);
        temp = response.getBody();

        return temp;
    }

    @Override
    public boolean usernameExist(String username)
    {
        ResponseEntity<User> response = restTemplate.getForEntity(DATABASE_TIER_URI + "/getRoleByName/"+ username , User.class);
        if (response.getBody() == null)
        {
            return false;
        }

        return true;
    }
}
