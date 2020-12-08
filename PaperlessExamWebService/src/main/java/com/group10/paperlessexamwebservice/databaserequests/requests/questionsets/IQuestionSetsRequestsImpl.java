package com.group10.paperlessexamwebservice.databaserequests.requests.questionsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.annotations.hidden.HiddenAnnotationExclusionStrategy;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.NetworkContainer;
import com.group10.paperlessexamwebservice.databaserequests.requests.shared.RequestSharedMethods;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.ISocketConnector;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation.*;

import java.io.IOException;

@Service
public class IQuestionSetsRequestsImpl implements IQuestionSetsRequests {

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
        gson = new GsonBuilder().setExclusionStrategies(new HiddenAnnotationExclusionStrategy()).setPrettyPrinting().create();;

    }

    @Override
    public MultipleChoiceSet getMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable {
        MultipleChoiceSet fetchedMultipleChoiceSet=null;
        // Connect
        try {
            socketConnector.connect();
            System.out.println("[CLIENT] Connected to server");
            // Serialize the object
            String multipleChoiceSetSerialized = gson.toJson(multipleChoiceSet);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceSetSerialized, GET_MULTIPLE_CHOICE_SET);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            fetchedMultipleChoiceSet = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), MultipleChoiceSet.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedMultipleChoiceSet;
    }

    @Override
    public MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable {
        MultipleChoiceSet createdMultipleChoiceSet;
        // Connect
        try {
            socketConnector.connect();
            System.out.println("[CLIENT] Connected to server");
            // Serialize the object
            String multipleChoiceSetSerialized = gson.toJson(multipleChoiceSet);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceSetSerialized, CREATE_MULTIPLE_CHOICE_SET);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            createdMultipleChoiceSet = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), MultipleChoiceSet.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return createdMultipleChoiceSet;
    }
}
