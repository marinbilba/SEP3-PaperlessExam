package com.group10.paperlessexamwebservice.databaserequests.requests.questionsets;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.QuestionOption;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenQuestion;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;

import java.util.List;

public interface IQuestionSetsRequests {
    MultipleChoiceSet getMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable;

    MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable;

    MultipleChoiceQuestion createMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws ServiceNotAvailable;

    MultipleChoiceQuestion getMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceSetQuestion) throws ServiceNotAvailable;

    QuestionOption createMultipleChoiceSetQuestionOption(QuestionOption multipleChoiceQuestionOption) throws ServiceNotAvailable;

    QuestionOption getMultipleChoiceSetQuestionOption(QuestionOption multipleChoiceQuestionOption) throws ServiceNotAvailable;

    WrittenSet createWrittenSet(WrittenSet writtenSet) throws ServiceNotAvailable;

    WrittenSet getWrittenSet(WrittenSet writtenSet) throws ServiceNotAvailable;

    WrittenQuestion createWrittenQuestion(WrittenQuestion writtenQuestion) throws ServiceNotAvailable;

    WrittenQuestion getWrittenQuestion(WrittenQuestion writtenQuestion) throws ServiceNotAvailable;
    List<MultipleChoiceSet> getUsersAllMultipleChoiceSet(User fetchedUser) throws ServiceNotAvailable;

    List<WrittenSet> getUsersAllWrittenSet(User fetchedUser) throws ServiceNotAvailable;

    WrittenSet deleteWrittenSet(WrittenSet writtenSetToDelete) throws ServiceNotAvailable;

    MultipleChoiceSet deleteMultipleChoiceSet(MultipleChoiceSet multipleChoiceSetToDelete) throws ServiceNotAvailable;

    MultipleChoiceQuestion deleteMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestionToDelete) throws ServiceNotAvailable;

    WrittenQuestion deleteWrittenSetQuestion(WrittenQuestion writtenQuestionToDelete) throws ServiceNotAvailable;

    WrittenSet getWrittenSetById(long writtenSetId) throws ServiceNotAvailable;

    List<WrittenQuestion> getWrittenSetQuestionsByWrittenSet(WrittenSet fetchedWrittenSet) throws ServiceNotAvailable;
}

