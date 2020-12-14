package com.group10.paperlessexamwebservice.service.examinaationevents;

import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.service.exceptions.examinationevent.ExaminationEventException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;

import java.util.List;

public interface IExaminationEventService {
    ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable;

    List<ExaminationEvent> getTeachersUpcomingExamEvents(String teacherId) throws ServiceNotAvailable, ExaminationEventException;

    List<ExaminationEvent> getTeachersPassedExamEvents(String teacherId) throws ExaminationEventException, ServiceNotAvailable;

    List<ExaminationEvent> getStudentsUpcomingExamEvents(String studentId) throws ExaminationEventException, ServiceNotAvailable;

    List<ExaminationEvent> getStudentsPassedExamEvents(String studentId) throws ExaminationEventException, ServiceNotAvailable;

    List<ExaminationEvent> getStudentsOngoingExamEvents(String studentId) throws ExaminationEventException, ServiceNotAvailable;

    ExaminationEvent getExaminationPaper(String examinationEventId) throws Exception;
}
