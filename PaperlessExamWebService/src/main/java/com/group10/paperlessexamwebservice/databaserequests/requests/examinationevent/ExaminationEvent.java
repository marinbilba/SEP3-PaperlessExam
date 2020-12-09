package com.group10.paperlessexamwebservice.databaserequests.requests.examinationevent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.annotations.hidden.HiddenAnnotationExclusionStrategy;
import com.group10.paperlessexamwebservice.databaserequests.requests.shared.RequestSharedMethods;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.ISocketConnector;
import org.springframework.beans.factory.annotation.Autowired;

public class ExaminationEvent implements ExaminationEventRequest {
    @Autowired
    private RequestSharedMethods requestSharedMethods;
    @Autowired
    private ISocketConnector socketConnector;
    //private User cashedUser;
    private Gson gson;

    /**
     * Instantiates a new ExaminationEvent requests object.
     */
    public ExaminationEvent() {
        gson = new GsonBuilder().setExclusionStrategies(new HiddenAnnotationExclusionStrategy()).setPrettyPrinting().create();

    }
}
