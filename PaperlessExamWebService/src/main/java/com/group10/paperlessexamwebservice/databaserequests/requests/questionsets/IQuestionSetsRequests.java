package com.group10.paperlessexamwebservice.databaserequests.requests.questionsets;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;

public interface IQuestionSetsRequests {
    MultipleChoiceSet getMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable;

    MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable;

}
