package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenQuestion;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
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

    /**
     * The User controller.
     */
    @Autowired
    IUserService userController;
    @Autowired
    private IQuestionSetsService questionSetsService;

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
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (NegativeNumberException | EmptyMultipleChoiceQuestion | UnexpectedError | QuestionSetAlreadyExists | NullQuestionSet | EmptyQuestionSetTitleOrTopic | NullQuestionSetQuestion e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userNotFound.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(createdMultipleChoiceSet);
    }

    /**
     * Update existing multiple choice set.It is processed as a POST request requesting <i>MultipleChoiceSet object</i>
     * in format of JSON as an argument.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/updateMultipleChoiceSet
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
     * @param multipleChoiceSet the multiple choice set to update
     * @return <i>HTTP 200 - OK</i> with the created multiple choice set or <i>HTTP 400 - BAD_REQUEST</i> if the set already exists,title or topic are empty or unexpected errors are detected. <i>HTTP 401 - UNAUTHORIZED</i> if the request does not contain the user object <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/updateMultipleChoiceSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateMultipleChoiceSet(@RequestBody MultipleChoiceSet multipleChoiceSet) {

        MultipleChoiceSet updatedMultipleChoiceSet = null;
        try {
            updatedMultipleChoiceSet = questionSetsService.updateMultipleChoiceSet(multipleChoiceSet);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (NegativeNumberException | EmptyMultipleChoiceQuestion | UnexpectedError | QuestionSetAlreadyExists | NullQuestionSet | EmptyQuestionSetTitleOrTopic | NullQuestionSetQuestion e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userNotFound.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedMultipleChoiceSet);
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
     * "user":{
     * "id":10,
     * "username":"silvmandrila",
     * "email":"silvmandr@va.cs",
     * "password":"111111",
     * "confirmPassword":null,
     * "firstName":"Silvestru",
     * "lastName":"Mandrila",
     * "role":{
     * "id":1,
     * "name":"Student"
     * }
     * },
     * "updatedTimestamp":"2020-12-13T00:26:52+01:00",
     * "writtenQuestions":[
     * {
     * "questionNumber":1,
     * "question":"zzzzzzzz",
     * "score":1221
     * },
     * {
     * "questionNumber":2,
     * "questionNumber":2,
     * "question":"asdads",
     * "score":2
     * }
     * ],
     * "id":8,
     * "title":"SADAS",
     * "topic":"DASSA"
     * }
     *
     * @param writtenSet the written set
     * @return the response entity <i>HTTP 200 - OK</i> with the written set or <i>HTTP 400 - BAD_REQUEST</i> if the set already exists,title or topic are empty or unexpected errors are detected. <i>HTTP 401 - UNAUTHORIZED</i> if the request does not contain the user object <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/createWrittenSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createWrittenSet(@RequestBody WrittenSet writtenSet) {
        WrittenSet createdWrittenSet = null;
        try {
            writtenSet.setId(null);
            createdWrittenSet = questionSetsService.createWrittenSet(writtenSet);

        } catch (NullQuestionSet | EmptyQuestionSetTitleOrTopic | UnexpectedError | QuestionSetAlreadyExists | EmptyMultipleChoiceQuestion e) {
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
     * Update written set response entity.
     * <p>
     *
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/updateWrittenSet
     * </p>
     *
     * <b>BODY</b>:
     * {
     * "user":{
     * "id":10,
     * "username":"silvmandrila",
     * "email":"silvmandr@va.cs",
     * "password":"111111",
     * "confirmPassword":null,
     * "firstName":"Silvestru",
     * "lastName":"Mandrila",
     * "role":{
     * "id":1,
     * "name":"Student"
     * }
     * },
     * "updatedTimestamp":"2020-12-13T00:26:52+01:00",
     * "writtenQuestions":[
     * {
     * "questionNumber":1,
     * "question":"zzzzzzzz",
     * "score":1221
     * },
     * {
     * "questionNumber":2,
     * "question":"asdads",
     * "score":2
     * }
     * ],
     * "id":8,
     * "title":"SADAS",
     * "topic":"DASSA"
     * }
     *
     * @param writtenSet the written set
     * @return the response entity <i>HTTP 200 - OK</i> with the written set or <i>HTTP 400 - BAD_REQUEST</i> if the set already exists,title or topic are empty or unexpected errors are detected. <i>HTTP 401 - UNAUTHORIZED</i> if the request does not contain the user object <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/updateWrittenSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateWrittenSet(@RequestBody WrittenSet writtenSet) {
        WrittenSet createdWrittenSet = null;
        try {
            createdWrittenSet = questionSetsService.updateWrittenSet(writtenSet);
        } catch (NullQuestionSet | EmptyQuestionSetTitleOrTopic | UnexpectedError | QuestionSetAlreadyExists | EmptyMultipleChoiceQuestion e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
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
     *
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/getAllUsersMultipleChoiceSets/{username123}
     *
     * @param username the multiple choice set that should be found
     * @return <i>HTTP 200 - OK</i> with the found multiple choice set or <i>HTTP 400 - BAD_REQUEST</i> <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getAllUsersMultipleChoiceSets/{username}", method = RequestMethod.GET)
    public ResponseEntity<Object> findAllMultipleChoiceSet(@PathVariable String username) {
        List<MultipleChoiceSet> usersMultipleChoiceSet;
        try {
            usersMultipleChoiceSet = questionSetsService.getUsersAllMultipleChoiceSet(username);
        } catch (EmptyQuestionSetTitleOrTopic | NullQuestionSet | UnexpectedError | UsersMultipleChoiceSetNotFound | UserNotFound e) {
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
     *
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/getAllUsersWrittenSets/{username}
     * </p>
     *
     * @param username the user's username
     * @return <i>HTTP 200 - OK</i> with the found written set or <i>HTTP 400 - BAD_REQUEST</i> <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getAllUsersWrittenSets/{username}", method = RequestMethod.GET)
    public ResponseEntity<Object> findAllWrittenSets(@PathVariable String username) {
        List<WrittenSet> usersWrittenSet = null;

        try {
            usersWrittenSet = questionSetsService.getUsersAllWrittenSet(username);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (UsersWrittenSetNotFound | UserNotFound usersWrittenSetNotFound) {
            usersWrittenSetNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(usersWrittenSetNotFound.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(usersWrittenSet);
    }

    /**
     * Deletes the passed multiple choice set of a user.
     * It is processed as a POST request requesting the <i>Written Set object to delete</i>
     *
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/deleteMultipleChoiceSet
     * </p>
     *
     * <b>BODY</b>:
     * {
     * "user":{
     * "id":10,
     * "username":"silvmandrila",
     * "email":"silvmandr@va.cs",
     * "password":"111111",
     * "confirmPassword":null,
     * "firstName":"Silvestru",
     * "lastName":"Mandrila",
     * "role":{
     * "id":1,
     * "name":"Student"
     * }
     * },
     * "updatedtimestamp":"0001-01-01T00:00:00",
     * "id":2,
     * "title":"fddddddd",
     * "topic":"dffd"
     * }
     *
     * @param multipleChoiceSetToDelete the multiple choice set to delete
     * @return <i>HTTP 200 - OK</i> with the deleted multiple choice set * * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/deleteMultipleChoiceSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteMultipleChoiceSet(@RequestBody MultipleChoiceSet multipleChoiceSetToDelete) {
        MultipleChoiceSet deletedMultipleChoiceSet;
        try {
            deletedMultipleChoiceSet = questionSetsService.deleteMultipleChoiceSet(multipleChoiceSetToDelete);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(deletedMultipleChoiceSet);
    }

    /**
     * Deletes the passed written sets of a user.
     * It is processed as a POST request requesting the <i>Written Set object to delete</i>
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/deleteWrittenSet
     * </p>
     *
     * <b>BODY</b>:
     * {
     * "user":{
     * "id":10,
     * "username":"silvmandrila",
     * "email":"silvmandr@va.cs",
     * "password":"111111",
     * "confirmPassword":null,
     * "firstName":"Silvestru",
     * "lastName":"Mandrila",
     * "role":{
     * "id":1,
     * "name":"Student"
     * }
     * },
     * "updatedTimestamp":"2020-12-12T21:12:54+01:00",
     * "id":2,
     * "title":"War",
     * "topic":"WW2"
     * }
     *
     * @param writtenSetToDelete the written set to delete
     * @return <i>HTTP 200 - OK</i> with the deleted written set * * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/deleteWrittenSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteWrittenSet(@RequestBody WrittenSet writtenSetToDelete) {
        WrittenSet deletedWrittenSet;
        try {
            deletedWrittenSet = questionSetsService.deleteWrittenSet(writtenSetToDelete);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(deletedWrittenSet);
    }

    /**
     * Deletes the passed multiple choice question sets of a user.
     * It is processed as a POST request requesting the <i>MultipleChoiceQuestion object to delete</i>
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/deleteMultipleChoiceQuestion
     * </p>
     *
     * <b>BODY</b>:
     *
     * @param multipleChoiceQuestionToDelete the multiple choice question to delete
     * @return <i>HTTP 200 - OK</i> with the deleted multiple Choice Question * * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/deleteMultipleChoiceQuestion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestion multipleChoiceQuestionToDelete) {
        MultipleChoiceQuestion deletedMultipleChoiceQuestion;
        try {
            deletedMultipleChoiceQuestion = questionSetsService.deleteMultipleChoiceQuestion(multipleChoiceQuestionToDelete);
        } catch (ServiceNotAvailable | UnexpectedError serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(deletedMultipleChoiceQuestion);
    }

    /**
     * Deletes the written set question of a user.
     * It is processed as a POST request requesting the <i>WrittenQuestion object to delete</i>
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/deleteWrittenSetQuestion
     * </p>
     *
     * <b>BODY</b>:
     *
     * @param writtenQuestionToDelete the written question to delete
     * @return <i>HTTP 200 - OK</i> with the deleted multiple Choice Question * * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/deleteWrittenSetQuestion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteWrittenSetQuestion(@RequestBody WrittenQuestion writtenQuestionToDelete) {
        WrittenQuestion deletedWrittenQuestion;
        try {
            deletedWrittenQuestion = questionSetsService.deleteWrittenQuestion(writtenQuestionToDelete);
        } catch (ServiceNotAvailable | UnexpectedError serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(deletedWrittenQuestion);
    }


    /**
     * Fetches the written set with all child elements.
     * It is processed as a GET request requesting the <i>written set id</i>
     *
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/getWrittenSetWithAllChildElements/{username123}
     *
     * @param writtenSetId the multiple choice set that should be found
     * @return <i>HTTP 200 - OK</i> with the found multiple choice set or <i>HTTP 400 - BAD_REQUEST</i><i>HTTP 400 - BAD_REQUEST</i> <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getWrittenSetWithAllChildElements/{writtenSetId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getWrittenSetWithAllChildElements(@PathVariable long writtenSetId) {
        System.out.println(writtenSetId);
        WrittenSet fetchedWrittenSet;
        try {
            fetchedWrittenSet = questionSetsService.getWrittenSetWithAllChildElements(writtenSetId);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (UnexpectedError unexpectedError) {
            unexpectedError.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(unexpectedError.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(fetchedWrittenSet);
    }

    /**
     * Fetches the written set with all child elements.
     * It is processed as a GET request requesting the <i>written set id</i>
     *
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/questionsets/getMultipleChoiceSetWithAllChildElements/{id}
     *
     * @param writtenSetId the multiple choice set that should be found
     * @return <i>HTTP 200 - OK</i> with the found multiple choice set or <i>HTTP 400 - BAD_REQUEST</i><i>HTTP 400 - BAD_REQUEST</i> <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getMultipleChoiceSetWithAllChildElements/{writtenSetId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getMultipleChoiceSetWithAllChildElements(@PathVariable long writtenSetId) {
        System.out.println(writtenSetId);
        MultipleChoiceSet fetchedMultipleChoiceSet;
        try {
            fetchedMultipleChoiceSet = questionSetsService.getMultipleChoiceSetWithAllChildElements(writtenSetId);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(fetchedMultipleChoiceSet);
    }
}
