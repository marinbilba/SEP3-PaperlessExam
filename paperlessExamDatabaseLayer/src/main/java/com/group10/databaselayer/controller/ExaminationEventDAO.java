package com.group10.databaselayer.controller;

import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.repositories.examinationevent.IExaminationEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExaminationEventDAO {
    @Autowired
    IExaminationEventRepository examinationEventRepository;

    public ExaminationEvent createUpdate(ExaminationEvent examinationEvent) {
     return examinationEventRepository.save(examinationEvent);

    }
}
