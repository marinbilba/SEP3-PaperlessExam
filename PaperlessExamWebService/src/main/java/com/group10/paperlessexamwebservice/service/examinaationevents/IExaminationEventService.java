package com.group10.paperlessexamwebservice.service.examinaationevents;

import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.service.exceptions.examinationevent.ExaminationEventException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;

import java.util.List;

public interface IExaminationEventService {
    ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable;

    List<ExaminationEvent> getTeachersExaminationEvents(String teacherId) throws ServiceNotAvailable, ExaminationEventException;
}
