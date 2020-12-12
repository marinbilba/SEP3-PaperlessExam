package com.group10.paperlessexamwebservice.service.examinaationevents;

import com.group10.paperlessexamwebservice.databaserequests.requests.examinationevent.IExaminationEventRequest;
import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.service.exceptions.examinationevent.ExaminationEventException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExaminationEventService implements IExaminationEventService {
    @Autowired
    private IExaminationEventRequest examinationEventRequest;
//todo check if only students were assigned
    @Override
    public ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable {
        return examinationEventRequest.createExaminationEvent(examinationEvent);
    }

    @Override
    public List<ExaminationEvent> getTeachersUpcomingExamEvents(String teacherId) throws ServiceNotAvailable, ExaminationEventException {
        List<ExaminationEvent> fetchedExaminationEvents = examinationEventRequest.getTeachersExamEvents(teacherId);
        if (fetchedExaminationEvents == null) {
            throw new ExaminationEventException("This user does not have any scheduled examination events");
        }
        List<ExaminationEvent> upcomingEvents = showOnlyUpcomingExaminationEvents(fetchedExaminationEvents);
        if(upcomingEvents.isEmpty()){
            throw new ExaminationEventException("This user does not have any scheduled upcoming examination events");

        }
        return upcomingEvents;
    }

    @Override
    public List<ExaminationEvent> getTeachersPassedExamEvents(String teacherId) throws ExaminationEventException, ServiceNotAvailable {
        List<ExaminationEvent> fetchedExaminationEvents = examinationEventRequest.getTeachersExamEvents(teacherId);
        if (fetchedExaminationEvents == null) {
            throw new ExaminationEventException("This user does not have any scheduled examination events");
        }
        List<ExaminationEvent> passedEvents = showOnlyPassedExaminationEvents(fetchedExaminationEvents);
        if(passedEvents.isEmpty()){
            throw new ExaminationEventException("This user does not have any scheduled upcoming examination events");

        }
        return passedEvents;
    }

    private List<ExaminationEvent> showOnlyPassedExaminationEvents(List<ExaminationEvent> fetchedExaminationEvents) {
        List<ExaminationEvent> passedEvents=new ArrayList<>();
        for (var exam : fetchedExaminationEvents) {
            if(exam.getExamDateAndTime().before(new Date(System.currentTimeMillis()))){
                passedEvents.add(exam);
            }
        }
        return passedEvents;
    }

    private List<ExaminationEvent> showOnlyUpcomingExaminationEvents(List<ExaminationEvent> fetchedExaminationEvents) {
        List<ExaminationEvent> upcomingEvents=new ArrayList<>();
        for (var exam : fetchedExaminationEvents) {
            if(exam.getExamDateAndTime().after(new Date(System.currentTimeMillis()))){
                upcomingEvents.add(exam);
            }
        }
        return upcomingEvents;
    }
}
