package com.group10.paperlessexamwebservice.service.examinaationevents;

import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;

public interface IExaminationEventService {
    ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable;

}
