package com.group10.databaselayer.controller.socketmediator;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.databaselayer.controller.RoleController;
import com.group10.databaselayer.controller.UserController;
import com.group10.databaselayer.controller.networkcontainer.NetworkContainer;
import com.group10.databaselayer.controller.networkcontainer.RequestOperation;
import com.group10.databaselayer.entity.user.Role;
import com.group10.databaselayer.entity.user.User;
import org.springframework.context.annotation.Scope;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

import static com.group10.databaselayer.controller.networkcontainer.RequestOperation.*;


/**
 * ServerSocketHandler.java  is handling each Client request as a separate thread.
 * The following socket protocol is followed inside the run method
 * 1. Receive input stream
 * 2. Deserialize the object from the input stream. The deserialized object should be casted
 * to NetworkContainer({@link NetworkContainer})
 * 3. Perform operation based on RequestOperation({@link RequestOperation})
 * 4. Query the database
 * 5. Serialize the response
 * 6. Send the response to the client
 *
 * @author Marin Bilba
 * @version 2.5
 */
@Scope("prototype")
public class ServerSocketHandler implements Runnable {

    private final Socket socket;
    private final HashSet<Object> controllersSet;

    private RoleController roleController;
    private UserController userController;

    private final Gson gson;

    private String objectSerialized;
    private NetworkContainer networkContainer;
    private String stringResponseSerialized;


    /**
     * Instantiates a new Server socket handler.
     *
     * @param socket         the socket
     * @param controllersSet the controllers set
     */
    public ServerSocketHandler(Socket socket, HashSet<Object> controllersSet) {
        this.socket = socket;
        this.controllersSet = controllersSet;
        gson = new GsonBuilder().setPrettyPrinting().create();
        parseControllerSet(this.controllersSet);

    }

    /**
     * Parses the passed HashSet<Object> to the constructor. The instances
     * of the hashset are checked and assigned to the field attributes.
     *
     * @param controllersSet hash set of all controlles
     */
    private void parseControllerSet(HashSet<Object> controllersSet) {
        for (Object controller : controllersSet) {
             if (controller instanceof RoleController) {
                this.roleController = (RoleController) controller;
            } else if (controller instanceof UserController) {
                this.userController = (UserController) controller;

            }

        }
    }

    /**
     * Receives the request in JSON format. The object is deserialized as NetworkContainer
     * object {@link NetworkContainer}. Based on first argument of the NetworkContainer the
     * request operation is get and routed to the appropriate functionality.
     */
    @Override
    public void run() {
        try {
            String message = receiveRequest();
            NetworkContainer networkContainerRequestDeserialized = gson.fromJson(message, NetworkContainer.class);
            RequestOperation requestOperation = networkContainerRequestDeserialized.getRequestOperation();

            switch (requestOperation) {
                case GET_USER_BY_USERNAME:
                    getUserByUsername(networkContainerRequestDeserialized);
                    break;
                case CREATE_USER:
                    createUser(networkContainerRequestDeserialized);
                    break;
                case GET_ROLE_ID_BY_NAME:
                    getRoleIdByName(networkContainerRequestDeserialized);
                    break;
                case GET_USERS_BY_FIRST_NAME:
                    getUsersListByFirstName(networkContainerRequestDeserialized);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUsersListByFirstName(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        String firstNameDeserialized = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), String.class);
        List<User> fetchedUserList=userController.getUsersListByFirstName(firstNameDeserialized);
        objectSerialized = gson.toJson(fetchedUserList);
        networkContainer = new NetworkContainer(GET_USERS_BY_FIRST_NAME, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
    }

    /**
     * The second argument of the network container is unparsed from JSON format and passed
     * as a parameter to the appropriate method in {@link RoleController} class.
     * The received query result is serialized and assign to the new Network Container.
     * The Network Container is converted in JSON format and is sent as a response to the Client.
     *
     * @param networkContainerRequestDeserialized deserialized network container
     * @throws IOException exceptions produced by failed or
     *                     interrupted I/O operations.
     */
    private void getRoleIdByName(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_ROLE_ID_BY_NAME start");
        String stringDeserialized = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), String.class);
        Role role = roleController.getRoleByName(stringDeserialized);
        objectSerialized = gson.toJson(role);
        networkContainer = new NetworkContainer(GET_ROLE_ID_BY_NAME, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_ROLE_ID_BY_NAME end");
    }

    /**
     * The second argument of the network container is unparsed from JSON format and passed
     * as a parameter to the appropriate method in {@link UserController} class.
     * The received query result is serialized and assign to the new Network Container.
     * The Network Container is converted in JSON format and is sent as a response to the Client.
     *
     * @param networkContainerRequestDeserialized deserialized network container
     * @throws IOException exceptions produced by failed or
     *                     interrupted I/O operations.
     */
    private void createUser(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("CREATE_USER start");
        User userDeserialized = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), User.class);
        //User pula=new User("Marin","Bilba","marinbilba","marin@.sd","123456",new Role((long) 1,"Student"));
        User createdUser = userController.createUser(userDeserialized);
        System.out.println("doneeeee");
        objectSerialized = gson.toJson(createdUser);
        networkContainer = new NetworkContainer(CREATE_USER, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("CREATE_USER end");
    }

    /**
     * The second argument of the network container is unparsed from JSON format and passed
     * as a parameter to the appropriate method in {@link UserController} class.
     * The received query result is serialized and assign to the new Network Container.
     * The Network Container is converted in JSON format and is sent as a response to the Client.
     *
     * @param networkContainerRequestDeserialized deserialized network container
     * @throws IOException exceptions produced by failed or
     *                     interrupted I/O operations.
     */
    private void getUserByUsername(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_USER_BY_USERNAME start");
        String username = networkContainerRequestDeserialized.getSerializedObject();
        System.out.println(username);
        User userFromDatabase = userController.getUserByUsername(username);
        objectSerialized = gson.toJson(userFromDatabase);
        networkContainer = new NetworkContainer(GET_USER_BY_USERNAME, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_USER_BY_USERNAME end");
    }

    /**
     * Receives the input stream as a byte array from the Client and converts it to a
     * String object
     *
     * @return String representation of the Client request.
     * @throws IOException exceptions produced by failed or
     *                     interrupted I/O operations.
     */
    private String receiveRequest() throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] lenbytes = new byte[1024];
        int read = inputStream.read(lenbytes, 0, lenbytes.length);
        return new String(lenbytes, 0, read);
    }


    /**
     * Send response as output stream to the Client and closes the socket connection.
     *
     * @param sendToClient string representation of the message that should be sent to CLient
     * @throws IOException exceptions produced by failed or
     *                     interrupted I/O operations.
     */
    public void sendResponse(String sendToClient) throws IOException {
        // respond to client
        OutputStream outputStream = socket.getOutputStream();
        byte[] responseAsBytes = sendToClient.getBytes();
        outputStream.write(responseAsBytes, 0, responseAsBytes.length);

        System.out.println("Done");
        socket.close();

    }

}
