package com.group10.paperlessexamwebservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.QuestionOption;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.NegativeNumberException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.*;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.EmptyMultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.MultipleChoiceQuestionOptionError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.NullQuestionSetQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.user.UserNotFound;
import com.group10.paperlessexamwebservice.service.questionsets.IQuestionSetsService;
import com.group10.paperlessexamwebservice.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

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
     * Multiple choice set validation. Method will validate whether Client is allowed to create a new multiple
     * choice set or not.
     * It is processed as POST request requesting <i>MultipleChoiceSet object</i>in format of JSON in the request
     * body.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/validateMultipleChoiceSet
     *
     * <b>BODY</b>:
     * {
     * "title": "123",
     * "topic": "Java"
     * }
     *
     * </p>
     *
     * @param multipleChoiceSet the multiple choice set
     * @return <i>HTTP 200 - OK</i> with boolean value true if validation was passed or <i>HTTP 400 - BAD_REQUEST</i> if the question set's title or topic are empty <i>HTTP 409 - CONFLICT</i> if the question set was found was not found in the system <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/validateMultipleChoiceSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateMultipleChoiceSet(@RequestBody MultipleChoiceSet multipleChoiceSet) {
        return null;
//        //        boolean temp = false;
//        try {
//        //    temp = questionSetsService.validateMultipleChoiceSet(multipleChoiceSet);
//
//        } catch (NullQuestionSet emptyQuestionSet) {
//            emptyQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(questionSetAlreadyExists.getMessage());
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(temp);
    }

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
     *
     * @param multipleChoiceQuestionOption the multiple choice question option
     * @return <i>HTTP 200 - OK</i> with the created multiple choice set question or
     * <i>HTTP 400 - BAD_REQUEST</i>
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/addMultipleChoiceQuestionOption", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addMultipleChoiceQuestionOption(@RequestBody QuestionOption multipleChoiceQuestionOption) {
        QuestionOption createdQuestionOption = null;
        try {
            createdQuestionOption = questionSetsService.addMultipleChoiceQuestionOption(multipleChoiceQuestionOption);
        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
            emptyMultipleChoiceQuestion.printStackTrace();
        } catch (NullQuestionSet | UnexpectedError | NullQuestionSetQuestion | NegativeNumberException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ServiceNotAvailable e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.OK).body(createdQuestionOption);
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = null;
        try {
            user = userController.getUsersByUsername("silvmandrila");
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
        }

        MultipleChoiceSet ms = null;
        try {
            ms = questionSetsService.getMultipleChoiceSet(new MultipleChoiceSet("Java", "Core", user));
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
        } catch (NullQuestionSet nullQuestionSet) {
            nullQuestionSet.printStackTrace();
        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
            questionSetAlreadyExists.printStackTrace();
        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
            emptyQuestionSetTitleOrTopic.printStackTrace();
        } catch (UnexpectedError unexpectedError) {
            unexpectedError.printStackTrace();
        }

        MultipleChoiceQuestion mq = new MultipleChoiceQuestion("What is this", 30, 1, ms);
        QuestionOption questionOption =new QuestionOption(true,"Yes",mq);
        try {
            questionSetsService.addMultipleChoiceQuestionOption(questionOption);
        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
            emptyMultipleChoiceQuestion.printStackTrace();
        } catch (NullQuestionSet nullQuestionSet) {
            nullQuestionSet.printStackTrace();
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
            nullQuestionSetQuestion.printStackTrace();
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        } catch (UnexpectedError unexpectedError) {
            unexpectedError.printStackTrace();
        }


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
     * @return <i>HTTP 200 - OK</i> with the created multiple choice set or <i>HTTP 400 - BAD_REQUEST</i> if the set already exists,title or topic are empty or unexpected errors are detected. <i>HTTP 401 - UNAUTHORIZED</i> if the request does not contain the user object <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
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


}
