package com.group10.paperlessexamwebservice.databaserequests.requests.examinationevent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.annotations.hidden.HiddenAnnotationExclusionStrategy;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.NetworkContainer;
import com.group10.paperlessexamwebservice.databaserequests.requests.shared.RequestSharedMethods;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.ISocketConnector;
import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
            System.out.println(examinationEventSerialized);
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

    @Override
    public List<ExaminationEvent> getTeachersExamEvents(String teacherId) throws ServiceNotAvailable {
        List<ExaminationEvent> fetchedExaminationEvents = null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String teacherIdSerialized = gson.toJson(teacherId);
            //            Send request
            requestSharedMethods.sendRequest(teacherIdSerialized, GET_TEACHER_EXAMINATION_EVENTS);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            ExaminationEvent[] tempList = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), ExaminationEvent[].class);
            fetchedExaminationEvents = Arrays.asList(tempList);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedExaminationEvents;
    }


    @Override
    public List<ExaminationEvent> getStudentsExamEvents(String studentId) throws ServiceNotAvailable {
        List<ExaminationEvent> fetchedExaminationEvents = null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String teacherIdSerialized = gson.toJson(studentId);
            //            Send request
            requestSharedMethods.sendRequest(teacherIdSerialized, GET_STUDENT_EXAMINATION_EVENTS);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            ExaminationEvent[] tempList = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), ExaminationEvent[].class);
            fetchedExaminationEvents = Arrays.asList(tempList);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedExaminationEvents;
    }

    @Override
    public ExaminationEvent getExaminationEventById(String examinationEventId) throws ServiceNotAvailable {
        ExaminationEvent fetchedExaminationEvent = null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String teacherIdSerialized = gson.toJson(examinationEventId);
            //            Send request
            requestSharedMethods.sendRequest(teacherIdSerialized,  GET_EXAMINATION_EVENT_BY_ID);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            fetchedExaminationEvent = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), ExaminationEvent.class);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedExaminationEvent;
    }

    @Override
    public List<MultipleChoiceSet> getExaminationEventMultipleChoiceSets(ExaminationEvent fetchedExaminationEvent) throws ServiceNotAvailable {
        List<MultipleChoiceSet> fetchedMultipleChoiceSets = null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String fetchedExaminationEventSerialized = gson.toJson(fetchedExaminationEvent);
            //            Send request
            requestSharedMethods.sendRequest(fetchedExaminationEventSerialized, GET_EXAMINATION_EVENT_MULTIPLE_CHOICE_SETS);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            MultipleChoiceSet[] tempList = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), MultipleChoiceSet[].class);
            fetchedMultipleChoiceSets = Arrays.asList(tempList);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedMultipleChoiceSets;
    }

    @Override
    public List<WrittenSet> getExaminationEventWrittenSets(ExaminationEvent fetchedExaminationEvent) throws ServiceNotAvailable {
        List<WrittenSet> fetchedWrittenSets = null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String fetchedExaminationEventSerialized = gson.toJson(fetchedExaminationEvent);
            //            Send request
            requestSharedMethods.sendRequest(fetchedExaminationEventSerialized, GET_EXAMINATION_EVENT_WRITTEN_SETS);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            WrittenSet[] tempList = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), WrittenSet[].class);
            fetchedWrittenSets = Arrays.asList(tempList);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedWrittenSets;
    }

}
