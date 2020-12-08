package com.group10.paperlessexamwebservice.service.questionsets;

import com.group10.paperlessexamwebservice.databaserequests.requests.questionsets.IQuestionSetsRequests;
import com.group10.paperlessexamwebservice.databaserequests.requests.user.IUserRequests;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.QuestionOption;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.NegativeNumberException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.*;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.EmptyMultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.MultipleChoiceQuestionOptionError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.NullQuestionSetQuestion;
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
        if (Objects.requireNonNull(multipleChoiceSet).getTopic() == null || Objects.requireNonNull(multipleChoiceSet).getTitle() == null || Objects.requireNonNull(multipleChoiceSet).getUser() == null) {
            throw new NullQuestionSet("Multiple choice set or its fields are null");
        } else if (multipleChoiceSet.getTitle().isEmpty() || multipleChoiceSet.getTopic().isEmpty()) {
            throw new EmptyQuestionSetTitleOrTopic("Title or topic are empty");
        }
        return true;
    }

    @Override
    public MultipleChoiceQuestion addMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws NullQuestionSet, NegativeNumberException, EmptyMultipleChoiceQuestion, ServiceNotAvailable, EmptyQuestionSetTitleOrTopic, UnexpectedError, QuestionSetAlreadyExists {
        MultipleChoiceQuestion createdMultipleChoiceQuestion;
        validateMultipleChoiceQuestionSetFields(multipleChoiceQuestion);
//      This step is required because of the frontend implementation
        if (multipleChoiceQuestion.getQuestionNumber() == 0) {
            return new MultipleChoiceQuestion();
        }
        //        Get multiple choice from the system

        MultipleChoiceSet fetchedMultipleChoiceSet = getMultipleChoiceSet(multipleChoiceQuestion.getMultipleChoiceSet());
        multipleChoiceQuestion.setMultipleChoiceSet(fetchedMultipleChoiceSet);

        try {
            getMultipleChoiceSetQuestion(multipleChoiceQuestion);
            throw new QuestionSetAlreadyExists("Multiple Choice Set Question" + "with question: " + multipleChoiceQuestion.getQuestion() +
                    " and score: " + multipleChoiceQuestion.getQuestionScore() + " ALREADY EXISTS");
        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {

            createdMultipleChoiceQuestion = questionSetsRequests.createMultipleChoiceSetQuestion(multipleChoiceQuestion);
            if (createdMultipleChoiceQuestion == null) {
                throw new UnexpectedError("Something went wrong");
            }
        }

        return createdMultipleChoiceQuestion;

    }

    private boolean validateMultipleChoiceQuestionSetFields(MultipleChoiceQuestion multipleChoiceQuestion) throws NullQuestionSet, EmptyMultipleChoiceQuestion, NegativeNumberException {
        // Validate multiple choice questions set on null. Check if title and  topic are not empty
        if (Objects.requireNonNull(multipleChoiceQuestion).getQuestion() == null || Objects.requireNonNull(multipleChoiceQuestion).getMultipleChoiceSet() == null) {
            throw new NullQuestionSet("One or more multiple choice set questions fields are null");
        } else if (multipleChoiceQuestion.getQuestion().isEmpty()) {
            throw new EmptyMultipleChoiceQuestion("Question is empty");
        } else if (multipleChoiceQuestion.getQuestionNumber() < 0) {
            throw new NegativeNumberException("Multiple choice set question number can not be negative");
        } else if (multipleChoiceQuestion.getQuestionScore() <= 0) {
            throw new NegativeNumberException("Multiple choice set question score can not be negative or 0");
        }
        return true;
    }

    @Override
    public MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, UnexpectedError, UserNotFound, EmptyQuestionSetTitleOrTopic, QuestionSetAlreadyExists, NullQuestionSet {
        MultipleChoiceSet createdMultipleChoiceSet;
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
            User fetchedUser = iUserRequests.getUserByUsername(multipleChoiceSet.getUser().getUsername());
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

    @Override
    public MultipleChoiceQuestion getMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceSetQuestion) throws NullQuestionSet, NegativeNumberException, EmptyMultipleChoiceQuestion, ServiceNotAvailable, NullQuestionSetQuestion {
        validateMultipleChoiceQuestionSetFields(multipleChoiceSetQuestion);
        MultipleChoiceSet fetchedMultipleChoiceSet = questionSetsRequests.getMultipleChoiceSet(multipleChoiceSetQuestion.getMultipleChoiceSet());
        multipleChoiceSetQuestion.setMultipleChoiceSet(fetchedMultipleChoiceSet);

        MultipleChoiceQuestion fetchedMultipleChoiceQuestion = questionSetsRequests.getMultipleChoiceSetQuestion(multipleChoiceSetQuestion);
        if (fetchedMultipleChoiceQuestion == null) {
            throw new NullQuestionSetQuestion("Multiple Choice Set Question" + "with question: " + multipleChoiceSetQuestion.getQuestion() +
                    "and question score: " + multipleChoiceSetQuestion.getQuestionScore() + " WAS NOT FOUND");
        } else
            return fetchedMultipleChoiceQuestion;

    }

    @Override
    public QuestionOption addMultipleChoiceQuestionOption(QuestionOption multipleChoiceQuestionOption) throws EmptyMultipleChoiceQuestion, NullQuestionSet, ServiceNotAvailable, NullQuestionSetQuestion, NegativeNumberException, UnexpectedError {
        QuestionOption createdQuestionOption;
        validateMultipleChoiceQuestionOptionFields(multipleChoiceQuestionOption);


        //Get multiple choice question from the system
        MultipleChoiceQuestion fetchedMultipleChoiceQuestion = getMultipleChoiceSetQuestion(multipleChoiceQuestionOption.getMultipleChoiceQuestion());
        multipleChoiceQuestionOption.setMultipleChoiceQuestion(fetchedMultipleChoiceQuestion);


//            getMultipleChoiceSetQuestion(multipleChoiceQuestion);
//            throw new QuestionSetAlreadyExists("Multiple Choice Set Question" + "with question: " + multipleChoiceQuestion.getQuestion() +
//                    " and score: " + multipleChoiceQuestion.getQuestionScore() + " ALREADY EXISTS");

        createdQuestionOption = questionSetsRequests.createMultipleChoiceSetQuestionOption(multipleChoiceQuestionOption);
            if (createdQuestionOption == null) {
                throw new UnexpectedError("Something went wrong");
            }

        return createdQuestionOption;
    }

    private boolean validateMultipleChoiceQuestionOptionFields(QuestionOption multipleChoiceQuestionOption) throws EmptyMultipleChoiceQuestion, NullQuestionSet {
        // Validate multiple choice questions option on null.
        if (Objects.requireNonNull(multipleChoiceQuestionOption).getAnswer() == null) {
            throw new NullQuestionSet("One or more multiple choice set question options fields are null");
        } else if (multipleChoiceQuestionOption.getAnswer().isEmpty()) {
            throw new EmptyMultipleChoiceQuestion("Question is empty");
        }
        return true;
    }
}

