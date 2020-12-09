package com.group10.paperlessexamwebservice.service.examinaationevents;

import com.group10.paperlessexamwebservice.databaserequests.requests.examinationevent.IExaminationEventRequest;
import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExaminationEventService implements IExaminationEventService {
    @Autowired
    private IExaminationEventRequest examinationEventRequest;

    @Override
    public ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable {
        return examinationEventRequest.createExaminationEvent(examinationEvent);
    }
}
