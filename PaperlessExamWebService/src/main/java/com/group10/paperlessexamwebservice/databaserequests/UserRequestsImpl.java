package com.group10.paperlessexamwebservice.databaserequests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.NetworkContainer;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.ISocketConnector;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.SocketConnector;
import com.group10.paperlessexamwebservice.model.Role;
import com.group10.paperlessexamwebservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation.*;

import java.io.IOException;
import java.util.List;

/**
 * This class sends and receives HTTP requests to the database tier
 */
@Service
public class UserRequestsImpl implements IUserRequests {
    private static final String DATABASE_TIER_URI = "http://localhost:8091";
    @Autowired
    ISocketConnector socketConnector;
    //private User cashedUser;
    private RestTemplate restTemplate;
    private Gson gson;

    public UserRequestsImpl() {
        // create an instance of RestTemplate
        restTemplate = new RestTemplate();
        gson = new GsonBuilder().setPrettyPrinting().create();

    }

    @Override
    public ResponseEntity<User> login(User user) {
        User temp;
        // request body parameters

        // send POST request

        ResponseEntity<User> response = restTemplate.postForEntity(DATABASE_TIER_URI + "/createUser", user, User.class);
        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful");
            temp = response.getBody();
            System.out.println(temp.getUsername());


        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            System.out.println("Request Failed");
        }

        return response;
    }

    @Override
    public boolean checkPassword(String password) {
        return true;
    }


    @Override
    public boolean emailExists(String email) {
        return false;
    }

    @Override
    public User createUser(User user) {
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
    public Role getRoleIdByName(String name) {
        Role temp;
        ResponseEntity<Role> response = restTemplate.getForEntity(DATABASE_TIER_URI + "/getRoleByName/" + name, Role.class);
        temp = response.getBody();

        return temp;
    }

    @Override
    public boolean usernameExist(String username) {
        User user=null;
        try {
            socketConnector.connect();
            NetworkContainer networkContainer = new NetworkContainer(USERNAME_EXISTS, username);
            String stringSerialized = gson.toJson(networkContainer);
            socketConnector.sendToServer(stringSerialized);
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponse = gson.fromJson(responseMessage, NetworkContainer.class);
             user = (User) networkContainerResponse.getObject();
            System.out.println(user.getUsername());

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (user == null) {
            return false;
        }

        return true;
    }

    @Override
    public User getUserByUsername(String username) {
        ResponseEntity<User> response = restTemplate.getForEntity(DATABASE_TIER_URI + "/getUserByUsername/" + username, User.class);
        return response.getBody();
    }
}
