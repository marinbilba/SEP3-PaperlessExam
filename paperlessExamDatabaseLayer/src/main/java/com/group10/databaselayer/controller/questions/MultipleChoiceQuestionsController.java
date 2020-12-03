package com.group10.databaselayer.controller.questions;

import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
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
            queriedMultipleChoiceSet = multipleChoiceSetRepository.findById(multipleChoiceSet);
        }
        return queriedMultipleChoiceSet.isPresent();
    }

    /**
     * Add question to existing multiple choice set. Method checks if the passed MultipleChoiceSet exists, otherwise exception is thrown.
     *     If set exists, the passed MultipleChoice question is check if such exists. If the passed question does not exist it will
     *     be added to the database.
     *
     * @param multipleChoiceSet      the multiple choice set
     * @param multipleChoiceQuestion the multiple choice question
     * @return the multiple choice question
     * @throws QuestionSetNotFound the question set not found
     */
    public MultipleChoiceQuestion addQuestionToExistingMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet,
                                                                         MultipleChoiceQuestion multipleChoiceQuestion) throws QuestionSetNotFound, TitleOrTopicAreNull, QuestionAlreadyExists {
        // Check if the passed multipleChoiceSet exists
        boolean questionNotFound = false;
        Optional<MultipleChoiceSet> tempQueriedMultipleChoiceSet = multipleChoiceSetRepository.findById(multipleChoiceSet);

        if (tempQueriedMultipleChoiceSet.isPresent()) {
MultipleChoiceSet queriedMultipleChoiceSet=tempQueriedMultipleChoiceSet.get();
// Check if question already exists
            try {
                findQuestionInMultipleChoiceSet(multipleChoiceSet,multipleChoiceQuestion);
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
     *  otherwise exception is thrown. The list of multiple choice question are retrieved from the
     *  database based on the passed  multiple choice set.
     *  The passed written question is searched in the list and if it is found it will be returned.
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
                if (question.getQuestion().equals(multipleChoiceQuestion.getQuestion())&&
                        question.getQuestionScore()==question.getQuestionScore()){
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
     * Remove a question from the written set. Method checks if the passed WrittenSet exists, otherwise exception is thrown.
     * The passed written question will be check if it exists. If it does exist it will be removed from the written set
     *
     * @param multipleChoiceSet      the written set that should be queried
     * @param multipleChoiceQuestion the written question that should be deleted
     * @return a confirmation String containing question title of the removed question
     * @throws TitleOrTopicAreNull in case the title or topic are null
     * @throws QuestionSetNotFound in case the written set not found
     * @throws QuestionNotFound    in case the question not found
     */
    public String removeQuestionMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet, MultipleChoiceQuestion multipleChoiceQuestion) throws TitleOrTopicAreNull, QuestionSetNotFound, QuestionNotFound {
        if (existsMultipleChoiceSet(multipleChoiceSet)) {
            findQuestionInMultipleChoiceSet(multipleChoiceSet, multipleChoiceQuestion);
            multipleChoiceSet.removeQuestion(multipleChoiceQuestion);
            multipleChoiceSetRepository.save(multipleChoiceSet);
            return "Question " + multipleChoiceQuestion.getQuestion() + " was REMOVED";
        }
        throw new QuestionSetNotFound("Multiple choice set with given title and topic was not found");
    }

}
