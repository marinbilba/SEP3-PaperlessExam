package com.group10.databaselayer.controller;

import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.exception.questions.QuestionAlreadyExists;
import com.group10.databaselayer.exception.questions.QuestionNotFound;
import com.group10.databaselayer.exception.questions.TitleOrTopicAreNull;
import com.group10.databaselayer.exception.questions.WrittenSetNotFound;
import com.group10.databaselayer.repositories.questions.written.IWrittenQuestionRepository;
import com.group10.databaselayer.repositories.questions.written.IWrittenSetRepository;
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
public class WrittenQuestionsController {

    private IWrittenSetRepository writtenSetRepository;
    private IWrittenQuestionRepository writtenQuestionRepository;

    /**
     * Instantiates a new Written questions controller using Spring Boot dependency injection.
     *
     * @param writtenSetRepository      the written set repository
     * @param writtenQuestionRepository the written question repository
     */
    @Autowired
    public WrittenQuestionsController(IWrittenSetRepository writtenSetRepository, IWrittenQuestionRepository writtenQuestionRepository) {
        this.writtenSetRepository = writtenSetRepository;
        this.writtenQuestionRepository = writtenQuestionRepository;
    }

    /**
     * Finds if the given written set is defined in the database. The written set must contain both title
     * and topic of the questions set{@link QuestionsSet}.
     *
     * @param writtenSet written set that should be queried
     * @return boolean value true if the set was found and false if it was not.
     * @throws TitleOrTopicAreNull in case the title or topic are empty
     */
    public boolean existsTitleAndTopicWrittenSet(WrittenSet writtenSet) throws TitleOrTopicAreNull {
        QuestionsSet tempQuestionsSet = null;
        String titleToQuery = writtenSet.getTitle();
        String topicToQuery = writtenSet.getTopic();
//        Form the question set that should be queried
        if (!titleToQuery.isEmpty() && !topicToQuery.isEmpty()) {
            tempQuestionsSet = new QuestionsSet(titleToQuery, topicToQuery);
        } else {
            throw new TitleOrTopicAreNull("Title or topic are not set");
        }
        Optional<WrittenSet> queriedWrittenSet = writtenSetRepository.findById(tempQuestionsSet);
        return queriedWrittenSet.isPresent();
    }


    /**
     * Create/update written set written set.
     *
     * @param writtenSet written set that should be created/updated
     * @return the written set
     */
    public WrittenSet createWrittenSet(WrittenSet writtenSet) {
        return writtenSetRepository.save(writtenSet);
    }


    /**
     * Add question to existing written set. Method checks if the passed WrittenSet exists, otherwise exception is thrown.
     * If set exists, the passed written question is check if such exists. If the passed question does not exist it will
     * be added to the database.
     *
     * @param questionsSet    the written set in which the question should be added
     * @param writtenQuestion the written question which should be added to the written set
     * @return the recently added question
     * @throws WrittenSetNotFound    in case the written set not found
     * @throws TitleOrTopicAreNull   the title or topic are null
     * @throws QuestionAlreadyExists the question already exists
     */
    public WrittenQuestion addQuestionToExistingWrittenSet(WrittenSet questionsSet, WrittenQuestion writtenQuestion) throws WrittenSetNotFound, TitleOrTopicAreNull, QuestionAlreadyExists {
// Check the passed written set
        boolean questionNotFound = false;
        Optional<WrittenSet> tempQueriedWrittenSet = writtenSetRepository.findById(questionsSet);
        if (tempQueriedWrittenSet.isPresent()) {
            WrittenSet queriedWrittenSet = tempQueriedWrittenSet.get();
// Check if question already exists
            try {
                findQuestionInWrittenSet(questionsSet, writtenQuestion);
            } catch (QuestionNotFound e) {
                questionNotFound = true;
            }

            if (questionNotFound) {
                writtenQuestion.setWrittenSet(queriedWrittenSet);
                return writtenQuestionRepository.save(writtenQuestion);
            } else
                throw new QuestionAlreadyExists("Question '" + writtenQuestion.getQuestion() + "' already exists");


        } else throw new WrittenSetNotFound("Written set with given title and topic was not found");
    }

    /**
     * Find questions in written set. The written set must contain both title
     * and topic of the questions set{@link QuestionsSet}.
     *
     * @param writtenSet the written set that should be queried
     * @return the list of written questions retrieved from the written set
     */
    public List<WrittenQuestion> getQuestionsByWrittenSet(WrittenSet writtenSet) {
        return writtenQuestionRepository.findByWrittenSet(writtenSet);
    }

    /**
     * Remove written set. Method checks if the passed WrittenSet exists, otherwise an exception will be thrown.
     *
     * @param writtenSet the written set that should be queried
     * @return a confirmation String containing title of the removed Written Set
     * @throws TitleOrTopicAreNull in case the title or topic are null
     * @throws WrittenSetNotFound  in case the written set not found
     */
    public String removeWrittenSet(WrittenSet writtenSet) throws TitleOrTopicAreNull, WrittenSetNotFound {
        if (existsTitleAndTopicWrittenSet(writtenSet)) {
            writtenSetRepository.delete(writtenSet);
            return "Question set " + writtenSet.getTitle() + " was REMOVED";
        }
        throw new WrittenSetNotFound("Written set with given title and topic was not found");
    }

    /**
     * Remove a question from the written set. Method checks if the passed WrittenSet exists, otherwise exception is thrown.
     * The passed written question will be check if it exists. If it does exist it will be removed from the written set
     *
     * @param writtenSet     the written set that should be queried
     * @param writtenQuestion the written question that should be deleted
     * @return a confirmation String containing question title of the removed question
     * @throws TitleOrTopicAreNull in case the title or topic are null
     * @throws WrittenSetNotFound  in case the written set not found
     * @throws QuestionNotFound    in case the question not found
     */
    public String removeQuestionWrittenSet(WrittenSet writtenSet, WrittenQuestion writtenQuestion) throws TitleOrTopicAreNull, WrittenSetNotFound, QuestionNotFound {
        if (existsTitleAndTopicWrittenSet(writtenSet)) {
            findQuestionInWrittenSet(writtenSet, writtenQuestion);
            writtenSet.removeQuestion(writtenQuestion);
            writtenSetRepository.save(writtenSet);
            return "Question " + writtenQuestion.getQuestion() + " was REMOVED";
        }
        throw new WrittenSetNotFound("Written set with given title and topic was not found");
    }

    /**
     * Find question in written set. Method checks if the passed WrittenSet exists, otherwise exception is thrown.
     * The list of written question are retrieved from the database based on the passed written set. The passed
     * written question is searched in the list and if it is found it will be deleted.
     *
     * @param writtenSet      the written set
     * @param writtenQuestion the written question
     * @return the written question
     * @throws TitleOrTopicAreNull in case the title or topic are null
     * @throws QuestionNotFound    in case the question not found
     * @throws WrittenSetNotFound  in case the written set not found
     */
    public WrittenQuestion findQuestionInWrittenSet(WrittenSet writtenSet, WrittenQuestion writtenQuestion) throws TitleOrTopicAreNull, QuestionNotFound, WrittenSetNotFound {
        if (existsTitleAndTopicWrittenSet(writtenSet)) {

            List<WrittenQuestion> fetchedWrittenQuestionsList = writtenQuestionRepository.findByWrittenSet(writtenSet);
            WrittenQuestion foundWrittenQuestion = null;
            for (var question : fetchedWrittenQuestionsList
            ) {
                if (question.getQuestion().equals(writtenQuestion.getQuestion()) && question.getQuestionScore() == writtenQuestion.getQuestionScore()) {
                    foundWrittenQuestion = writtenQuestion;
                }
            }
            if (foundWrittenQuestion != null) {
                return foundWrittenQuestion;
            } else throw new QuestionNotFound("Question was not found");
        }
        throw new WrittenSetNotFound("Written set with given title and topic was not found");
    }
}
