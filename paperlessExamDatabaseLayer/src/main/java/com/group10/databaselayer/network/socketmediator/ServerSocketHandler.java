package com.group10.databaselayer.network.socketmediator;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.databaselayer.annotations.hidden.HiddenAnnotationExclusionStrategy;
import com.group10.databaselayer.dataaccessobject.ExaminationEventDAO;
import com.group10.databaselayer.dataaccessobject.RoleDAO;
import com.group10.databaselayer.dataaccessobject.UserDAO;
import com.group10.databaselayer.network.networkcontainer.NetworkContainer;
import com.group10.databaselayer.network.networkcontainer.RequestOperation;
import com.group10.databaselayer.dataaccessobject.questions.MultipleChoiceQuestionsDAO;
import com.group10.databaselayer.dataaccessobject.questions.WrittenQuestionsDAO;
import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.multiplechoice.QuestionOption;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.user.Role;
import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.exception.user.UserWasNotDeleted;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.group10.databaselayer.network.networkcontainer.RequestOperation.*;


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

public class ServerSocketHandler implements Runnable {

    private final Socket socket;
    private final HashSet<Object> controllersSet;

    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private MultipleChoiceQuestionsDAO multipleChoiceQuestionsDAO;
    private WrittenQuestionsDAO writtenQuestionsDAO;
    private ExaminationEventDAO examinationEventDAO;

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
            if (controller instanceof RoleDAO) {
                this.roleDAO = (RoleDAO) controller;
            } else if (controller instanceof UserDAO) {
                this.userDAO = (UserDAO) controller;
            } else if (controller instanceof MultipleChoiceQuestionsDAO) {
                this.multipleChoiceQuestionsDAO = (MultipleChoiceQuestionsDAO) controller;
            } else if (controller instanceof WrittenQuestionsDAO) {
                this.writtenQuestionsDAO = (WrittenQuestionsDAO) controller;
            }else if (controller instanceof ExaminationEventDAO) {
                this.examinationEventDAO = (ExaminationEventDAO) controller;
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
//                Multiple choice set
                case GET_MULTIPLE_CHOICE_SET:
                    getMultipleChoiceSet(networkContainerRequestDeserialized);
                    break;
                case CREATE_MULTIPLE_CHOICE_SET:
                    createMultipleChoiceSet(networkContainerRequestDeserialized);
                    break;
                case GET_MULTIPLE_CHOICE_SET_BY_ID:
                    getMultipleChoiceSetById(networkContainerRequestDeserialized);
                    break;
                case CREATE_MULTIPLE_CHOICE_SET_QUESTION:
                    createMultipleChoiceSetQuestion(networkContainerRequestDeserialized);
                    break;
                case GET_MULTIPLE_CHOICE_SET_QUESTION:
                    getMultipleChoiceSetQuestion(networkContainerRequestDeserialized);
                    break;
                case GET_MULTIPLE_CHOICE_QUESTIONS_BY_MULTIPLE_CHOICE:
                    getMultipleChoiceSetQuestionByMultipleChoiceSet(networkContainerRequestDeserialized);
                    break;
                    case DELETE_MULTIPLE_CHOICE_SET_QUESTION:
                    deleteMultipleChoiceQuestion(networkContainerRequestDeserialized);
                    break;

                case CREATE_MULTIPLE_CHOICE_SET_QUESTION_OPTION:
                    createMultipleChoiceSetQuestionOption(networkContainerRequestDeserialized);
                    break;
                case GET_MULTIPLE_CHOICE_SET_QUESTION_OPTION:
                    getMultipleChoiceSetQuestionOption(networkContainerRequestDeserialized);
                    break;
                case GET_MULTIPLE_CHOICE_QUESTION_OPTIONS_BY_MULTIPLE_CHOICE_QUESTION:
                    getMultipleChoiceQuestionOptionsByMultipleChoiceQuestion(networkContainerRequestDeserialized);
                    break;
                case CREATE_WRITTEN_SET:
                    createWrittenSet(networkContainerRequestDeserialized);
                    break;
                case GET_WRITTEN_SET_BY_ID:
                    getWrittenSetById(networkContainerRequestDeserialized);
break;
                case DELETE_MULTIPLE_CHOICE_SET:
                    deleteMultipleChoiceSet(networkContainerRequestDeserialized);
                    break;
//                    Written set
                case DELETE_WRITTEN_SET:
                    deleteWrittenSet(networkContainerRequestDeserialized);
                    break;
                case GET_WRITTEN_SET:
                    getWrittenSet(networkContainerRequestDeserialized);
                    break;
                case CREATE_WRITTEN_SET_QUESTION:
                    createWrittenSetQuestion(networkContainerRequestDeserialized);
                    break;
                case GET_WRITTEN_SET_QUESTION:
                    getWrittenSetQuestion(networkContainerRequestDeserialized);
                    break;
                case GET_WRITTEN_SET_QUESTIONS_BY_WRITTEN_SET:
                    getWrittenSetQuestionsByWrittenSet(networkContainerRequestDeserialized);
                break;
                    case DELETE_WRITTEN_QUESTION:
                    deleteWrittenSetQuestion(networkContainerRequestDeserialized);
                    break;
                case GET_ALL_MULTIPLE_CHOICE_SETS:
                    getAllUsersMultipleChoiceSet(networkContainerRequestDeserialized);
                    break;
                case GET_ALL_WRITTEN_SETS:
                    getAllUsersWrittenSet(networkContainerRequestDeserialized);
                    break;
                    //Examination Event
                case CREATE_EXAMINATION_EVENT:
                    createExaminationEvent(networkContainerRequestDeserialized);
                    break;
                case GET_TEACHER_EXAMINATION_EVENTS:
                    getTeachersExaminationEvents(networkContainerRequestDeserialized);
break;


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMultipleChoiceQuestionOptionsByMultipleChoiceQuestion(NetworkContainer networkContainerRequestDeserialized) throws IOException {

        System.out.println("GET_MULTIPLE_CHOICE_QUESTION_OPTIONS_BY_MULTIPLE_CHOICE_QUESTION start");
        MultipleChoiceQuestion question = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), MultipleChoiceQuestion.class);
        List<QuestionOption>  fetchedMultipleChoiceQuestions = multipleChoiceQuestionsDAO.getAllQuestionOptionsByMultipleChoiceQuestion(question);
        objectSerialized = gson.toJson(fetchedMultipleChoiceQuestions);
        networkContainer = new NetworkContainer(GET_MULTIPLE_CHOICE_QUESTION_OPTIONS_BY_MULTIPLE_CHOICE_QUESTION, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_MULTIPLE_CHOICE_QUESTION_OPTIONS_BY_MULTIPLE_CHOICE_QUESTION end");
    }


    private void getMultipleChoiceSetQuestionByMultipleChoiceSet(NetworkContainer networkContainerRequestDeserialized) throws IOException {

        System.out.println("GET_MULTIPLE_CHOICE_QUESTIONS_BY_MULTIPLE_CHOICE start");
        MultipleChoiceSet multipleChoiceSet = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), MultipleChoiceSet.class);
        List<MultipleChoiceQuestion>  fetchedMultipleChoiceQuestion = multipleChoiceQuestionsDAO.getAllQuestionsByMultipleChoiceSet(multipleChoiceSet);
        objectSerialized = gson.toJson(fetchedMultipleChoiceQuestion);
        networkContainer = new NetworkContainer(GET_MULTIPLE_CHOICE_QUESTIONS_BY_MULTIPLE_CHOICE, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_MULTIPLE_CHOICE_QUESTIONS_BY_MULTIPLE_CHOICE end");
    }

    private void getWrittenSetQuestionsByWrittenSet(NetworkContainer networkContainerRequestDeserialized) throws IOException {

        System.out.println("GET_WRITTEN_SET_QUESTIONS_BY_WRITTEN_SET start");
        WrittenSet writtenSet = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), WrittenSet.class);
        List<WrittenQuestion>  fetchedWrittenQuestion = writtenQuestionsDAO.getQuestionsByWrittenSet(writtenSet);
        objectSerialized = gson.toJson(fetchedWrittenQuestion);
        networkContainer = new NetworkContainer(GET_WRITTEN_SET_QUESTIONS_BY_WRITTEN_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_WRITTEN_SET_QUESTIONS_BY_WRITTEN_SET end");
    }

    private void getWrittenSetById(NetworkContainer networkContainerRequestDeserialized) throws IOException {

        System.out.println("GET_WRITTEN_SET_BY_ID start");
        long writtenSetId = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), Long.class);
        Optional<WrittenSet> fetchedWrittenSet = writtenQuestionsDAO.getWrittenSetById(writtenSetId);
        objectSerialized = gson.toJson(fetchedWrittenSet.get());
        networkContainer = new NetworkContainer(GET_WRITTEN_SET_BY_ID, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_WRITTEN_SET_BY_ID end");
    }
    private void getMultipleChoiceSetById(NetworkContainer networkContainerRequestDeserialized) throws IOException {

        System.out.println("GET_MULTIPLE_CHOICE_SET_BY_ID start");
        long multipleChoiceSetId = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), Long.class);
        Optional<MultipleChoiceSet> fetchedMultipleChoiceSet = multipleChoiceQuestionsDAO.getMultipleChoiceSetById(multipleChoiceSetId);
        objectSerialized = gson.toJson(fetchedMultipleChoiceSet.get());
        networkContainer = new NetworkContainer(GET_MULTIPLE_CHOICE_SET_BY_ID, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_WRITTEN_SET_BY_ID end");
    }

    private void deleteMultipleChoiceQuestion(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("DELETE_MULTIPLE_CHOICE_SET_QUESTION start");
        MultipleChoiceQuestion multipleChoiceQuestionToDelete = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), MultipleChoiceQuestion.class);
        MultipleChoiceQuestion deletedMultipleChoiceQuestion = multipleChoiceQuestionsDAO.deleteMultipleChoiceQuestion(multipleChoiceQuestionToDelete);
        objectSerialized = gson.toJson(deletedMultipleChoiceQuestion);
        networkContainer = new NetworkContainer(DELETE_MULTIPLE_CHOICE_SET_QUESTION, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("DELETE_MULTIPLE_CHOICE_SET_QUESTION end");
    }

    private void deleteWrittenSetQuestion(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("DELETE_WRITTEN_QUESTION start");
        WrittenQuestion writtenSetQuestionToDelete = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), WrittenQuestion.class);
        WrittenQuestion deletedWrittenQuestion = writtenQuestionsDAO.deleteWrittenSetQuestion(writtenSetQuestionToDelete);
        objectSerialized = gson.toJson(deletedWrittenQuestion);
        networkContainer = new NetworkContainer(DELETE_WRITTEN_QUESTION, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("DELETE_WRITTEN_QUESTION end");
    }

    private void deleteWrittenSet(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("DELETE_WRITTEN_SET start");
        WrittenSet writtenSetToDelete = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), WrittenSet.class);
        WrittenSet deletedWrittenSet = writtenQuestionsDAO.deleteWrittenSet(writtenSetToDelete);
        objectSerialized = gson.toJson(deletedWrittenSet);
        networkContainer = new NetworkContainer(DELETE_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("DELETE_WRITTEN_SET end");
    }

    private void deleteMultipleChoiceSet(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("DELETE_MULTIPLE_CHOICE_SET start");
        MultipleChoiceSet multipleChoiceSetToDelete = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), MultipleChoiceSet.class);
        MultipleChoiceSet deletedMultipleChoiceSet = multipleChoiceQuestionsDAO.deleteMultipleChoiceSet(multipleChoiceSetToDelete);
        objectSerialized = gson.toJson(deletedMultipleChoiceSet);
        networkContainer = new NetworkContainer(DELETE_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("DELETE_MULTIPLE_CHOICE_SET end");
    }

    private void getTeachersExaminationEvents(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_TEACHER_EXAMINATION_EVENTS start");
        String teacherId = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), String.class);
        long parsedTeacherId= Integer.parseInt(teacherId);
//        get user with id 10
        User user= userDAO.getUserById(parsedTeacherId);

        List<ExaminationEvent> createdExaminationEvent = examinationEventDAO.getTeachersExaminationEvents(user);
        objectSerialized = gson.toJson(createdExaminationEvent);
        networkContainer = new NetworkContainer(GET_TEACHER_EXAMINATION_EVENTS, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_TEACHER_EXAMINATION_EVENTS end");
    }

    private void createExaminationEvent(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("CREATE_EXAMINATION_EVENT start");
        ExaminationEvent examinationEventDeserialized = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), ExaminationEvent.class);
        ExaminationEvent createdExaminationEvent = examinationEventDAO.createUpdate(examinationEventDeserialized);
        objectSerialized = gson.toJson(createdExaminationEvent);
        networkContainer = new NetworkContainer(CREATE_EXAMINATION_EVENT, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("CREATE_EXAMINATION_EVENT end");
    }

    private void getAllUsersWrittenSet(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_ALL_WRITTEN_SETS start");
        User userDeserialized = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), User.class);
        List<WrittenSet> fetchedMultipleChoiceSetList = writtenQuestionsDAO.getAllWrittenSet(userDeserialized);
        objectSerialized = gson.toJson(fetchedMultipleChoiceSetList);
        networkContainer = new NetworkContainer(GET_ALL_WRITTEN_SETS, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_ALL_WRITTEN_SETS end");
    }

    private void getAllUsersMultipleChoiceSet(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_ALL_MULTIPLE_CHOICE_SETS start");
        User userDeserialized = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), User.class);
        List<MultipleChoiceSet> fetchedMultipleChoiceSetList = multipleChoiceQuestionsDAO.getAllUsersMultipleChoiceSet(userDeserialized);
        objectSerialized = gson.toJson(fetchedMultipleChoiceSetList);
        networkContainer = new NetworkContainer(GET_ALL_MULTIPLE_CHOICE_SETS, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_ALL_MULTIPLE_CHOICE_SETS end");
    }

    private void getWrittenSetQuestion(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_WRITTEN_SET_QUESTION start");
        WrittenQuestion writtenSetQuestion = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), WrittenQuestion.class);
        WrittenQuestion fetchedWrittenQuestion = null;
        fetchedWrittenQuestion = writtenQuestionsDAO.getWrittenSetQuestion(writtenSetQuestion);
        objectSerialized = gson.toJson(fetchedWrittenQuestion);
        networkContainer = new NetworkContainer(GET_WRITTEN_SET_QUESTION, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_WRITTEN_SET_QUESTION end");
    }

    private void createWrittenSetQuestion(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("CREATE_WRITTEN_SET_QUESTION start");
        WrittenQuestion writtenSetQuestion = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), WrittenQuestion.class);
        WrittenQuestion createdWrittenSetQuestion = null;
        createdWrittenSetQuestion = writtenQuestionsDAO.createUpdateWrittenSetQuestion(writtenSetQuestion);
        objectSerialized = gson.toJson(createdWrittenSetQuestion);
        networkContainer = new NetworkContainer(CREATE_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("CREATE_WRITTEN_SET_QUESTION end");
    }

    private void getWrittenSet(NetworkContainer networkContainerRequestDeserialized) throws IOException {

        System.out.println("GET_WRITTEN_SET start");
        WrittenSet writtenSet = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), WrittenSet.class);
        WrittenSet fetchedWrittenSet = null;
        fetchedWrittenSet = writtenQuestionsDAO.getWrittenSet(writtenSet);
        objectSerialized = gson.toJson(fetchedWrittenSet);
        networkContainer = new NetworkContainer(CREATE_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_WRITTEN_SET end");
    }

    private void createWrittenSet(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("CREATE_WRITTEN_SET start");
        WrittenSet writtenSet = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), WrittenSet.class);
        WrittenSet createdWrittenSet = null;
        createdWrittenSet = writtenQuestionsDAO.createUpdateWrittenSet(writtenSet);
        objectSerialized = gson.toJson(createdWrittenSet);
        networkContainer = new NetworkContainer(CREATE_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("CREATE_WRITTEN_SET end");
    }

    private void getMultipleChoiceSetQuestionOption(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_MULTIPLE_CHOICE_SET_QUESTION_OPTION start");
        QuestionOption multipleChoiceSetQuestionOption = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), QuestionOption.class);
        QuestionOption fetchedMultipleChoiceSetQuestionOption = null;
        fetchedMultipleChoiceSetQuestionOption = multipleChoiceQuestionsDAO.getMultipleChoiceSetQuestionOption(multipleChoiceSetQuestionOption);
        objectSerialized = gson.toJson(fetchedMultipleChoiceSetQuestionOption);
        networkContainer = new NetworkContainer(CREATE_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_MULTIPLE_CHOICE_SET_QUESTION_OPTION end");
    }

    private void createMultipleChoiceSetQuestionOption(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("CREATE_MULTIPLE_CHOICE_SET_QUESTION_OPTION start");
        QuestionOption multipleChoiceSetQuestionOption = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), QuestionOption.class);
        QuestionOption createdMultipleChoiceSetQuestionOption = null;


        createdMultipleChoiceSetQuestionOption = multipleChoiceQuestionsDAO.createUpdateMultipleChoiceSetQuestionOption(multipleChoiceSetQuestionOption);
        objectSerialized = gson.toJson(createdMultipleChoiceSetQuestionOption);
        networkContainer = new NetworkContainer(CREATE_MULTIPLE_CHOICE_SET_QUESTION_OPTION, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("CREATE_MULTIPLE_CHOICE_SET_QUESTION_OPTION end");
    }

    private void getMultipleChoiceSetQuestion(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_MULTIPLE_CHOICE_SET_QUESTIONS start");
        MultipleChoiceQuestion multipleChoiceSetQuestion = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), MultipleChoiceQuestion.class);
        MultipleChoiceQuestion fetchedMultipleChoiceSet = null;

        fetchedMultipleChoiceSet = multipleChoiceQuestionsDAO.getMultipleChoiceSetQuestion(multipleChoiceSetQuestion);
        objectSerialized = gson.toJson(fetchedMultipleChoiceSet);
        networkContainer = new NetworkContainer(GET_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_MULTIPLE_CHOICE_SET_QUESTIONS end");
    }

    private void createMultipleChoiceSetQuestion(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("CREATE_MULTIPLE_CHOICE_SET_QUESTION start");
        MultipleChoiceQuestion multipleChoiceSetQuestion = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), MultipleChoiceQuestion.class);
        MultipleChoiceQuestion createdMultipleChoiceSet = null;
        createdMultipleChoiceSet = multipleChoiceQuestionsDAO.createUpdateMultipleChoiceSetQuestion(multipleChoiceSetQuestion);
        objectSerialized = gson.toJson(createdMultipleChoiceSet);
        networkContainer = new NetworkContainer(CREATE_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("CREATE_MULTIPLE_CHOICE_SET_QUESTION end");
    }


    private void deleteUser(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("DELETE_USER start");
        User userToDelete = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), User.class);
        User deletedUser = null;
//        delete user
        userDAO.deleteUser(userToDelete);
//        check if user was deleted
        deletedUser = userDAO.getUserByUsername(userToDelete.getUsername());
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

        User user = userDAO.getUserByUsername("silvmandrila");

        multipleChoiceSet.setUser(user);


        createdMultipleChoiceSet = multipleChoiceQuestionsDAO.createUpdateMultipleChoiceSet(multipleChoiceSet);
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

        fetchedMultipleChoiceSet = multipleChoiceQuestionsDAO.getMultipleChoiceSet(multipleChoiceSet);
        objectSerialized = gson.toJson(fetchedMultipleChoiceSet);
        networkContainer = new NetworkContainer(GET_MULTIPLE_CHOICE_SET, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_MULTIPLE_CHOICE_SET end");
    }

    private void getUsersListByFirstName(NetworkContainer networkContainerRequestDeserialized) throws IOException {
        System.out.println("GET_USERS_BY_FIRST_NAME start");
        String firstNameDeserialized = gson.fromJson(networkContainerRequestDeserialized.getSerializedObject(), String.class);
        List<User> fetchedUserList = userDAO.getUsersListByFirstName(firstNameDeserialized);
        objectSerialized = gson.toJson(fetchedUserList);
        networkContainer = new NetworkContainer(GET_USERS_BY_FIRST_NAME, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_USERS_BY_FIRST_NAME start");
    }

    /**
     * The second argument of the network container is unparsed from JSON format and passed
     * as a parameter to the appropriate method in {@link RoleDAO} class.
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
        Role role = roleDAO.getRoleByName(stringDeserialized);
        objectSerialized = gson.toJson(role);
        networkContainer = new NetworkContainer(GET_ROLE_ID_BY_NAME, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("GET_ROLE_ID_BY_NAME end");
    }

    /**
     * The second argument of the network container is unparsed from JSON format and passed
     * as a parameter to the appropriate method in {@link UserDAO} class.
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
        User createdUser = userDAO.createUpdateUser(userDeserialized);
        System.out.println("doneeeee");
        objectSerialized = gson.toJson(createdUser);
        networkContainer = new NetworkContainer(CREATE_UPDATE_USER, objectSerialized);
        stringResponseSerialized = gson.toJson(networkContainer);
        sendResponse(stringResponseSerialized);
        System.out.println("CREATE_USER end");
    }

    /**
     * The second argument of the network container is unparsed from JSON format and passed
     * as a parameter to the appropriate method in {@link UserDAO} class.
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
        User userFromDatabase = userDAO.getUserByUsername(username);
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
        byte[] lenbytes = new byte[24576];
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
