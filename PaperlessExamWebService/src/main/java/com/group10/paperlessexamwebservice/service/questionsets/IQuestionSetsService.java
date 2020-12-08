package com.group10.paperlessexamwebservice.service.questionsets;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.*;
import com.group10.paperlessexamwebservice.service.exceptions.user.UserNotFound;

public interface IQuestionSetsService {
    MultipleChoiceQuestion addMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws EmptyQuestionSetQuestions, MultipleChoiceQuestionOptionError;

    MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, UnexpectedError, UserNotFound, EmptyQuestionSetTitleOrTopic, NullQuestionSet, QuestionSetAlreadyExists;

    MultipleChoiceSet getMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, NullQuestionSet, QuestionSetAlreadyExists, EmptyQuestionSetTitleOrTopic, UnexpectedError;
}
