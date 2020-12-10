package com.group10.paperlessexamwebservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.QuestionOption;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenQuestion;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.NegativeNumberException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.*;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.EmptyMultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.NullQuestionSetQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.user.UserNotFound;
import com.group10.paperlessexamwebservice.service.questionsets.IQuestionSetsService;
import com.group10.paperlessexamwebservice.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Controller for managing question sets, create, find, delete requests
 *
 * @author Marin Bilba
 * @version 1.0
 */
@RestController
@RequestMapping("/questionsets")
public class QuestionSetsController {

    @Autowired
    private IQuestionSetsService questionSetsService;
    /**
     * The User controller.
     */
    @Autowired
    IUserService userController;


    /**
     * Create multiple choice set question.It is processed as a POST request requesting <i>MultipleChoiceSetQuestion object</i>
     * in format of JSON as an argument.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/createMultipleChoiceSet
     * </p>
     *
     * <b>BODY</b>:
     * {
     * "multipleChoiceSet": {
     * "user": {
     * "firstName": "Silvestru",
     * "lastName": "Mandrila",
     * "username": "silvmandrila",
     * "email": "silvmandr@va.cs",
     * "password": "111111",
     * "role": {
     * "id": 1,
     * "name": "Student"
     * }
     * },
     * <p>
     * "title": "Java",
     * "topic": "Core"
     * },
     * "questionNumber": 1,
     * "question": "What is this",
     * "score": 30.0
     * }
     *
     * @param multipleChoiceQuestion the multiple choice question
     * @return <i>HTTP 200 - OK</i> with the created multiple choice set question or <i>HTTP 400 - BAD_REQUEST</i> <i>HTTP 401 - UNAUTHORIZED</i> if the request does not contain the user object <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/addMultipleChoiceQuestion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestion multipleChoiceQuestion) {
        MultipleChoiceQuestion createdMultipleChoiceQuestion = null;
        try {
            createdMultipleChoiceQuestion = questionSetsService.addMultipleChoiceQuestion(multipleChoiceQuestion);
        } catch (QuestionSetAlreadyExists | NegativeNumberException | NullQuestionSet | UnexpectedError | EmptyMultipleChoiceQuestion | EmptyQuestionSetTitleOrTopic e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(createdMultipleChoiceQuestion);
    }

    /**
     * Create multiple choice set question option.It is processed as a POST request requesting <i>QuestionOption object</i>
     * in format of JSON as an argument.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/addMultipleChoiceQuestionOption
     * </p>
     *
     * <b>BODY</b>:
     * {
     * "id": 12,
     * "correctAnswer": true,
     * "answer": "evucii slucii",
     * "multipleChoiceQuestion": {
     * "multipleChoiceSet": {
     * "user": {
     * "id": 10,
     * "firstName": "Silvestru",
     * "lastName": "Mandrila",
     * "username": "silvmandrila",
     * "email": "silvmandr@va.cs",
     * "password": "111111",
     * "role": {
     * "id": 1,
     * "name": "Student"
     * }
     * },
     * "updatedTimestamp": "Dec 8, 2020, 9:44:42 PM",
     * "id": 1,
     * "title": "Java",
     * "topic": "Core"
     * },
     * "questionNumber": 2,
     * "question": "OOP?",
     * "score": 50.0
     * }
     * }
     *
     * @param multipleChoiceQuestionOption the multiple choice question option
     * @return <i>HTTP 200 - OK</i> with the created multiple choice set question or <i>HTTP 400 - BAD_REQUEST</i> <i>HTTP 409 - CONFLICT</i> if the question set was found was not found in the system <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/addMultipleChoiceQuestionOption", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addMultipleChoiceQuestionOption(@RequestBody QuestionOption multipleChoiceQuestionOption) {
        QuestionOption createdQuestionOption = null;
        try {
            createdQuestionOption = questionSetsService.addMultipleChoiceQuestionOption(multipleChoiceQuestionOption);
        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
            emptyMultipleChoiceQuestion.printStackTrace();
        } catch (NullQuestionSet | UnexpectedError | NullQuestionSetQuestion | NegativeNumberException | EmptyQuestionSetTitleOrTopic e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ServiceNotAvailable e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());

        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
            questionSetAlreadyExists.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(questionSetAlreadyExists.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(createdQuestionOption);
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        User user = null;
//        try {
//            user = userController.getUsersByUsername("silvmandrila");
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (UserNotFound userNotFound) {
//            userNotFound.printStackTrace();
//        }
//
//       WrittenSet writtenSet = null;
//        try {
//            writtenSet=questionSetsService.createWrittenSet(new WrittenSet("Geo","Ner",user));
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (UserNotFound userNotFound) {
//            userNotFound.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        }
//        try {
//            writtenSet = questionSetsService.getWrittenSet(new WrittenSet("Romana", "Eseu", user));
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        }
//
//        try {
//           writtenSet= questionSetsService.createWrittenSet(new WrittenSet("Chemist", "Eseu", user));
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (UserNotFound userNotFound) {
//            userNotFound.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        }

//        WrittenQuestion writtenQuestion=null;
//
//        try {
//          writtenQuestion=  questionSetsService.addWrittenQuestion(new WrittenQuestion("What is 2?",122,4,writtenSet));
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        }

//        try {
//            writtenQuestion=questionSetsService.getWrittenQuestion(new WrittenQuestion("What is lif?",12,4,writtenSet));
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
//            nullQuestionSetQuestion.printStackTrace();
//        }


//
//      MultipleChoiceSet ms = null;
//       try {
//            ms = questionSetsService.getMultipleChoiceSet(new MultipleChoiceSet("Java21e1", "Capitals", user));
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        }
//
//        MultipleChoiceQuestion mq = null;
//
//        try {
//            mq = questionSetsService.getMultipleChoiceSetQuestion(new MultipleChoiceQuestion("OOP?", 50, 2, ms));
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (NegativeNumberException e) {
//            e.printStackTrace();
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
//            nullQuestionSetQuestion.printStackTrace();
//        }
//
//
//        try {
//            QuestionOption qo = questionSetsService.addMultipleChoiceQuestionOption(new QuestionOption(true, "yessadsad", mq));
//            System.out.println(qo.getAnswer());
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
//            nullQuestionSetQuestion.printStackTrace();
//        } catch (NegativeNumberException e) {
//            e.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        }

//        QuestionOption questionOption =new QuestionOption(true,"Yes",mq);
//        try {
//            questionSetsService.addMultipleChoiceQuestionOption(questionOption);
//            //QuestionOption questionOption2= questionSetsService.getMultipleChoiceSetQuestionOption(questionOption);
//            //System.out.println(questionOption2.getAnswer());
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
//            nullQuestionSetQuestion.printStackTrace();
//        } catch (NegativeNumberException e) {
//            e.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        }


//
//
//            System.out.println(gson.toJson(mq2));
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (NegativeNumberException e) {
//            e.printStackTrace();
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
//            nullQuestionSetQuestion.printStackTrace();
//        }


//        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(1, "What is this?", 20);
//        QuestionOption questionOption = new QuestionOption(true, "da");
//        QuestionOption questionOption2 = new QuestionOption(false, "nu");
//        multipleChoiceQuestion.addQuestionOption(questionOption);
//        multipleChoiceQuestion.addQuestionOption(questionOption2);


//         MultipleChoiceSet multipleChoiceSet = new MultipleChoiceSet("Java", "Capitals");
//        multipleChoiceSet.setUser(user);
//        System.out.println(gson.toJson(multipleChoiceSet));


        //System.out.println(gson.toJson(multipleChoiceSet));
//        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(1, "What is OOP?", 30);
//multipleChoiceQuestion.setMultipleChoiceSet(multipleChoiceSet);


        //  QuestionOption questionOption = new QuestionOption(true, "Object oriented programing");
        //  QuestionOption questionOption2 = new QuestionOption(false, "Hz");
//        questionOption.setMultipleChoiceQuestion(multipleChoiceQuestion);
//        questionOption2.setMultipleChoiceQuestion(multipleChoiceQuestion);
//
//        multipleChoiceQuestion.setMultipleChoiceSet(multipleChoiceSet);

        //multipleChoiceSet.addQuestion(multipleChoiceQuestion);
        // createMultipleChoiceSet(multipleChoiceSet);

//        List<MultipleChoiceSet> multipleChoiceSetList= null;
//        try {
//            multipleChoiceSetList = questionSetsService.getUsersAllMultipleChoiceSet(user.getUsername());
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        }
//        System.out.println(gson.toJson(multipleChoiceSetList));

    }

    /**
     * Create multiple choice set.It is processed as a POST request requesting <i>MultipleChoiceSet object</i>
     * in format of JSON as an argument.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/createMultipleChoiceSet
     * </p>
     *
     * <b>BODY</b>:
     * {
     * {
     * "user": {
     * "firstName": "Silvestru",
     * "lastName": "Mandrila",
     * "username": "silvmandrila",
     * "email": "silvmandr@va.cs",
     * "password": "111111",
     * "role": {
     * "id": 1,
     * "name": "Student"
     * }
     * },
     * "title": "Java",
     * "topic": "Capitals"
     * }
     *
     * @param multipleChoiceSet the multiple choice set
     * @return <i>HTTP 200 - OK</i> with the created multiple choice set or
     * <i>HTTP 400 - BAD_REQUEST</i> if the set already exists,title or topic are empty or unexpected errors are detected.
     * <i>HTTP 401 - UNAUTHORIZED</i> if the request does not contain the user object
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/createMultipleChoiceSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createMultipleChoiceSet(@RequestBody MultipleChoiceSet multipleChoiceSet) {

        MultipleChoiceSet createdMultipleChoiceSet = null;
        try {
            createdMultipleChoiceSet = questionSetsService.createMultipleChoiceSet(multipleChoiceSet);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (UnexpectedError | QuestionSetAlreadyExists | NullQuestionSet | EmptyQuestionSetTitleOrTopic e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userNotFound.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(createdMultipleChoiceSet);
    }

    /**
     * Create written question response entity.
     *
     *  <p>
     *   <b>EXAMPLE</b>:
     *   <p>
     *   http://{host}:8080/questionsets/createWrittenQuestion
     *   </p>
     *
     *   <b>BODY</b>:
     * @param writtenQuestion the written question
     * @return the response entity
     */
    @RequestMapping(value = "/createWrittenQuestion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createWrittenQuestion(@RequestBody WrittenQuestion writtenQuestion) {
        WrittenQuestion createdWrittenQuestion=null;
        try {
           createdWrittenQuestion= questionSetsService.addWrittenQuestion(writtenQuestion);
        } catch (EmptyMultipleChoiceQuestion |NullQuestionSet |EmptyQuestionSetTitleOrTopic |UnexpectedError|QuestionSetAlreadyExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }  catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());

        }
        return ResponseEntity.status(HttpStatus.OK).body(createdWrittenQuestion);

    }

    /**
     * Create multiple written set response entity.
     * <p>
     *
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/createWrittenSet
     * </p>
     *
     * <b>BODY</b>:
     * {
     * "user": {
     * "id": 10,
     * "firstName": "Silvestru",
     * "lastName": "Mandrila",
     * "username": "silvmandrila",
     * "email": "silvmandr@va.cs",
     * "password": "111111",
     * "role": {
     * "id": 1,
     * "name": "Student"
     * }
     * },
     * "id": 13,
     * "title": "Romana",
     * "topic": "Eseu"
     * }
     *
     * @param writtenSet the written set
     * @return the response entity <i>HTTP 200 - OK</i> with the written set or <i>HTTP 400 - BAD_REQUEST</i> if the set already exists,title or topic are empty or unexpected errors are detected. <i>HTTP 401 - UNAUTHORIZED</i> if the request does not contain the user object <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/createWrittenSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createMultipleWrittenSet(@RequestBody WrittenSet writtenSet) {
        WrittenSet createdWrittenSet = null;
        try {
            createdWrittenSet = questionSetsService.createWrittenSet(writtenSet);
        } catch (NullQuestionSet | EmptyQuestionSetTitleOrTopic | UnexpectedError |QuestionSetAlreadyExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userNotFound.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(createdWrittenSet);
    }

    /**
     * Finds a multiple choice set. It is processed as a POST request requesting the <i>MultipleChoiceSet object</i>
     * in format of JSON as an argument.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/findMultipleChoiceSet
     * </p>
     *
     * <b>BODY</b>:
     * {
     * {
     * "user": {
     * "firstName": "Silvestru",
     * "lastName": "Mandrila",
     * "username": "silvmandrila",
     * "email": "silvmandr@va.cs",
     * "password": "111111",
     * "role": {
     * "id": 1,
     * "name": "Student"
     * }
     * },
     * "title": "Java",
     * "topic": "Capitals"
     * }
     *
     * @param multipleChoiceSet the multiple choice set that should be found
     * @return <i>HTTP 200 - OK</i> with the found multiple choice set or <i>HTTP 400 - BAD_REQUEST</i> if the question set's title or topic are empty or an unexpected error <i>HTTP 409 - CONFLICT</i> if the question set was found was not found in the system <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/findMultipleChoiceSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findMultipleChoiceSet(@RequestBody MultipleChoiceSet multipleChoiceSet) {

        MultipleChoiceSet foundMultipleChoiceSet = null;
        try {
            foundMultipleChoiceSet = questionSetsService.getMultipleChoiceSet(multipleChoiceSet);

        } catch (EmptyQuestionSetTitleOrTopic | UnexpectedError | NullQuestionSet e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
            questionSetAlreadyExists.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(questionSetAlreadyExists.getMessage());
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundMultipleChoiceSet);
    }
    /**
     * Finds all multiple choice sets of a user.
     * It is processed as a GET request requesting the <i>USERS username</i>

     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/getAllUsersMultipleChoiceSets/{username123}


     *
     * @param username the multiple choice set that should be found
     * @return <i>HTTP 200 - OK</i> with the found multiple choice set or <i>HTTP 400 - BAD_REQUEST</i>

     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getAllUsersMultipleChoiceSets/{username}", method = RequestMethod.GET)
    public ResponseEntity<Object> findAllMultipleChoiceSet(@PathVariable String username) {
      List<MultipleChoiceSet> usersMultipleChoiceSet;
        try {
            usersMultipleChoiceSet=questionSetsService.getUsersAllMultipleChoiceSet(username);
        } catch (EmptyQuestionSetTitleOrTopic|NullQuestionSet|UnexpectedError|UsersMultipleChoiceSetNotFound|UserNotFound e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(usersMultipleChoiceSet);
    }

    /**
     * Finds all written sets of a user.
     * It is processed as a GET request requesting the <i>Users username</i>

     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/getAllUsersWrittenSets/{username}
     * </p>
     *

     *
     * @param username the user's username
     * @return <i>HTTP 200 - OK</i> with the found written set or <i>HTTP 400 - BAD_REQUEST</i>

     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getAllUsersWrittenSets/{username}", method = RequestMethod.GET)
    public ResponseEntity<Object> findAllWrittenSets(@PathVariable String username) {
        List<WrittenSet> usersWrittenSet = null;

        try {
            usersWrittenSet=questionSetsService.getUsersAllWrittenSet(username);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (UsersWrittenSetNotFound|UserNotFound usersWrittenSetNotFound) {
            usersWrittenSetNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(usersWrittenSetNotFound.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(usersWrittenSet);
    }


}
