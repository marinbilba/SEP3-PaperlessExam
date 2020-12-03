package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.*;
import com.group10.paperlessexamwebservice.service.questionsets.IQuestionSetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing question sets, create, find, delete requests
 *
 * @author Marin Bilba
 * @version 1.0
 */
@RestController
@RequestMapping("/questionSets")
public class QuestionSetsController {

    @Autowired
    private IQuestionSetsService questionSetsService;


    /**
     * Multiple choice set validation. Method will validate whether Client is allowed to create a new multiple
     * choice set or not.
     * It is processed as POST request requesting <i>MultipleChoiceSet object</i>in format of JSON in the request
     * body.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/login
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
     * @return <i>HTTP 200 - OK</i> with boolean value true if validation was passed
     * or
     * <i>HTTP 400 - BAD_REQUEST</i> if the question set's title or topic are empty
     * <i>HTTP 409 - CONFLICT</i> if the question set was found was not found in the system
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/validateMultipleChoiceSet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateMultipleChoiceSet(@RequestBody MultipleChoiceSet multipleChoiceSet) {
        boolean temp = false;
        try {
            temp = questionSetsService.validateMultipleChoiceSet(multipleChoiceSet);

        } catch (EmptyQuestionSet emptyQuestionSet) {
            emptyQuestionSet.printStackTrace();
        } catch (EmptyQuestionSetTitleOrTopic e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
            questionSetAlreadyExists.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(questionSetAlreadyExists.getMessage());
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(temp);
    }

    /**
     * Multiple choice question validation. Method will validate whether Client is allowed
     * to create a new multiple choice question or not.
     * It is processed as a POST request requesting <i>MultipleChoiceQuestion object</i>
     * in format of JSON as an argument.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/login
     *
     * <b>BODY</b>:
     * {
     * "questionOptions": [
     * {
     * "isCorrectAnswer": true,
     * "answer": "da"
     * },
     * {
     * "isCorrectAnswer": true,
     * "answer": "da"
     * }
     * ],
     * "questionNumber": 1,
     * "question": "What is?",
     * "score": 20.0
     * }
     *
     * @param multipleChoiceQuestion the multiple choice question
     * @return <i>HTTP 200 - OK</i> with boolean value true if validation was passed
     * or
     * <i>HTTP 400 - BAD_REQUEST</i> if the question set's title or topic are empty
     */
    @RequestMapping(value = "/validateMultipleChoiceQuestion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateMultipleChoiceSet(@RequestBody MultipleChoiceQuestion multipleChoiceQuestion) {
        boolean temp = false;
        try {
            temp = questionSetsService.validateMultipleChoiceSetQuestion(multipleChoiceQuestion);
        } catch (MultipleChoiceQuestionOptionError | EmptyQuestionSetQuestions e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(temp);
    }
}
