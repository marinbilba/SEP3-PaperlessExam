package com.group10.databaselayer.controller.socketmediator;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.databaselayer.annotations.hidden.HiddenAnnotationExclusionStrategy;
import com.group10.databaselayer.controller.RoleController;
import com.group10.databaselayer.controller.UserController;
import com.group10.databaselayer.controller.networkcontainer.NetworkContainer;
import com.group10.databaselayer.controller.networkcontainer.RequestOperation;
import com.group10.databaselayer.controller.questions.MultipleChoiceQuestionsController;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.user.Role;
import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.exception.questions.TitleOrTopicAreNull;
import com.group10.databaselayer.exception.user.UserWasNotDeleted;

import javax.transaction.Transactional;
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
@Transactional
@org.springframework.transaction.annotation.Transactional
public class ServerSocketHandler implements Runnable {

    private final Socket socket;
    private final HashSet<Object> controllersSet;

    private RoleController roleController;
    private UserController userController;
    private MultipleChoiceQuestionsController multipleChoiceQuestionsController;

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
        gson = new GsonBuilder().setExclusionStrategies(new HiddenAnnotationExclusionStrategy()).setPrettyPrinting().create();
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
            } else if (controller instanceof MultipleChoiceQuestionsController) {
                this.multipleChoiceQuestionsController = (MultipleChoiceQuestionsController) controller;
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
                // User requests
                case GET_USER_BY_USERNAME:
                    getUserByUsername(networkContainerRequestDeserialized);
                    break;
                case CREATE_UPDATE_USER:
                    createUpdateUser(networkContainerRequestDeserialized);
                    break;
                case GET_ROLE_ID_BY_NAME:
                    getRoleIdByName(networkContainerRequestDeserialized);
                    break;
                case GET_USERS_BY_FIRST_NAME:
                    getUsersListByFirstName(networkContainerRequestDeserialized);
                    break;
                case DELETE_USER:
                    deleteUser(networkContainerRequestDeserialized);
                    break;
//                    Questions request
                case GET_MULTIPLE_CHOICE_SET:
                    getMultipleChoiceSet(networkContainerRequestDeserialized);
                    break;
                case CREATE_MULTIPLE_CHOICE_SET:
                    createMultipleChoiceSet(networkContainerRequestDeserialized);
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("DELETE_USER start");
        User userToDelete = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), User.class);
        User deletedUser = null;
//        delete user
        userController.deleteUser(userToDelete);
//        check if user was deleted
        deletedUser = userController.getUserByUsername(userToDelete.getUsername());
        if (deletedUser != null) {
            try {
                throw new UserWasNotDeleted("User could not be deleted");
            } catch (UserWasNotDeleted userWasNotDeleted) {
                userWasNotDeleted.printStackTrace();
            }
        }
        objectSerialized = gson.toJson(userToDelete);
        networkContainer = new NetworkContainer(GET_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("DELETE_USER end");
    }

    private void createMultipleChoiceSet(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("CREATE_MULTIPLE_CHOICE_SET start");
        MultipleChoiceSet multipleChoiceSet = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), MultipleChoiceSet.class);
        MultipleChoiceSet createdMultipleChoiceSet = null;
        MultipleChoiceSet tempMultipleChoiceSet=new MultipleChoiceSet(multipleChoiceSet.getTitle(),multipleChoiceSet.getTopic(),multipleChoiceSet.getUser());
        createdMultipleChoiceSet = multipleChoiceQuestionsController.createUpdateMultipleChoiceSet(tempMultipleChoiceSet);
        objectSerialized = gson.toJson(createdMultipleChoiceSet);
        networkContainer = new NetworkContainer(CREATE_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("CREATE_MULTIPLE_CHOICE_SET end");
    }

    private void getMultipleChoiceSet(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_MULTIPLE_CHOICE_SET start");
        MultipleChoiceSet multipleChoiceSet = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), MultipleChoiceSet.class);
        MultipleChoiceSet fetchedMultipleChoiceSet = null;

            fetchedMultipleChoiceSet = multipleChoiceQuestionsController.getMultipleChoiceSet(multipleChoiceSet);
            objectSerialized = gson.toJson(fetchedMultipleChoiceSet);
        networkContainer = new NetworkContainer(GET_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_MULTIPLE_CHOICE_SET end");
    }

    private void getUsersListByFirstName(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_USERS_BY_FIRST_NAME start");
        String firstNameDeserialized = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), String.class);
        List<User> fetchedUserList = userController.getUsersListByFirstName(firstNameDeserialized);
        objectSerialized = gson.toJson(fetchedUserList);
        networkContainer = new NetworkContainer(GET_USERS_BY_FIRST_NAME, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_USERS_BY_FIRST_NAME start");
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
    private void createUpdateUser(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("CREATE_USER start");
        User userDeserialized = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), User.class);
        //User pula=new User("Marin","Bilba","marinbilba","marin@.sd","123456",new Role((long) 1,"Student"));
        User createdUser = userController.createUpdateUser(userDeserialized);
        System.out.println("doneeeee");
        objectSerialized = gson.toJson(createdUser);
        networkContainer = new NetworkContainer(CREATE_UPDATE_USER, objectSerialized);
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
