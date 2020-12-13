package com.group10.paperlessexamwebservice.databaserequests.requests.questionsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.annotations.hidden.HiddenAnnotationExclusionStrategy;
import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.NetworkContainer;
import com.group10.paperlessexamwebservice.databaserequests.requests.shared.RequestSharedMethods;
import com.group10.paperlessexamwebservice.databaserequests.socketmediator.ISocketConnector;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.QuestionOption;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenQuestion;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The type Question sets requests.
 */
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

    @Override
    public MultipleChoiceQuestion createMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws ServiceNotAvailable {
        MultipleChoiceQuestion createdMultipleChoiceSet;
        // Connect
        try {
            socketConnector.connect();

            // Serialize the object
            String multipleChoiceSetSerialized = gson.toJson(multipleChoiceQuestion);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceSetSerialized, CREATE_MULTIPLE_CHOICE_SET_QUESTION);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            createdMultipleChoiceSet = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), MultipleChoiceQuestion.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return createdMultipleChoiceSet;
    }

    @Override
    public MultipleChoiceQuestion getMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceSetQuestion) throws ServiceNotAvailable {
        MultipleChoiceQuestion fetchedMultipleChoiceSet=null;
        // Connect
        try {
            socketConnector.connect();

            // Serialize the object
            String multipleChoiceSetSerialized = gson.toJson(multipleChoiceSetQuestion);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceSetSerialized, GET_MULTIPLE_CHOICE_SET_QUESTION);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            fetchedMultipleChoiceSet = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), MultipleChoiceQuestion.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedMultipleChoiceSet;
    }

    @Override
    public QuestionOption createMultipleChoiceSetQuestionOption(QuestionOption multipleChoiceQuestionOption) throws ServiceNotAvailable {
        QuestionOption createdMultipleChoiceQuestionOption=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String multipleChoiceSetSerialized = gson.toJson(multipleChoiceQuestionOption);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceSetSerialized, CREATE_MULTIPLE_CHOICE_SET_QUESTION_OPTION);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            createdMultipleChoiceQuestionOption = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), QuestionOption.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return createdMultipleChoiceQuestionOption;
    }

    @Override
    public QuestionOption getMultipleChoiceSetQuestionOption(QuestionOption multipleChoiceQuestionOption) throws ServiceNotAvailable {
        QuestionOption fetchedMultipleChoiceSetQuestionOption=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String multipleChoiceSetSerialized = gson.toJson(multipleChoiceQuestionOption);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceSetSerialized, GET_MULTIPLE_CHOICE_SET_QUESTION_OPTION);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            fetchedMultipleChoiceSetQuestionOption = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), QuestionOption.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedMultipleChoiceSetQuestionOption;
    }

    @Override
    public WrittenSet createWrittenSet(WrittenSet writtenSet) throws ServiceNotAvailable {
        WrittenSet createdWrittenSet=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String writtenSetSerialized = gson.toJson(writtenSet);
            //            Send request
            requestSharedMethods.sendRequest(writtenSetSerialized, CREATE_WRITTEN_SET);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            createdWrittenSet = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), WrittenSet.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return createdWrittenSet;
    }

    @Override
    public WrittenSet getWrittenSet(WrittenSet writtenSet) throws ServiceNotAvailable {
        WrittenSet fetchedWrittenSet=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String writtenSetSerialized = gson.toJson(writtenSet);
            //            Send request
            requestSharedMethods.sendRequest(writtenSetSerialized, GET_WRITTEN_SET);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            fetchedWrittenSet = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), WrittenSet.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedWrittenSet;
    }

    @Override
    public WrittenQuestion createWrittenQuestion(WrittenQuestion writtenQuestion) throws ServiceNotAvailable {
        WrittenQuestion createdWrittenSetQuestion=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String writtenSetQuestionSerialized = gson.toJson(writtenQuestion);
            //            Send request
            requestSharedMethods.sendRequest(writtenSetQuestionSerialized, CREATE_WRITTEN_SET_QUESTION);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            createdWrittenSetQuestion = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), WrittenQuestion.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return createdWrittenSetQuestion;
    }

    @Override
    public WrittenQuestion getWrittenQuestion(WrittenQuestion writtenQuestion) throws ServiceNotAvailable {
        WrittenQuestion fetchedWrittenQuestion=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String writtenSetQuestionSerialized = gson.toJson(writtenQuestion);
            //            Send request
            requestSharedMethods.sendRequest(writtenSetQuestionSerialized, GET_WRITTEN_SET_QUESTION);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            fetchedWrittenQuestion = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), WrittenQuestion.class);
            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedWrittenQuestion;
    }

    @Override
    public List<MultipleChoiceSet> getUsersAllMultipleChoiceSet(User fetchedUser) throws ServiceNotAvailable {
        List<MultipleChoiceSet> fetchedMultipleChoiceList=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String multipleChoiceSetSerialized = gson.toJson(fetchedUser);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceSetSerialized,  GET_ALL_MULTIPLE_CHOICE_SETS);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            MultipleChoiceSet[] tempList = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), MultipleChoiceSet[].class);
            fetchedMultipleChoiceList = Arrays.asList(tempList);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedMultipleChoiceList;
    }

    @Override
    public List<WrittenSet> getUsersAllWrittenSet(User fetchedUser) throws ServiceNotAvailable {
        List<WrittenSet> fetchedWrittenSetList=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String writtenSetListSerialized = gson.toJson(fetchedUser);
            //            Send request
            requestSharedMethods.sendRequest(writtenSetListSerialized,  GET_ALL_WRITTEN_SETS);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            WrittenSet[] tempList = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), WrittenSet[].class);
            fetchedWrittenSetList = Arrays.asList(tempList);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedWrittenSetList;
    }

    /**
     *  Method makes a Delete for the given {@param writtenSetToDelete} request to tier 3
     * @param writtenSetToDelete
     * @return the deleted written set
     * @throws ServiceNotAvailable
     */
    @Override
    public WrittenSet deleteWrittenSet(WrittenSet writtenSetToDelete) throws ServiceNotAvailable {
        WrittenSet deletedWrittenSet=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String writtenSetSerialized = gson.toJson(writtenSetToDelete);
            //            Send request
            requestSharedMethods.sendRequest(writtenSetSerialized,  DELETE_WRITTEN_SET);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            deletedWrittenSet = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), WrittenSet.class);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return deletedWrittenSet;
    }

    /**
     * Method makes a Delete for the given {@param multipleChoiceSetToDelete} request to tier 3
     * @param multipleChoiceSetToDelete
     * @return the deleted multiple choice set  set
     * @throws ServiceNotAvailable
     */
    @Override
    public MultipleChoiceSet deleteMultipleChoiceSet(MultipleChoiceSet multipleChoiceSetToDelete) throws ServiceNotAvailable {
        MultipleChoiceSet deletedMultipleChoiceSet=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String multipleChoiceSetSerialized = gson.toJson(multipleChoiceSetToDelete);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceSetSerialized,  DELETE_MULTIPLE_CHOICE_SET);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            deletedMultipleChoiceSet = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), MultipleChoiceSet.class);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return deletedMultipleChoiceSet;
    }

    @Override
    public MultipleChoiceQuestion deleteMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestionToDelete) throws ServiceNotAvailable {
        MultipleChoiceQuestion deletedMultipleChoiceQuestion=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String multipleChoiceQuestionSerialized = gson.toJson(multipleChoiceQuestionToDelete);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceQuestionSerialized,  DELETE_MULTIPLE_CHOICE_SET_QUESTION);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            deletedMultipleChoiceQuestion = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), MultipleChoiceQuestion.class);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return deletedMultipleChoiceQuestion;
    }

    @Override
    public WrittenQuestion deleteWrittenSetQuestion(WrittenQuestion writtenQuestionToDelete) throws ServiceNotAvailable {
        WrittenQuestion deletedWrittenQuestion=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String writtenQuestionToDeleteSerialized = gson.toJson(writtenQuestionToDelete);
            //            Send request
            requestSharedMethods.sendRequest(writtenQuestionToDeleteSerialized,  DELETE_WRITTEN_QUESTION);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            deletedWrittenQuestion = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), WrittenQuestion.class);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return deletedWrittenQuestion;
    }

    @Override
    public WrittenSet getWrittenSetById(long writtenSetId) throws ServiceNotAvailable {
        WrittenSet fetchedWrittenQuestion=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String idSerialized = gson.toJson(writtenSetId);
            //            Send request
            requestSharedMethods.sendRequest(idSerialized,  GET_WRITTEN_SET_BY_ID);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            fetchedWrittenQuestion = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), WrittenSet.class);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedWrittenQuestion;
    }

    @Override
    public List<WrittenQuestion> getWrittenSetQuestionsByWrittenSet(WrittenSet fetchedWrittenSet) throws ServiceNotAvailable {
        List<WrittenQuestion> fetchedWrittenSetQuestionsList=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String writtenSetSerialized = gson.toJson(fetchedWrittenSet);
            //            Send request
            requestSharedMethods.sendRequest(writtenSetSerialized,  GET_WRITTEN_SET_QUESTIONS_BY_WRITTEN_SET);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            WrittenQuestion[] tempList = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), WrittenQuestion[].class);
            fetchedWrittenSetQuestionsList = Arrays.asList(tempList);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedWrittenSetQuestionsList;
    }

    @Override
    public List<MultipleChoiceQuestion> getMultipleChoiceQuestionByMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable {
        List<MultipleChoiceQuestion> fetchedMultipleChoiceQuestionList=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String writtenSetSerialized = gson.toJson(multipleChoiceSet);
            //            Send request
            requestSharedMethods.sendRequest(writtenSetSerialized,  GET_MULTIPLE_CHOICE_QUESTIONS_BY_MULTIPLE_CHOICE);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            MultipleChoiceQuestion[] tempList = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), MultipleChoiceQuestion[].class);
            fetchedMultipleChoiceQuestionList = Arrays.asList(tempList);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedMultipleChoiceQuestionList;
    }

    @Override
    public List<QuestionOption> getMultipleChoiceQuestionOptionsByMultipleChoiceQuestion(MultipleChoiceQuestion question) throws ServiceNotAvailable {
        List<QuestionOption> fetchedQuestionOptionList=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String multipleChoiceQuestionSerialized = gson.toJson(question);
            //            Send request
            requestSharedMethods.sendRequest(multipleChoiceQuestionSerialized, GET_MULTIPLE_CHOICE_QUESTION_OPTIONS_BY_MULTIPLE_CHOICE_QUESTION);
            //            Read response
            String responseMessage = socketConnector.readFromServer();
            NetworkContainer networkContainerResponseDeserialized = gson.fromJson(responseMessage, NetworkContainer.class);
            QuestionOption[] tempList = gson.fromJson(networkContainerResponseDeserialized.getSerializedObject(), QuestionOption[].class);
            fetchedQuestionOptionList = Arrays.asList(tempList);

            //            Disconnect
            socketConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceNotAvailable("Couldn't connect to the server");
        }
        return fetchedQuestionOptionList;
    }

    @Override
    public MultipleChoiceSet getMultipleChoiceSetById(long multipleChoiceSetId) throws ServiceNotAvailable {
        MultipleChoiceSet fetchedMultipleChoiceSet=null;
        // Connect
        try {
            socketConnector.connect();
            // Serialize the object
            String idSerialized = gson.toJson(multipleChoiceSetId);
            //            Send request
            requestSharedMethods.sendRequest(idSerialized,  GET_MULTIPLE_CHOICE_SET_BY_ID);
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


}
