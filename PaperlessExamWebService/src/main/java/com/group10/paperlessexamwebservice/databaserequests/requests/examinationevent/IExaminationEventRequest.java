package com.group10.paperlessexamwebservice.databaserequests.requests.examinationevent;

import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;

import java.util.List;

public interface IExaminationEventRequest {
    ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable;

    List<ExaminationEvent> getTeachersExamEvents(String teacherId) throws ServiceNotAvailable;

    List<ExaminationEvent> getStudentsExamEvents(String studentId) throws ServiceNotAvailable;
}
