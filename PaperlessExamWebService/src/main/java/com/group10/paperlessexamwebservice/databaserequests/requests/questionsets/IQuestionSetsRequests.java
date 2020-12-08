package com.group10.paperlessexamwebservice.databaserequests.requests.questionsets;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.QuestionOption;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;

public interface IQuestionSetsRequests {
    MultipleChoiceSet getMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable;

    MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable;

    MultipleChoiceQuestion createMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws ServiceNotAvailable;

    MultipleChoiceQuestion getMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceSetQuestion) throws ServiceNotAvailable;

    QuestionOption createMultipleChoiceSetQuestionOption(QuestionOption multipleChoiceQuestionOption) throws ServiceNotAvailable;

}
