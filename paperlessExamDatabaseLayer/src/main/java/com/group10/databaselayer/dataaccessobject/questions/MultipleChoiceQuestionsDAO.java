package com.group10.databaselayer.dataaccessobject.questions;

import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.multiplechoice.QuestionOption;
import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.exception.questions.QuestionAlreadyExists;
import com.group10.databaselayer.exception.questions.QuestionNotFound;
import com.group10.databaselayer.exception.questions.QuestionSetNotFound;
import com.group10.databaselayer.exception.questions.TitleOrTopicAreNull;
import com.group10.databaselayer.repositories.questions.multiplechoice.IMultipleChoiceQuestionOptionRepository;
import com.group10.databaselayer.repositories.questions.multiplechoice.IMultipleChoiceQuestionRepository;
import com.group10.databaselayer.repositories.questions.multiplechoice.IMultipleChoiceSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing written questions
 *
 * @author Marin Bilba
 * @version 1.0
 */
@Component
public class MultipleChoiceQuestionsDAO {

    private IMultipleChoiceSetRepository multipleChoiceSetRepository;
    private IMultipleChoiceQuestionOptionRepository multipleChoiceQuestionOptionRepository;
    private IMultipleChoiceQuestionRepository multipleChoiceQuestionRepository;


    /**
     * Instantiates a new Multiple choice questions controller using Spring Boot dependency injection.
     *
     * @param multipleChoiceSetRepository            the multiple choice set repository
     * @param multipleChoiceQuestionOptionRepository the multiple choice question option repository
     * @param multipleChoiceQuestionRepository       the multiple choice question repository
     */
    @Autowired
    public MultipleChoiceQuestionsDAO(IMultipleChoiceSetRepository multipleChoiceSetRepository, IMultipleChoiceQuestionOptionRepository
            multipleChoiceQuestionOptionRepository, IMultipleChoiceQuestionRepository multipleChoiceQuestionRepository
    ) {
        this.multipleChoiceSetRepository = multipleChoiceSetRepository;
        this.multipleChoiceQuestionOptionRepository = multipleChoiceQuestionOptionRepository;
        this.multipleChoiceQuestionRepository = multipleChoiceQuestionRepository;

    }


    /**
     * Create/update multiple choice set.
     *
     * @param multipleChoiceSet the multiple choice that should be created/updated
     * @return the multiple choice set
     */
    public MultipleChoiceSet createUpdateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) {
        return multipleChoiceSetRepository.save(multipleChoiceSet);
    }

    /**
     * Remove multiple choice set. Method checks if the passed MultipleChoiceSet exists,
     * otherwise an exception will be thrown.
     *
     * @param multipleChoiceSet the written set that should be queried
     * @return a confirmation String containing title of the removed Multiple Choice Set
     * @throws TitleOrTopicAreNull in case the title or topic are null
     * @throws QuestionSetNotFound in case the written set not found
     */
    public String removeMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws TitleOrTopicAreNull, QuestionSetNotFound {
        if (existsMultipleChoiceSet(multipleChoiceSet)) {
            multipleChoiceSetRepository.delete(multipleChoiceSet);
            return "Multiple choice set " + multipleChoiceSet.getTitle() + " was REMOVED";
        }
        throw new QuestionSetNotFound("Written set with given title and topic was not found");
    }

    /**
     * Finds if the given multiple choice set is defined in the database. The written set must contain both title
     * and topic of the questions set
     *
     * @param multipleChoiceSet multiple choice set that should be queried
     * @return boolean value true if the set was found and false if it was not.
     * @throws TitleOrTopicAreNull in case the title or topic are empty
     */
    //todo not sure this is working anymore
    public boolean existsMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws TitleOrTopicAreNull {
        Optional<MultipleChoiceSet> queriedMultipleChoiceSet = Optional.empty();
        if (checkTitleTopicNotNull(multipleChoiceSet)) {
            queriedMultipleChoiceSet = Optional.ofNullable(multipleChoiceSetRepository.findByTitleAndTopicAndUserIdAndSubmittedMultipleChoiceSet(multipleChoiceSet.getTitle(), multipleChoiceSet.getTopic(), multipleChoiceSet.getUser().getId(), multipleChoiceSet.isSubmittedMultipleChoiceSet()));
        }
        return queriedMultipleChoiceSet.isPresent();
    }

    /**
     * Check if title and topic are not null.
     *
     * @param questionsSet the questions set
     * @return the boolean true if the check title and topic are not null
     * @throws TitleOrTopicAreNull the title or topic are null
     */
    public boolean checkTitleTopicNotNull(MultipleChoiceSet questionsSet) throws TitleOrTopicAreNull {
        String titleToQuery = questionsSet.getTitle();
        String topicToQuery = questionsSet.getTopic();
        if (!titleToQuery.isEmpty() && !topicToQuery.isEmpty()) {
            return true;
        } else {
            throw new TitleOrTopicAreNull("Title or topic are not set");
        }

    }

    /**
     * Add question to existing multiple choice set. Method checks if the passed MultipleChoiceSet exists, otherwise exception is thrown.
     * If set exists, the passed MultipleChoice question is check if such exists. If the passed question does not exist it will
     * be added to the database.
     *
     * @param multipleChoiceSet      the multiple choice set
     * @param multipleChoiceQuestion the multiple choice question
     * @return the multiple choice question
     * @throws QuestionSetNotFound   the question set not found
     * @throws TitleOrTopicAreNull   the title or topic are null
     * @throws QuestionAlreadyExists the question already exists
     */
    public MultipleChoiceQuestion addQuestionToExistingMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet,
                                                                         MultipleChoiceQuestion multipleChoiceQuestion) throws QuestionSetNotFound, TitleOrTopicAreNull, QuestionAlreadyExists {
        // Check if the passed multipleChoiceSet exists
        boolean questionNotFound = false;
        Optional<MultipleChoiceSet> tempQueriedMultipleChoiceSet = multipleChoiceSetRepository.findById(multipleChoiceSet.getId());

        if (tempQueriedMultipleChoiceSet.isPresent()) {
            MultipleChoiceSet queriedMultipleChoiceSet = tempQueriedMultipleChoiceSet.get();
// Check if question already exists
            try {
                findQuestionInMultipleChoiceSet(multipleChoiceSet, multipleChoiceQuestion);
            } catch (QuestionNotFound notFound) {
                questionNotFound = true;
            }
            if (questionNotFound) {
                multipleChoiceQuestion.setMultipleChoiceSet(queriedMultipleChoiceSet);
                return multipleChoiceQuestionRepository.save(multipleChoiceQuestion);
            } else
                throw new QuestionAlreadyExists("Question '" + multipleChoiceQuestion.getQuestion() + "' already exists");

        } else throw new QuestionSetNotFound("Multiple choice set with given title and topic was not found");
    }


    /**
     * Find question in multiple choice set. Method checks if the passed MultipleChoiceSet exists,
     * otherwise exception is thrown. The list of multiple choice question are retrieved from the
     * database based on the passed  multiple choice set.
     * The passed written question is searched in the list and if it is found it will be returned.
     *
     * @param multipleChoiceSet      the multiple choice set
     * @param multipleChoiceQuestion the multiple choice question
     * @return the multiple choice question
     * @throws QuestionNotFound    the question not found
     * @throws TitleOrTopicAreNull the title or topic are null
     * @throws QuestionSetNotFound the question set not found
     */
    public MultipleChoiceQuestion findQuestionInMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet,
                                                                  MultipleChoiceQuestion multipleChoiceQuestion) throws QuestionNotFound, TitleOrTopicAreNull, QuestionSetNotFound {
        if (existsMultipleChoiceSet(multipleChoiceSet)) {
            List<MultipleChoiceQuestion> fetchedMultipleChoiceQuestionsList = multipleChoiceQuestionRepository.findByMultipleChoiceSet(multipleChoiceSet);
            MultipleChoiceQuestion foundMultipleChoiceQuestion = null;

            for (var question : fetchedMultipleChoiceQuestionsList) {
                if (question.getQuestion().equals(multipleChoiceQuestion.getQuestion()) &&
                        question.getScore() == question.getScore()) {
                    foundMultipleChoiceQuestion = multipleChoiceQuestion;
                }
            }
            if (foundMultipleChoiceQuestion != null) {
                return foundMultipleChoiceQuestion;
            } else throw new QuestionNotFound("Question was not found");
        }
        throw new QuestionSetNotFound("Written set with given title and topic was not found");
    }

    /**
     * Gets multiple choice set from the database.
     *
     * @param multipleChoiceSet the multiple choice set
     * @return the multiple choice set or null if not found
     */
    public Optional<MultipleChoiceSet> getMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) {
        return multipleChoiceSetRepository.findById(multipleChoiceSet.getId());
    }

    /**
     * Create update multiple choice set question multiple choice question.
     *
     * @param multipleChoiceSetQuestion the multiple choice set question
     * @return the multiple choice question
     */
    public MultipleChoiceQuestion createUpdateMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceSetQuestion) {
        return multipleChoiceQuestionRepository.save(multipleChoiceSetQuestion);
    }

    /**
     * Gets multiple choice set question.
     *
     * @param multipleChoiceSetQuestion the multiple choice set question
     * @return the multiple choice set question
     */
    public Optional<MultipleChoiceQuestion> getMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceSetQuestion) {
        MultipleChoiceSet multipleChoiceSet = multipleChoiceSetQuestion.getMultipleChoiceSet();
        return multipleChoiceQuestionRepository.findById(multipleChoiceSetQuestion.getId());

    }

    /**
     * Gets multiple choice set question option.
     *
     * @param multipleChoiceSetQuestionOption the multiple choice set question option
     * @return the multiple choice set question option
     */
    public Optional<QuestionOption> getMultipleChoiceSetQuestionOption(QuestionOption multipleChoiceSetQuestionOption) {
        MultipleChoiceQuestion multipleChoiceSetQuestion = multipleChoiceSetQuestionOption.getMultipleChoiceQuestion();
        MultipleChoiceSet multipleChoiceSet = multipleChoiceSetQuestion.getMultipleChoiceSet();
        // multipleChoiceQuestionOptionRepository.findAll((Pageable) multipleChoiceSetQuestion);
        return multipleChoiceQuestionOptionRepository.findById(multipleChoiceSetQuestionOption.getId());


    }

    /**
     * Create update multiple choice set question option question option.
     *
     * @param multipleChoiceSetQuestionOption the multiple choice set question option
     * @return the question option
     */
    public QuestionOption createUpdateMultipleChoiceSetQuestionOption(QuestionOption multipleChoiceSetQuestionOption) {
        return multipleChoiceQuestionOptionRepository.save(multipleChoiceSetQuestionOption);

    }

    /**
     * Gets all questions by multiple choice set.
     *
     * @param multipleChoiceSet the multiple choice set
     * @return the all questions by multiple choice set
     */
    public List<MultipleChoiceQuestion> getAllQuestionsByMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) {
        return multipleChoiceQuestionRepository.findByMultipleChoiceSet(multipleChoiceSet);
    }

    /**
     * Gets all question options by multiple choice question.
     *
     * @param multipleChoiceQuestion the multiple choice question
     * @return the all question options by multiple choice question
     */
    public List<QuestionOption> getAllQuestionOptionsByMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        return multipleChoiceQuestionOptionRepository.findByMultipleChoiceQuestion(multipleChoiceQuestion);
    }


    /**
     * Gets all users multiple choice set.
     *
     * @param userDeserialized the user deserialized
     * @return the all users multiple choice set
     */
    public List<MultipleChoiceSet> getAllUsersMultipleChoiceSet(User userDeserialized) {
        return multipleChoiceSetRepository.findByUserId(userDeserialized.getId());
    }

    /**
     * Delete multiple choice set multiple choice set.
     *
     * @param multipleChoiceSetToDelete the multiple choice set to delete
     * @return the multiple choice set
     */
    public MultipleChoiceSet deleteMultipleChoiceSet(MultipleChoiceSet multipleChoiceSetToDelete) {

        multipleChoiceSetRepository.delete(multipleChoiceSetToDelete);
        return multipleChoiceSetToDelete;
    }

    /**
     * Delete multiple choice question multiple choice question.
     *
     * @param multipleChoiceQuestionToDelete the multiple choice question to delete
     * @return the multiple choice question
     */
    public MultipleChoiceQuestion deleteMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestionToDelete) {
        multipleChoiceQuestionRepository.delete(multipleChoiceQuestionToDelete);
        return multipleChoiceQuestionToDelete;
    }

    public Optional<MultipleChoiceSet> getMultipleChoiceSetById(long multipleChoiceSetId) {

        return multipleChoiceSetRepository.findById(multipleChoiceSetId);
    }


    public List<MultipleChoiceSet> getExaminationEventMultipleChoiceSets(long examId) {
        return multipleChoiceSetRepository.getExaminationEventMultipleChoiceSet(examId);
    }
}
