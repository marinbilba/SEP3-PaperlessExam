package com.group10.paperlessexamwebservice.service.questionsets;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.*;

public interface IQuestionSetsService {
    boolean validateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws EmptyQuestionSet, EmptyQuestionSetTitleOrTopic, QuestionSetAlreadyExists, ServiceNotAvailable;

    boolean validateMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws EmptyQuestionSetQuestions, MultipleChoiceQuestionOptionError;

    MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, UnexpectedError;
}
