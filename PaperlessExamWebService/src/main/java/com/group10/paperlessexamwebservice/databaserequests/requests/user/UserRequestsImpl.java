package com.group10.paperlessexamwebservice.databaserequests.requests.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.NetworkContainer;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation;
import com.group10.paperlessexamwebservice.databaserequests.requests.shared.RequestSharedMethods;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.ISocketConnector;
import com.group10.paperlessexamwebservice.model.user.Role;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation.*;

import java.io.IOException;
import java.util.Arrays;
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

    @Autowired
    private RequestSharedMethods requestSharedMethods;
    @Autowired
   private ISocketConnector socketConnector;
    //private User cashedUser;
    private Gson gson;

    /**
     * Instantiates a new User requests.
     */
    public UserRequestsImpl() {
        gson = new GsonBuilder().setPrettyPrinting().create();

    }



    @Override
    public boolean checkPassword(String password) {
        return true;
    }


    @Override
    public boolean emailExists(String email) {
        return false;
    }

    /**
     * Make a quire to the database with the purpose of creating a new user, passing the {@param user}.
     * <p>
     * 1. Create socket connection
     * 2. The user object is serialized as JSON
     * 3. Send the request
     * 4. Read the output stream from the Server
     * 5. Deserialize the Network Container
     * 6. Deserialize the second argument of the network container
     * 7. Close the socket connection
     *
     * @param user user object that should be created
     * @return a user object. <i>The object might be null if was not found in the database</>
     */
    @Override
    public User createUser(User user) throws ServiceNotAvailable {
        User tempUser = null;
        // Connect
        try {
            socketConnector.connect();
            System.out.println("[CLIENT] Connected to server");
            // Serialize the object
            String userSerialized = gson.toJson(user);
            //            Send request
            requestSharedMethods.sendRequest(userSerialized, CREATE_USER);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            tempUser = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), User.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return tempUser;
    }

    /**
     * Make a quire to the database with the purpose of finding a user by the given username, passing the {@param username}.
     * <p>
     * 1. Create socket connection
     * 2. Send the request
     * 3. Read the output stream from the Server
     * 4. Deserialize the Network Container
     * 5. Deserialize the second argument of the network container
     * 6. Close the socket connection
     *
     * @param username username
     * @return a user object. <i>The object might be null if was not found in the database</>
     */
    @Override
    public User getUserByUsername(String username) throws ServiceNotAvailable {
        User user = null;
        // Connect
        try {
            socketConnector.connect();
            System.out.println("[CLIENT] Connected to server");
//            Send request
            requestSharedMethods.sendRequest(username, GET_USER_BY_USERNAME);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            user = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), User.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return user;
    }

    @Override
    public List<User> getUsersByFirstName(String firstName) throws ServiceNotAvailable {
        List<User> userList=null;
        try {

            socketConnector.connect();
            System.out.println("[CLIENT] Connected to server");
            //            Send request
            requestSharedMethods.sendRequest(firstName, GET_USERS_BY_FIRST_NAME);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            User[] tempList = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), User[].class);
            userList= Arrays.asList(tempList);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return userList;
    }

    @Override
    public List<User> getAllUsersList() {
        return null;
    }
    /**
     * Make a quire to the database with the purpose of finding the role id by the given name, passing the {@param name}.
     * <p>
     * 1. Create socket connection
     * 2. Send the request
     * 3. Read the output stream from the Server
     * 4. Deserialize the Network Container
     * 5. Deserialize the second argument of the network container
     * 6. Close the socket connection
     *
     * @param name username
     * @return a Role object. <i>The object might be null if was not found in the database</>
     */
    @Override
    public Role getRoleIdByName(String name) throws ServiceNotAvailable {
        Role role = null;
        // Connect
        try {
            socketConnector.connect();
            System.out.println("[CLIENT] Connected to server");
//            Send request
            requestSharedMethods.sendRequest(name, GET_ROLE_ID_BY_NAME);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            role = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), Role.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return role;
    }

//    /**
//     * Sends a request through the socket connection.
//     * 1. Create the NetworkContainer with the received parameters{@param objectSerialized},{@param requestOperation}.
//     * 2. Serialize the Network Container.
//     * 3. Send the Network Container as input stream
//     *
//     * @param serializedObject used as the second argument in the NetworkContainer <i>MUST be serialized</>
//     * @param requestOperation operation that should be performed.
//     * @throws IOException exceptions produced by failed or interrupted I/O operations
//     */
//    private void sendRequest(String serializedObject, RequestOperation requestOperation) throws IOException {
//        NetworkContainer networkContainer = new NetworkContainer(requestOperation, serializedObject);
//        String networkContainerSerialized = gson.toJson(networkContainer);
//        socketConnector.sendToServer(networkContainerSerialized);
//    }
//

}
