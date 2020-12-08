package com.group10.paperlessexamwebservice.service.questionsets;

import com.group10.paperlessexamwebservice.databaserequests.requests.questionsets.IQuestionSetsRequests;
import com.group10.paperlessexamwebservice.databaserequests.requests.user.IUserRequests;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.*;
import com.group10.paperlessexamwebservice.service.exceptions.user.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * The type Question sets service.
 */
@Service
public class QuestionSetsServiceImpl implements IQuestionSetsService {

    @Autowired
    private IQuestionSetsRequests questionSetsRequests;

    @Autowired
    private IUserRequests iUserRequests;


    private boolean validateMultipleChoiceSetFields(MultipleChoiceSet multipleChoiceSet) throws NullQuestionSet, EmptyQuestionSetTitleOrTopic, QuestionSetAlreadyExists, ServiceNotAvailable {
        // Validate multiple choice set on null. Check if title and  topic are not empty
        if (Objects.requireNonNull(multipleChoiceSet).getTopic()==null||Objects.requireNonNull(multipleChoiceSet).getTitle()==null) {
            throw new NullQuestionSet("Multiple choice set or its fields are null");
        } else if (multipleChoiceSet.getTitle().isEmpty() || multipleChoiceSet.getTopic().isEmpty()) {
            throw new EmptyQuestionSetTitleOrTopic("Title or topic are empty");
        }
        return true;
    }

    @Override
    public MultipleChoiceQuestion addMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws EmptyQuestionSetQuestions, MultipleChoiceQuestionOptionError {
        int count = 0;
        if (multipleChoiceQuestion == null) {
            throw new EmptyQuestionSetQuestions("Multiple choice question is empty");
        }
//        This step is required because of the frontend implementation
        if (multipleChoiceQuestion.getQuestionNumber() == 0) {
            return new MultipleChoiceQuestion();
        }
        //if(multipleChoiceQuestion.ge)

//        else if (multipleChoiceQuestion.getQuestion().isEmpty()) {
//            throw new EmptyQuestionSetQuestions("Multiple choice question set must have a question with a valid title ");
//        }
//        for (var questionOption : multipleChoiceQuestion.getQuestionOptions()) {
//            count++;
//            if (questionOption.getAnswer().isEmpty()) {
//                throw new MultipleChoiceQuestionOptionError("Multiple choice question option must contain an answer");
//            }
//        }
//        if (count < 2) {
//            throw new MultipleChoiceQuestionOptionError("Multiple choice question option must contain at least 2 options");
//        }
        //       return true;
        return null;
    }

    @Override
    public MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, UnexpectedError, UserNotFound, EmptyQuestionSetTitleOrTopic, QuestionSetAlreadyExists, NullQuestionSet {
        MultipleChoiceSet createdMultipleChoiceSet;
        if (multipleChoiceSet.getUser() == null) {
            throw new UnexpectedError("User is not set");
        }
        validateMultipleChoiceSetFields(multipleChoiceSet);
        User fetchedUser = iUserRequests.getUserByUsername(multipleChoiceSet.getUser().getUsername());
        if (fetchedUser == null) {
            throw new UserNotFound("User that is trying to create the question set is not authorized");
        }

        multipleChoiceSet.setUser(fetchedUser);
      //  if question set is not found it can be created
        try {
            getMultipleChoiceSet(multipleChoiceSet);
            throw new QuestionSetAlreadyExists("Multiple Choice Set" + "with title: " + multipleChoiceSet.getTitle() +
                    "and topic: " + multipleChoiceSet.getTopic() + " ALREADY EXISTS");
        } catch (NullQuestionSet nullQuestionSet) {
            createdMultipleChoiceSet = questionSetsRequests.createMultipleChoiceSet(multipleChoiceSet);
       }
        if (createdMultipleChoiceSet == null) {
            throw new UnexpectedError("Something went wrong");
        }
        return createdMultipleChoiceSet;
    }

    @Override
    public MultipleChoiceSet getMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, NullQuestionSet, QuestionSetAlreadyExists, EmptyQuestionSetTitleOrTopic, UnexpectedError {
        if (validateMultipleChoiceSetFields(multipleChoiceSet)) {
            User fetchedUser=iUserRequests.getUserByUsername(multipleChoiceSet.getUser().getUsername());
            multipleChoiceSet.setUser(fetchedUser);
            MultipleChoiceSet fetchedMultipleChoice = questionSetsRequests.getMultipleChoiceSet(multipleChoiceSet);
            if (fetchedMultipleChoice == null) {
                throw new NullQuestionSet("Multiple Choice Set" + "with title: " + multipleChoiceSet.getTitle() +
                        "and topic: " + multipleChoiceSet.getTopic() + " WAS NOT FOUND");
            } else
                return fetchedMultipleChoice;
        } else
            throw new UnexpectedError("Something went wrong");
    }
}

