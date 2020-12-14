package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.model.studentsubmitpaper.StudentSubmitExaminationPaper;
import com.group10.paperlessexamwebservice.service.examinaationevents.IExaminationEventService;
import com.group10.paperlessexamwebservice.service.exceptions.examinationevent.ExaminationEventException;
import com.group10.paperlessexamwebservice.service.exceptions.other.NegativeNumberException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.EmptyQuestionSetTitleOrTopic;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.NullQuestionSet;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.QuestionSetAlreadyExists;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.EmptyMultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.NullQuestionSetQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.submitpaper.SubmitExaminationPaperException;
import com.group10.paperlessexamwebservice.service.exceptions.user.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for creating/find/delete of examination event
 *
 * @author Marin Bilba
 * @version 1.0
 */
@RestController
@RequestMapping("/examinationevent")
public class ExaminationEventController {
    @Autowired
    private IExaminationEventService examinationEventService;

    /**
     * Create the examination event .It is processed as a POST request requesting <i>ExaminationEvent object</i>
     * in format of JSON as an argument.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/examinationevent/createExaminationEvent
     * </p>
     *
     * <b>BODY</b>:
     * {
     * "examTitle":"Eomana",
     * "writtenSets":[
     * <p>
     * ],
     * "multipleChoiceSets":[
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
     * "id":1,
     * "title":"asd",
     * "topic":"asdasd"
     * }
     * ],
     * "usersAssigned":[
     * {
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
     * }
     * ],
     * "examDateAndTime":"2020-12-18T11:00:00",
     * "createdBy":{
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
     * }
     * }
     *
     * @param examinationEvent the multiple choice question
     * @return <i>HTTP 200 - OK</i> with the created multiple choice set question
     * <i>HTTP 400 - BAD_REQUEST</i>
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/createExaminationEvent", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createExaminationEvent(@RequestBody ExaminationEvent examinationEvent) {
        ExaminationEvent createdExaminationEvent = null;
        try {
            createdExaminationEvent = examinationEventService.createExaminationEvent(examinationEvent);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(createdExaminationEvent);
    }


    /**
     * Gets teachers upcoming examination events. It is processed as a GET request requesting <i>teacher id</i>
     * passed through the URI
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/examinationevent/getTeachersUpcomingExamEvents/32
     * </p>
     *
     * @param teacherId the teacher id
     * @return the teachers examination events
     * <i>HTTP 400 - BAD_REQUEST</i> if no scheduled examination events were found
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getTeachersUpcomingExamEvents/{teacherId}", method = RequestMethod.GET)
    public ResponseEntity<Object> GetTeachersUpcomingExamEvents(@PathVariable String teacherId) {
        List<ExaminationEvent> fetchedExaminationEvents = null;
        try {
            fetchedExaminationEvents = examinationEventService.getTeachersUpcomingExamEvents(teacherId);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (ExaminationEventException e) {
            e.printStackTrace();
            System.out.println(fetchedExaminationEvents.get(0).getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(fetchedExaminationEvents);
    }
    /**
     * Gets teachers passed examination events. It is processed as a GET request requesting <i>teacher id</i>
     * passed through the URI
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/examinationevent/getTeachersPassedExamEvents/21
     * </p>
     *
     * @param teacherId the teacher id
     * @return the teachers examination events
     * <i>HTTP 400 - BAD_REQUEST</i> if no scheduled examination events were found
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getTeachersPassedExamEvents/{teacherId}", method = RequestMethod.GET)
    public ResponseEntity<Object> GetTeachersPassedExamEvents(@PathVariable String teacherId) {
        List<ExaminationEvent> fetchedExaminationEvents = null;
        try {
            fetchedExaminationEvents = examinationEventService.getTeachersPassedExamEvents(teacherId);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (ExaminationEventException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(fetchedExaminationEvents);
    }

    /**
     * Gets students upcoming examination events. It is processed as a GET request requesting <i>student id</i>
     * passed through the URI
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/examinationevent/getStudentsUpcomingExamEvents/32
     * </p>
     *
     * @param studentId the teacher id
     * @return the teachers examination events
     * <i>HTTP 400 - BAD_REQUEST</i> if no scheduled examination events were found
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getStudentsUpcomingExamEvents/{studentId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getStudentsUpcomingExamEvents(@PathVariable String studentId) {
        List<ExaminationEvent> fetchedExaminationEvents = null;
        try {
            fetchedExaminationEvents = examinationEventService.getStudentsUpcomingExamEvents(studentId);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (ExaminationEventException e) {
            e.printStackTrace();
            System.out.println(fetchedExaminationEvents.get(0).getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(fetchedExaminationEvents);
    }

    /**
     * Gets students passed examination events. It is processed as a GET request requesting <i>student id</i>
     * passed through the URI
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/examinationevent/getStudentsPassedExamEvents
     * </p>
     *
     * @param studentId the teacher id
     * @return the teachers examination events
     * <i>HTTP 400 - BAD_REQUEST</i> if no scheduled examination events were found
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getStudentsPassedExamEvents/{studentId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getStudentsPassedExamEvents(@PathVariable String studentId) {
        List<ExaminationEvent> fetchedExaminationEvents = null;
        try {
            fetchedExaminationEvents = examinationEventService.getStudentsPassedExamEvents(studentId);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (ExaminationEventException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(fetchedExaminationEvents);
    }
    /**
     * Gets students ongoing examination events. It is processed as a GET request requesting <i>student id</i>
     * passed through the URI
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/examinationevent/getStudentsOngoingExamEvents/2
     * </p>
     *
     * @param studentId the teacher id
     * @return the teachers examination events
     * <i>HTTP 400 - BAD_REQUEST</i> if no scheduled examination events were found
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getStudentsOngoingExamEvents/{studentId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getStudentsOngoingExamEvents(@PathVariable String studentId) {
        List<ExaminationEvent> fetchedExaminationEvents = null;
        try {
            fetchedExaminationEvents = examinationEventService.getStudentsOngoingExamEvents(studentId);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (ExaminationEventException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(fetchedExaminationEvents);
    }

    /**
     * Gets examination event with all child components ongoing examination events.
     * It is processed as a GET request requesting <i>exam id</i>
     * passed through the URI
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/examinationevent/getExaminationPaper/2
     * </p>
     *
     * @param examinationEventId the exam event id
     * @return  examination events
     *
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/getExaminationPaper/{examinationEventId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExaminationPaper(@PathVariable String examinationEventId) {
        ExaminationEvent fetchedExaminationEventPaper = null;
        try {
            fetchedExaminationEventPaper = examinationEventService.getExaminationPaper(examinationEventId);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        catch (ExaminationEventException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }

        return ResponseEntity.status(HttpStatus.OK).body(fetchedExaminationEventPaper);
    }
    /**
     * Submit the  examination "paper" .It is processed as a POST request requesting <i>StudentSubmitExaminationPaper object</i>
     * in format of JSON as an argument.
     * <p>
     * <b>EXAMPLE</b>:
     * <p>
     * http://{host}:8080/examinationevent/createExaminationEvent
     * </p>
     *
     * <b>BODY</b>:

     *
     * @param paperToSubmit the multiple choice question
     * @return <i>HTTP 200 - OK</i> with the created multiple choice set question
     * <i>HTTP 400 - BAD_REQUEST</i>
     * <i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
     */
    @RequestMapping(value = "/submitStudentExaminationPaper", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> submitStudentExaminationPaper(@RequestBody StudentSubmitExaminationPaper paperToSubmit) {
        StudentSubmitExaminationPaper submittedPaper = null;
        try {
            submittedPaper = examinationEventService.submitStudentExaminationPaper(paperToSubmit);
        } catch (ServiceNotAvailable serviceNotAvailable) {
            serviceNotAvailable.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailable.getMessage());
        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
            nullQuestionSetQuestion.printStackTrace();
        } catch (NullQuestionSet nullQuestionSet) {
            nullQuestionSet.printStackTrace();
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
            emptyQuestionSetTitleOrTopic.printStackTrace();
        } catch (SubmitExaminationPaperException e) {
            e.printStackTrace();
        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
            questionSetAlreadyExists.printStackTrace();
        } catch (UnexpectedError unexpectedError) {
            unexpectedError.printStackTrace();
        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
            emptyMultipleChoiceQuestion.printStackTrace();
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(submittedPaper);
    }
}
