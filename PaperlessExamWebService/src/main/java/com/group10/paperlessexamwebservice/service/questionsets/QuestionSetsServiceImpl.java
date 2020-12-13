package com.group10.paperlessexamwebservice.service.questionsets;

import com.group10.paperlessexamwebservice.databaserequests.requests.questionsets.IQuestionSetsRequests;
import com.group10.paperlessexamwebservice.databaserequests.requests.user.IUserRequests;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.QuestionOption;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenQuestion;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
import com.group10.paperlessexamwebservice.model.user.User;
import com.group10.paperlessexamwebservice.service.exceptions.other.NegativeNumberException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.*;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.EmptyMultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.NullQuestionSetQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.user.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * The type Question sets service.
 */
@Service
public class QuestionSetsServiceImpl implements IQuestionSetsService {

    @Autowired
    private IQuestionSetsRequests questionSetsRequests;

    @Autowired
    private IUserRequests userRequests;


    private boolean validateMultipleChoiceSetFields(MultipleChoiceSet multipleChoiceSet) throws NullQuestionSet, EmptyQuestionSetTitleOrTopic {
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
    public WrittenSet createWrittenSet(WrittenSet writtenSet) throws NullQuestionSet, EmptyQuestionSetTitleOrTopic, ServiceNotAvailable, UserNotFound, UnexpectedError, QuestionSetAlreadyExists, EmptyMultipleChoiceQuestion {
        WrittenSet createdWrittenSet = null;
        validateWrittenSetFields(writtenSet);
        User fetchedUser = userRequests.getUserByUsername(writtenSet.getUser().getUsername());
        if (fetchedUser == null) {
            throw new UserNotFound("User that is trying to create the question set is not authorized");
        }
        writtenSet.setUser(fetchedUser);
        try {
            getWrittenSet(writtenSet);
            throw new QuestionSetAlreadyExists("Written Set" + "with title: " + writtenSet.getTitle() +
                    "and topic: " + writtenSet.getTopic() + " ALREADY EXISTS");
        } catch (NullQuestionSet e) {
            createdWrittenSet = questionSetsRequests.createWrittenSet(writtenSet);
            if (createdWrittenSet == null) {
                throw new UnexpectedError("Something went wrong");
            }

            for (var question : writtenSet.getWrittenQuestions()) {
                question.setWrittenSet(writtenSet);
                addWrittenQuestion(question);
            }
        }
        return createdWrittenSet;
    }
    @Override
    //TODO A better aproch must be found. It is not a very good idea to delete data from database
    public WrittenSet updateWrittenSet(WrittenSet writtenSet) throws NullQuestionSet, UnexpectedError, ServiceNotAvailable, EmptyQuestionSetTitleOrTopic, EmptyMultipleChoiceQuestion, QuestionSetAlreadyExists {
       // WrittenSet writtenSetToUpdate = getWrittenSet(writtenSet);
        WrittenSet oldWrittenSet=getWrittenSetWithAllChildElements(writtenSet.getId());
        if(writtenSet.getWrittenQuestions().equals(oldWrittenSet.getWrittenQuestions())){
            return writtenSet;
        }
       else{
//           Delete old questions
           for (var question : oldWrittenSet.getWrittenQuestions()) {
               question.setWrittenSet(writtenSet);
               deleteWrittenQuestion(question);
           }
//           Populate db again
            for (var question : writtenSet.getWrittenQuestions()) {
                question.setWrittenSet(writtenSet);
                addWrittenQuestion(question);
            }
       }
//        for (var questionOld:oldWrittenSet.getWrittenQuestions()) {
//            for (var questionNew:writtenSet.getWrittenQuestions()) {
//                if(questionNew)
//            }
//        }
        return writtenSet;
    }


    @Override
    public WrittenSet getWrittenSet(WrittenSet writtenSet) throws NullQuestionSet, EmptyQuestionSetTitleOrTopic, ServiceNotAvailable, UnexpectedError {
        if (validateWrittenSetFields(writtenSet)) {
            User fetchedUser = userRequests.getUserByUsername(writtenSet.getUser().getUsername());
            writtenSet.setUser(fetchedUser);
            WrittenSet fetchedWrittenSet = questionSetsRequests.getWrittenSet(writtenSet);
            if (fetchedWrittenSet == null) {
                throw new NullQuestionSet("Written Set" + "with title: " + writtenSet.getTitle() +
                        "and topic: " + writtenSet.getTopic() + " WAS NOT FOUND");
            } else
                return fetchedWrittenSet;
        } else
            throw new UnexpectedError("Something went wrong");
    }

    @Override
    public WrittenQuestion addWrittenQuestion(WrittenQuestion writtenQuestion) throws EmptyMultipleChoiceQuestion, NullQuestionSet, EmptyQuestionSetTitleOrTopic, UnexpectedError, ServiceNotAvailable, QuestionSetAlreadyExists {
        WrittenQuestion createdWrittenQuestion = null;
        validateWrittenQuestionFields(writtenQuestion);
        WrittenSet fetchedWrittenSet = getWrittenSet(writtenQuestion.getWrittenSet());
        writtenQuestion.setWrittenSet(fetchedWrittenSet);

        try {
            getWrittenQuestion(writtenQuestion);
            throw new QuestionSetAlreadyExists("Written Set Question" + " with question: " + writtenQuestion.getQuestion() +
                    "and question score: " + writtenQuestion.getQuestionScore() + " WAS FOUND");

        } catch (NullQuestionSetQuestion e) {
            createdWrittenQuestion = questionSetsRequests.createWrittenQuestion(writtenQuestion);
            if (createdWrittenQuestion == null) {
                throw new UnexpectedError("Something went wrong");
            }
        }
        return createdWrittenQuestion;
    }

    @Override
    public WrittenQuestion getWrittenQuestion(WrittenQuestion writtenQuestion) throws EmptyMultipleChoiceQuestion, NullQuestionSet, EmptyQuestionSetTitleOrTopic, UnexpectedError, ServiceNotAvailable, NullQuestionSetQuestion {
        validateWrittenQuestionFields(writtenQuestion);
        WrittenSet fetchedWrittenSet = getWrittenSet(writtenQuestion.getWrittenSet());
        writtenQuestion.setWrittenSet(fetchedWrittenSet);
        WrittenQuestion fetchedWrittenQuestion = questionSetsRequests.getWrittenQuestion(writtenQuestion);
        if (fetchedWrittenQuestion == null) {
            throw new NullQuestionSetQuestion("Written Set Question" + "with question: " + writtenQuestion.getQuestion() +
                    "and question score: " + writtenQuestion.getQuestionScore() + " WAS NOT FOUND");
        } else
            return fetchedWrittenQuestion;
    }

    @Override

    public List<MultipleChoiceSet> getUsersAllMultipleChoiceSet(String username) throws EmptyQuestionSetTitleOrTopic, NullQuestionSet, ServiceNotAvailable, UnexpectedError, UsersMultipleChoiceSetNotFound, UserNotFound {
        User fetchedUser = userRequests.getUserByUsername(username);
        if (fetchedUser == null) {
            throw new UserNotFound("User with give username was not found");
        }
        List<MultipleChoiceSet> fetchedMultipleChoiceQuestionList = questionSetsRequests.getUsersAllMultipleChoiceSet(fetchedUser);
        if (fetchedMultipleChoiceQuestionList == null) {
            throw new UsersMultipleChoiceSetNotFound("User does not have any Multiple Choice Sets created");
        }
        return fetchedMultipleChoiceQuestionList;
    }

    @Override
    public List<WrittenSet> getUsersAllWrittenSet(String username) throws ServiceNotAvailable, UsersWrittenSetNotFound, UserNotFound {
        User fetchedUser = userRequests.getUserByUsername(username);
        if (fetchedUser == null) {
            throw new UserNotFound("User with give username was not found");
        }
        List<WrittenSet> fetchedWrittenSetsList = questionSetsRequests.getUsersAllWrittenSet(fetchedUser);
        if (fetchedWrittenSetsList == null) {
            throw new UsersWrittenSetNotFound("User does not have any Written Sets created");
        }
        return fetchedWrittenSetsList;
    }

    @Override
    public WrittenSet deleteWrittenSet(WrittenSet writtenSetToDelete) throws ServiceNotAvailable {
        return questionSetsRequests.deleteWrittenSet(writtenSetToDelete);
    }

    @Override
    public MultipleChoiceSet deleteMultipleChoiceSet(MultipleChoiceSet multipleChoiceSetToDelete) throws ServiceNotAvailable {
        return questionSetsRequests.deleteMultipleChoiceSet(multipleChoiceSetToDelete);
    }

    @Override
    public MultipleChoiceQuestion deleteMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestionToDelete) throws UnexpectedError, ServiceNotAvailable {
        MultipleChoiceQuestion deletedMultipleChoiceQuestion = questionSetsRequests.deleteMultipleChoiceQuestion(multipleChoiceQuestionToDelete);
        if (deletedMultipleChoiceQuestion == null) {
            throw new UnexpectedError("Something went wrong");
        }
        return deletedMultipleChoiceQuestion;
    }

    @Override
    public WrittenQuestion deleteWrittenQuestion(WrittenQuestion writtenQuestionToDelete) throws UnexpectedError, ServiceNotAvailable {
        WrittenQuestion deletedWrittenQuestion = questionSetsRequests.deleteWrittenSetQuestion(writtenQuestionToDelete);
        if (deletedWrittenQuestion == null) {
            throw new UnexpectedError("Something went wrong");
        }
        return deletedWrittenQuestion;
    }

    @Override
    public WrittenSet getWrittenSetWithAllChildElements(long writtenSetId) throws ServiceNotAvailable, UnexpectedError {
        WrittenSet fetchedWrittenSet = questionSetsRequests.getWrittenSetById(writtenSetId);
        if (fetchedWrittenSet == null) {
            throw new UnexpectedError("Something went wrong");
        }
        List<WrittenQuestion> fetchedWrittenQuestionList = questionSetsRequests.getWrittenSetQuestionsByWrittenSet(fetchedWrittenSet);
        fetchedWrittenSet.setWrittenQuestions(fetchedWrittenQuestionList);
        return fetchedWrittenSet;
    }

    private boolean validateWrittenQuestionFields(WrittenQuestion writtenQuestion) throws NullQuestionSet, EmptyMultipleChoiceQuestion {
        if (Objects.requireNonNull(writtenQuestion).getQuestion() == null || Objects.requireNonNull(writtenQuestion).getWrittenSet() == null) {
            throw new NullQuestionSet("One or more multiple choice set questions fields are null");
        } else if (writtenQuestion.getQuestion().isEmpty()) {
            throw new EmptyMultipleChoiceQuestion("Question is empty");
        }
        return true;
    }

    @Override
    public MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, UnexpectedError, UserNotFound, EmptyQuestionSetTitleOrTopic, QuestionSetAlreadyExists, NullQuestionSet, NullQuestionSetQuestion, NegativeNumberException, EmptyMultipleChoiceQuestion {
        MultipleChoiceSet createdMultipleChoiceSet;
        validateMultipleChoiceSetFields(multipleChoiceSet);
        User fetchedUser = userRequests.getUserByUsername(multipleChoiceSet.getUser().getUsername());
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

        for (var multipleChoiceQuestion : multipleChoiceSet.getMultipleChoiceQuestionList()) {
            multipleChoiceQuestion.setMultipleChoiceSet(createdMultipleChoiceSet);
            addMultipleChoiceQuestion(multipleChoiceQuestion);
            for (var multipleChoiceQuestionOption : multipleChoiceQuestion.getQuestionOptions()) {
                multipleChoiceQuestionOption.setMultipleChoiceQuestion(multipleChoiceQuestion);
                addMultipleChoiceQuestionOption(multipleChoiceQuestionOption);
            }

        }
        return createdMultipleChoiceSet;
    }

    @Override
    public MultipleChoiceSet getMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, NullQuestionSet, EmptyQuestionSetTitleOrTopic, UnexpectedError {
        if (validateMultipleChoiceSetFields(multipleChoiceSet)) {
            User fetchedUser = userRequests.getUserByUsername(multipleChoiceSet.getUser().getUsername());
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
    public MultipleChoiceQuestion getMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceSetQuestion) throws NullQuestionSet, NegativeNumberException, EmptyMultipleChoiceQuestion, ServiceNotAvailable, NullQuestionSetQuestion, EmptyQuestionSetTitleOrTopic, UnexpectedError {
        validateMultipleChoiceQuestionSetFields(multipleChoiceSetQuestion);
        MultipleChoiceSet fetchedMultipleChoiceSet = getMultipleChoiceSet(multipleChoiceSetQuestion.getMultipleChoiceSet());
        multipleChoiceSetQuestion.setMultipleChoiceSet(fetchedMultipleChoiceSet);

        MultipleChoiceQuestion fetchedMultipleChoiceQuestion = questionSetsRequests.getMultipleChoiceSetQuestion(multipleChoiceSetQuestion);
        if (fetchedMultipleChoiceQuestion == null) {
            throw new NullQuestionSetQuestion("Multiple Choice Set Question" + "with question: " + multipleChoiceSetQuestion.getQuestion() +
                    "and question score: " + multipleChoiceSetQuestion.getQuestionScore() + " WAS NOT FOUND");
        } else
            return fetchedMultipleChoiceQuestion;

    }

    @Override
    public QuestionOption addMultipleChoiceQuestionOption(QuestionOption multipleChoiceQuestionOption) throws EmptyMultipleChoiceQuestion, NullQuestionSet, NegativeNumberException, UnexpectedError, NullQuestionSetQuestion, ServiceNotAvailable, EmptyQuestionSetTitleOrTopic, QuestionSetAlreadyExists {
        QuestionOption createdQuestionOption;
        validateMultipleChoiceQuestionOptionFields(multipleChoiceQuestionOption);


        //Get multiple choice question from the system
        MultipleChoiceQuestion fetchedMultipleChoiceQuestion = getMultipleChoiceSetQuestion(multipleChoiceQuestionOption.getMultipleChoiceQuestion());
        multipleChoiceQuestionOption.setMultipleChoiceQuestion(fetchedMultipleChoiceQuestion);

        try {
            getMultipleChoiceSetQuestionOption(multipleChoiceQuestionOption);
            throw new QuestionSetAlreadyExists("Multiple Choice Set Question Option" + " with answer: " + multipleChoiceQuestionOption.getAnswer() +
                    "and question boolean value: " + multipleChoiceQuestionOption.getCorrectAnswer() + " WAS FOUND");
        } catch (NullQuestionSetQuestion e) {
            createdQuestionOption = questionSetsRequests.createMultipleChoiceSetQuestionOption(multipleChoiceQuestionOption);
            if (createdQuestionOption == null) {
                throw new UnexpectedError("Something went wrong");
            }

        }
        return createdQuestionOption;
    }

    @Override
    public QuestionOption getMultipleChoiceSetQuestionOption(QuestionOption multipleChoiceQuestionOption) throws EmptyMultipleChoiceQuestion, NullQuestionSet, UnexpectedError, NullQuestionSetQuestion, NegativeNumberException, EmptyQuestionSetTitleOrTopic, ServiceNotAvailable {
        validateMultipleChoiceQuestionOptionFields(multipleChoiceQuestionOption);
        MultipleChoiceQuestion fetchedMultipleChoiceQuestion = getMultipleChoiceSetQuestion(multipleChoiceQuestionOption.getMultipleChoiceQuestion());
        multipleChoiceQuestionOption.setMultipleChoiceQuestion(fetchedMultipleChoiceQuestion);

        QuestionOption fetchedMultipleChoiceQuestionOption = questionSetsRequests.getMultipleChoiceSetQuestionOption(multipleChoiceQuestionOption);
        if (fetchedMultipleChoiceQuestionOption == null) {
            throw new NullQuestionSetQuestion("Multiple Choice Set Question Option" + " with answer: " + multipleChoiceQuestionOption.getAnswer() +
                    "and question boolean value: " + multipleChoiceQuestionOption.getCorrectAnswer() + " WAS NOT FOUND");
        } else
            return fetchedMultipleChoiceQuestionOption;
    }


    private boolean validateWrittenSetFields(WrittenSet writtenSet) throws EmptyQuestionSetTitleOrTopic, NullQuestionSet {
        // Validate multiple choice set on null. Check if title and  topic are not empty
        if (Objects.requireNonNull(writtenSet).getTopic() == null || Objects.requireNonNull(writtenSet).getTitle() == null || Objects.requireNonNull(writtenSet).getUser() == null) {
            throw new NullQuestionSet("Written set or its fields are null");
        } else if (writtenSet.getTitle().isEmpty() || writtenSet.getTopic().isEmpty()) {
            throw new EmptyQuestionSetTitleOrTopic("Title or topic are empty");
        }
        return true;
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

