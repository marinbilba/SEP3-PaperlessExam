package com.group10.paperlessexamwebservice.service.questionsets;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.QuestionOption;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenQuestion;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
import com.group10.paperlessexamwebservice.service.exceptions.other.NegativeNumberException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.*;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.EmptyMultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.NullQuestionSetQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.user.UserNotFound;

import java.util.List;

public interface IQuestionSetsService {
    MultipleChoiceQuestion addMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws NullQuestionSet, NegativeNumberException, EmptyMultipleChoiceQuestion, ServiceNotAvailable, EmptyQuestionSetTitleOrTopic, UnexpectedError, QuestionSetAlreadyExists;

    MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, UnexpectedError, UserNotFound, EmptyQuestionSetTitleOrTopic, NullQuestionSet, QuestionSetAlreadyExists, NullQuestionSetQuestion, NegativeNumberException, EmptyMultipleChoiceQuestion;

    MultipleChoiceSet getMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, NullQuestionSet, QuestionSetAlreadyExists, EmptyQuestionSetTitleOrTopic, UnexpectedError;

    MultipleChoiceQuestion getMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceSetQuestion) throws NullQuestionSet, NegativeNumberException, EmptyMultipleChoiceQuestion, ServiceNotAvailable, NullQuestionSetQuestion, EmptyQuestionSetTitleOrTopic, UnexpectedError, QuestionSetAlreadyExists;

    QuestionOption addMultipleChoiceQuestionOption(QuestionOption multipleChoiceQuestionOption) throws EmptyMultipleChoiceQuestion, NullQuestionSet, ServiceNotAvailable, NullQuestionSetQuestion, NegativeNumberException, UnexpectedError, EmptyQuestionSetTitleOrTopic, QuestionSetAlreadyExists;

    QuestionOption getMultipleChoiceSetQuestionOption(QuestionOption multipleChoiceQuestionOption) throws EmptyMultipleChoiceQuestion, NullQuestionSet, UnexpectedError, NullQuestionSetQuestion, NegativeNumberException, EmptyQuestionSetTitleOrTopic, ServiceNotAvailable;

    WrittenSet createWrittenSet(WrittenSet writtenSet) throws NullQuestionSet, EmptyQuestionSetTitleOrTopic, ServiceNotAvailable, UserNotFound, UnexpectedError, QuestionSetAlreadyExists, EmptyMultipleChoiceQuestion;

    WrittenSet getWrittenSet(WrittenSet writtenSet) throws NullQuestionSet, EmptyQuestionSetTitleOrTopic, ServiceNotAvailable, UnexpectedError;

    WrittenQuestion addWrittenQuestion(WrittenQuestion writtenQuestion) throws EmptyMultipleChoiceQuestion, NullQuestionSet, EmptyQuestionSetTitleOrTopic, UnexpectedError, ServiceNotAvailable, QuestionSetAlreadyExists;

    WrittenQuestion getWrittenQuestion(WrittenQuestion writtenQuestion) throws EmptyMultipleChoiceQuestion, NullQuestionSet, EmptyQuestionSetTitleOrTopic, UnexpectedError, ServiceNotAvailable, NullQuestionSetQuestion;

    List<MultipleChoiceSet> getUsersAllMultipleChoiceSet(String username) throws EmptyQuestionSetTitleOrTopic, NullQuestionSet, ServiceNotAvailable, UnexpectedError, UsersMultipleChoiceSetNotFound, UserNotFound;

    List<WrittenSet> getUsersAllWrittenSet(String username) throws ServiceNotAvailable, UsersWrittenSetNotFound, UserNotFound;

    WrittenSet deleteWrittenSet(WrittenSet writtenSetToDelete) throws ServiceNotAvailable;

    MultipleChoiceSet deleteMultipleChoiceSet(MultipleChoiceSet multipleChoiceSetToDelete) throws ServiceNotAvailable;

    MultipleChoiceQuestion deleteMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestionToDelete) throws UnexpectedError, ServiceNotAvailable;

    WrittenQuestion deleteWrittenQuestion(WrittenQuestion writtenQuestionToDelete) throws UnexpectedError, ServiceNotAvailable;

    WrittenSet getWrittenSetWithAllChildElements(long writtenSetId) throws ServiceNotAvailable, UnexpectedError;

    WrittenSet updateWrittenSet(WrittenSet writtenSet) throws NullQuestionSet, UnexpectedError, ServiceNotAvailable, EmptyQuestionSetTitleOrTopic, EmptyMultipleChoiceQuestion, QuestionSetAlreadyExists;

    MultipleChoiceSet updateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, NullQuestionSet, EmptyQuestionSetTitleOrTopic, UnexpectedError, NegativeNumberException, NullQuestionSetQuestion, UserNotFound, QuestionSetAlreadyExists, EmptyMultipleChoiceQuestion;

    MultipleChoiceSet getMultipleChoiceSetWithAllChildElements(long writtenSetId) throws ServiceNotAvailable;
}
