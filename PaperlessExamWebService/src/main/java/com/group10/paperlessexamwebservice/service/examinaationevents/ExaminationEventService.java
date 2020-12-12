package com.group10.paperlessexamwebservice.service.examinaationevents;

import com.group10.paperlessexamwebservice.databaserequests.requests.examinationevent.IExaminationEventRequest;
import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.service.exceptions.examinationevent.ExaminationEventException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<ExaminationEvent> getTeachersExaminationEvents(String teacherId) throws ServiceNotAvailable, ExaminationEventException {
        List<ExaminationEvent> fetchedExaminationEvents= examinationEventRequest.getTeachersExaminationEvents(teacherId);
    if(fetchedExaminationEvents==null){
        throw new ExaminationEventException("This user does not have any scheduled examination events");
    }
    return fetchedExaminationEvents;
    }
}
