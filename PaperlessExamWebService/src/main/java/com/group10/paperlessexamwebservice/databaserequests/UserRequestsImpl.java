package com.group10.paperlessexamwebservice.databaserequests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.NetworkContainer;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.ISocketConnector;
import com.group10.paperlessexamwebservice.model.Role;
import com.group10.paperlessexamwebservice.model.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import jdk.jfr.Unsigned;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation.*;

import java.io.IOException;
import java.util.List;

/**
 * Class Handles the client requests to the third tier through a Socket connection
 * The following socket protocol is followed:
 * 1. Create socket connection
 * 2. Send the input stream
 * 3. Read the output stream from the Server
 * 4. Disconnect from the server
 *
 * @author Marin Bilba
 * @version 2.2
 */

@Service
public class UserRequestsImpl implements IUserRequests {
    private static final String DATABASE_TIER_URI = "http://localhost:8091";
    /**
     * The Socket connector.
     */
    @Autowired
    ISocketConnector socketConnector;
    //private User cashedUser;
    private RestTemplate restTemplate;
    private Gson gson;

    /**
     * Instantiates a new User requests.
     */
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
    public User createUser(User user) throws ServiceNotAvailable {
        User tempUser = null;
        // Connect
        try {
            socketConnector.connect();
            System.out.println("[CLIENT] Connected to server");
//            Send request
            String userSerialized = gson.toJson(user);
            NetworkContainer networkContainer = new NetworkContainer(CREATE_USER, userSerialized);
            String networkContainerSerialized = gson.toJson(networkContainer);
            socketConnector.sendToServer(networkContainerSerialized);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            tempUser  = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), User.class);
            //            Disconnect
            socketConnector.disconnect();
            System.out.println("[CLIENT] Disconnected from server");

        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return tempUser;
    }
//
//   private <T> T makeRequest(RequestOperation requestOperation,Object objectToSerialize,Class<T> classOfT) throws IOException {
//       socketConnector.connect();
//       System.out.println("[CLIENT] Connected to server");
//        classOfT = (Class<T>) objectToSerialize;
//       String objectSerialized = gson.toJson(classOfT);
//       NetworkContainer networkContainer = new NetworkContainer(requestOperation, objectSerialized);
//       String stringRequestSerialized = gson.toJson(networkContainer);
//       socketConnector.sendToServer(stringRequestSerialized);
//       //            Read response
//       String responseMessage = socketConnector.readFromServer();
//       NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
//       tempUser  = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), User.class);
//       //            Disconnect
//       socketConnector.disconnect();
//       System.out.println("[CLIENT] Disconnected from server");
//
//   }
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

    /**
     * Makes a queries to the database, passing the {@param username}.
     * <p>
     * 1. Create socket connection
     * 2. Create the NetworkContainer and pass the method parameter as second argument.
     * 3. Serialize the Network Container.
     * 4. Send the Network Container as input stream
     * 5. Read the output stream from the Server
     * 6. Deserialize the Network Container
     * 7. Deserialize the second argument of the network container
     * 8. Check the queried result
     *
     *
     * @param username username
     * @return a boolean expresion. If user was found==true, else false
     */
    @Override
    public User getUserByUsername(String username) throws ServiceNotAvailable {
        User user = null;
        // Connect
        try {
            socketConnector.connect();
            System.out.println("[CLIENT] Connected to server");
//            Send request
            NetworkContainer networkContainer = new NetworkContainer(GET_USER_BY_USERNAME, username);
            String stringRequestSerialized = gson.toJson(networkContainer);
            socketConnector.sendToServer(stringRequestSerialized);

//            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            user = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), User.class);
            //            Disconnect
            socketConnector.disconnect();
            System.out.println("[CLIENT] Disconnected from server");

        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return user;
    }


}
