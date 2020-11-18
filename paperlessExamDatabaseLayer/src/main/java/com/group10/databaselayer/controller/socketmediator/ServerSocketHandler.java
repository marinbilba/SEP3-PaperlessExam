package com.group10.databaselayer.controller.socketmediator;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.databaselayer.controller.RoleController;
import com.group10.databaselayer.controller.UserController;
import com.group10.databaselayer.controller.networkcontainer.NetworkContainer;
import com.group10.databaselayer.controller.networkcontainer.RequestOperation;
import com.group10.databaselayer.entity.User;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;


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
 * @version 2.4
 */
public class ServerSocketHandler implements Runnable {

    private final Socket socket;
    private final HashSet<Object> controllersSet;

    private UserController userController;
    private RoleController roleController;

    private final Gson gson;

    /**
     * Instantiates a new Server socket handler.
     *
     * @param socket         the socket
     * @param controllersSet the controllers set
     * @throws IOException the io exception
     */
    public ServerSocketHandler(Socket socket, HashSet<Object> controllersSet)
            throws IOException {
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
            if (controller instanceof UserController) {
                this.userController = (UserController) controller;
                System.out.println(userController.hashCode());
//     wtf IS THIS???????
 userController.getUserByUsername("s");
            } else if (controller instanceof RoleController) {
                this.roleController = (RoleController) controller;
            }

        }
    }

    /**
     * Gets controllers set.
     *
     * @return the controllers set
     */
    public HashSet<Object> getControllersSet() {
        return controllersSet;
    }

    @Override
    public void run() {

        try {
            String message = receiveRequest();
            NetworkContainer networkContainerRequestDeserialized = gson.fromJson(message, NetworkContainer.class);
            System.out.println(networkContainerRequestDeserialized.getRequestOperation());
            RequestOperation requestOperation = networkContainerRequestDeserialized.getRequestOperation();
            System.out.println(requestOperation);
            switch (requestOperation) {
                case USERNAME_EXISTS:

                    String username = networkContainerRequestDeserialized.getSerializedObject();
                    System.out.println(username);
                    User userFromDatabase = userController.getUserByUsername(username);
                    String userSerialized = gson.toJson(userFromDatabase);
                    NetworkContainer networkContainer = new NetworkContainer(RequestOperation.USERNAME_EXISTS, userSerialized);
                    String stringResponseSerialized = gson.toJson(networkContainer);
                    System.out.println(stringResponseSerialized);
                    sendResponse(stringResponseSerialized);
                    break;
            }
            System.out.println("Received from client: " + message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receiveRequest() throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] lenbytes = new byte[1024];
        int read = inputStream.read(lenbytes, 0, lenbytes.length);
        return new String(lenbytes, 0, read);
    }


    /**
     * Send response.
     *
     * @param sendToClient the send to client
     * @throws IOException the io exception
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
