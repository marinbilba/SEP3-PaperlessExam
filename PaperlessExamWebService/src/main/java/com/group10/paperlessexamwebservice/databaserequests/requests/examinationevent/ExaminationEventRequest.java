package com.group10.paperlessexamwebservice.databaserequests.requests.examinationevent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.annotations.hidden.HiddenAnnotationExclusionStrategy;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.NetworkContainer;
import com.group10.paperlessexamwebservice.databaserequests.requests.shared.RequestSharedMethods;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.ISocketConnector;
import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation.*;

@Component
public class ExaminationEventRequest implements IExaminationEventRequest {
    @Autowired
    private RequestSharedMethods requestSharedMethods;
    @Autowired
    private ISocketConnector socketConnector;
    //private User cashedUser;
    private Gson gson;

    /**
     * Instantiates a new IExaminationEvent requests object.
     */
    public ExaminationEventRequest() {
        gson = new GsonBuilder().setExclusionStrategies(new HiddenAnnotationExclusionStrategy()).setPrettyPrinting().create();

    }

    @Override
    public ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable {
        ExaminationEvent createdExaminationEvent = null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String examinationEventSerialized = gson.toJson(examinationEvent);
            //            Send request
            requestSharedMethods.sendRequest(examinationEventSerialized,  CREATE_EXAMINATION_EVENT);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            createdExaminationEvent = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), ExaminationEvent.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return createdExaminationEvent;
    }
}
