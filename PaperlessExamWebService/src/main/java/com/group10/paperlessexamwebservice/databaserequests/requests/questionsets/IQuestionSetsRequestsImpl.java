package com.group10.paperlessexamwebservice.databaserequests.requests.questionsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.NetworkContainer;
import com.group10.paperlessexamwebservice.databaserequests.requests.shared.RequestSharedMethods;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.ISocketConnector;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation.*;
import java.io.IOException;

import static com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation.CREATE_USER;

@Service
public class IQuestionSetsRequestsImpl implements IQuestionSetsRequests{

    /**
     * The Socket connector.
     */
    @Autowired
    private ISocketConnector socketConnector;
    @Autowired
    private RequestSharedMethods requestSharedMethods;
    //private User cashedUser;
    private Gson gson;

    /**
     * Instantiates a new User requests.
     */
    public IQuestionSetsRequestsImpl() {
        gson = new GsonBuilder().setPrettyPrinting().create();

    }

    @Override
    public boolean existsMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable {
        boolean multipleChoiceExists;
        // Connect
        try {
            socketConnector.connect();
            System.out.println("[CLIENT] Connected to server");
            // Serialize the object
            String multipleChoiceSetSerialized = gson.toJson(multipleChoiceSet);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceSetSerialized,MULTIPLE_CHOICE_SET_EXISTS);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            multipleChoiceExists = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), Boolean.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return multipleChoiceExists;
    }
}
