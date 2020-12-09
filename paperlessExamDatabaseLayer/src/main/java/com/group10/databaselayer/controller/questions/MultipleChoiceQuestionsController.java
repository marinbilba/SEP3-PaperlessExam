package com.group10.databaselayer.controller.questions;

import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.multiplechoice.QuestionOption;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.exception.questions.QuestionAlreadyExists;
import com.group10.databaselayer.exception.questions.QuestionNotFound;
import com.group10.databaselayer.exception.questions.TitleOrTopicAreNull;
import com.group10.databaselayer.exception.questions.QuestionSetNotFound;
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
public class MultipleChoiceQuestionsController {

    private IMultipleChoiceSetRepository multipleChoiceSetRepository;
    private IMultipleChoiceQuestionOptionRepository multipleChoiceQuestionOptionRepository;
    private IMultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    private WrittenMultipleChoiceQuestionsSharedMethods writtenMultipleChoiceQuestionsSharedMethods;


    /**
     * Instantiates a new Multiple choice questions controller using Spring Boot dependency injection.
     *
     * @param multipleChoiceSetRepository                 the multiple choice set repository
     * @param multipleChoiceQuestionOptionRepository      the multiple choice question option repository
     * @param multipleChoiceQuestionRepository            the multiple choice question repository
     * @param writtenMultipleChoiceQuestionsSharedMethods the written multiple choice questions shared methods
     */
    @Autowired
    public MultipleChoiceQuestionsController(IMultipleChoiceSetRepository multipleChoiceSetRepository, IMultipleChoiceQuestionOptionRepository
            multipleChoiceQuestionOptionRepository, IMultipleChoiceQuestionRepository multipleChoiceQuestionRepository,
                                             WrittenMultipleChoiceQuestionsSharedMethods writtenMultipleChoiceQuestionsSharedMethods) {
        this.multipleChoiceSetRepository = multipleChoiceSetRepository;
        this.multipleChoiceQuestionOptionRepository = multipleChoiceQuestionOptionRepository;
        this.multipleChoiceQuestionRepository = multipleChoiceQuestionRepository;
        this.writtenMultipleChoiceQuestionsSharedMethods = writtenMultipleChoiceQuestionsSharedMethods;
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
     * and topic of the questions set{@link QuestionsSet}.
     *
     * @param multipleChoiceSet multiple choice set that should be queried
     * @return boolean value true if the set was found and false if it was not.
     * @throws TitleOrTopicAreNull in case the title or topic are empty
     */
    public boolean existsMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws TitleOrTopicAreNull {
        Optional<MultipleChoiceSet> queriedMultipleChoiceSet = Optional.empty();
        if (writtenMultipleChoiceQuestionsSharedMethods.checkTitleTopicNotNull(multipleChoiceSet)) {
            queriedMultipleChoiceSet = Optional.ofNullable(multipleChoiceSetRepository.findByTitleAndTopicAndUserId(multipleChoiceSet.getTitle(), multipleChoiceSet.getTopic(), multipleChoiceSet.getUser().getId()));
        }
        return queriedMultipleChoiceSet.isPresent();
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
        Optional<MultipleChoiceSet> tempQueriedMultipleChoiceSet = multipleChoiceSetRepository.findById(multipleChoiceSet);

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
                        question.getQuestionScore() == question.getQuestionScore()) {
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
    public MultipleChoiceSet getMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) {
        return multipleChoiceSetRepository.findByTitleAndTopicAndUserId(multipleChoiceSet.getTitle(), multipleChoiceSet.getTopic(), multipleChoiceSet.getUser().getId());
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
    public MultipleChoiceQuestion getMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceSetQuestion) {
        MultipleChoiceSet multipleChoiceSet=multipleChoiceSetQuestion.getMultipleChoiceSet();
        return multipleChoiceQuestionRepository.findByMultipleChoiceSetTopicAndMultipleChoiceSetTitleAndMultipleChoiceSetIdAndQuestionNumberAndQuestionAndScore(multipleChoiceSet.getTopic(),multipleChoiceSet.getTitle(),multipleChoiceSet.getId(), multipleChoiceSetQuestion.getQuestionNumber(), multipleChoiceSetQuestion.getQuestion(), multipleChoiceSetQuestion.getQuestionScore());

    }

    public QuestionOption getMultipleChoiceSetQuestionOption(QuestionOption multipleChoiceSetQuestionOption) {
        MultipleChoiceQuestion multipleChoiceSetQuestion= multipleChoiceSetQuestionOption.getMultipleChoiceQuestion();
       MultipleChoiceSet multipleChoiceSet=multipleChoiceSetQuestion.getMultipleChoiceSet();
      // multipleChoiceQuestionOptionRepository.findAll((Pageable) multipleChoiceSetQuestion);
       QuestionOption qw= multipleChoiceQuestionOptionRepository.findByAnswerAndCorrectAnswerAndMultipleChoiceQuestion_QuestionAndMultipleChoiceQuestion_ScoreAndMultipleChoiceQuestion_QuestionNumber(multipleChoiceSetQuestionOption.getAnswer(),multipleChoiceSetQuestionOption.setCorrectAnswer(),multipleChoiceSetQuestion.getQuestion(),multipleChoiceSetQuestion.getQuestionScore(),multipleChoiceSetQuestion.getQuestionNumber());

        return qw;

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

        public List<MultipleChoiceQuestion> getAllQuestionsByMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) {
        return multipleChoiceQuestionRepository.findByMultipleChoiceSet(multipleChoiceSet);
    }
    public List<QuestionOption> getAllQuestionOptionsByMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        return multipleChoiceQuestionOptionRepository.findByMultipleChoiceQuestion(multipleChoiceQuestion);
    }


    public List<MultipleChoiceSet> getAllUsersMultipleChoiceSet(User userDeserialized) {
        return multipleChoiceSetRepository.findByUserId(userDeserialized.getId());
    }
}
