package com.group10.paperlessexamwebservice.databaserequests.requests.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.NetworkContainer;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.ISocketConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestSharedMethods {
    /**
     * The Socket connector.
     */
    @Autowired
    ISocketConnector socketConnector;
    //private User cashedUser;
    private Gson gson;

    /**
     * Instantiates a new User requests.
     */
    public RequestSharedMethods() {
        gson = new GsonBuilder().setPrettyPrinting().create();

    }

    /**
     * Sends a request through the socket connection.
     * 1. Create the NetworkContainer with the received parameters{@param objectSerialized},{@param requestOperation}.
     * 2. Serialize the Network Container.
     * 3. Send the Network Container as input stream
     *
     * @param serializedObject used as the second argument in the NetworkContainer <i>MUST be serialized</>
     * @param requestOperation operation that should be performed.
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    public void sendRequest(String serializedObject, RequestOperation requestOperation) throws IOException {
        NetworkContainer networkContainer = new NetworkContainer(requestOperation, serializedObject);
        String networkContainerSerialized = gson.toJson(networkContainer);
        socketConnector.sendToServer(networkContainerSerialized);
    }

}
