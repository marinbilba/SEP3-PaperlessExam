package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.service.examinaationevents.IExaminationEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for creating/find/delete of examination event
 *
 * @author Marin Bilba
 * @version 1.1
 */
@RestController
@RequestMapping("/examinationevent")
public class ExaminationEventController {
    @Autowired
    private IExaminationEventService examinationEventService;
}
