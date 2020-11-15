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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Added ServerSocketHandler.java which is handling each Client request as a separate thread.
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
 * @version 1.0
 */
public class ServerSocketHandler  implements Runnable{

    private final Socket socket;
    private final HashSet<Object> controllersSet;

    private  UserController userController;
   private RoleController roleController;

    private final Gson gson;

    public ServerSocketHandler(Socket socket, HashSet<Object> controllersSet)
            throws IOException {
        this.socket = socket;
        this.controllersSet=controllersSet;
        gson = new GsonBuilder().setPrettyPrinting().create();
        parseControllerSet(this.controllersSet);
    }

    private void parseControllerSet(HashSet<Object> controllersSet) {
        for (Object controller : controllersSet) {
            if (controller instanceof UserController) {
                this.userController = (UserController) controller;
                System.out.println(userController.hashCode());
//     wtf IS THIS???????
        userController.getUsersByLastName("s");

            }else if(controller instanceof RoleController){
                this.roleController=(RoleController)controller;
            }

        }
    }

    public HashSet<Object> getControllersSet() {
        return controllersSet;
    }


    @Override
    public void run() {
        System.out.println(userController.hashCode());
        System.out.println("run");


        userController.getUserByUsername("marin");
        System.out.println("run2");
        try {
            InputStream inputStream = socket.getInputStream();
                byte[] lenbytes = new byte[1024];
                int read = inputStream.read(lenbytes, 0, lenbytes.length);
                String message = new String(lenbytes, 0, read);

                NetworkContainer networkContainerDeserialized = gson.fromJson(message, NetworkContainer.class);
                System.out.println(networkContainerDeserialized.getRequestOperation());
                RequestOperation requestOperation = networkContainerDeserialized.getRequestOperation();
                System.out.println(requestOperation);
                switch (requestOperation) {
                    case USERNAME_EXISTS:
                        String username = (String) networkContainerDeserialized.getObject();
                        System.out.println(username);
                        User userFromDatabase = userController.getUserByUsername(username);
                        System.out.println("fs");
                        NetworkContainer networkContainer = new NetworkContainer(userFromDatabase);
                        String stringSerialized = gson.toJson(networkContainer);
                        sendResponse(stringSerialized);
                        break;
                }
                System.out.println("Received from client: " + message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendResponse(String sendToClient) throws IOException {
        // respond to client
        OutputStream outputStream = socket.getOutputStream();
        byte[] responseAsBytes = sendToClient.getBytes();
        outputStream.write(responseAsBytes, 0, responseAsBytes.length);

        System.out.println("Done");
        socket.close();

    }



}
