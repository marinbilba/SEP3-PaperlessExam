package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.databaserequests.networkcontainer.NetworkContainer;
import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.examinaationevents.IExaminationEventService;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.group10.paperlessexamwebservice.databaserequests.networkcontainer.RequestOperation.GET_USER_BY_USERNAME;

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
     *
     * @param examinationEvent the multiple choice question
     * @return <i>HTTP 200 - OK</i> with the created multiple choice set question or
     * <i>HTTP 400 - BAD_REQUEST</i>
     *<i>HTTP 503 - SERVICE_UNAVAILABLE</i> code if there are connection problems with the third tier
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
}
