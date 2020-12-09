package com.group10.paperlessexamwebservice.service.examinaationevents;

import com.group10.paperlessexamwebservice.databaserequests.requests.questionsets.IQuestionSetsRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExaminationEventService implements IExaminationEventService {
    @Autowired
    private IQuestionSetsRequests questionSetsRequests;

}
