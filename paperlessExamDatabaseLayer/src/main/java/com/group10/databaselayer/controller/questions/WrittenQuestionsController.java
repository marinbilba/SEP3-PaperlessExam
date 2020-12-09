package com.group10.databaselayer.controller.questions;

import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.exception.questions.QuestionAlreadyExists;
import com.group10.databaselayer.exception.questions.QuestionNotFound;
import com.group10.databaselayer.exception.questions.TitleOrTopicAreNull;
import com.group10.databaselayer.exception.questions.QuestionSetNotFound;
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
    public WrittenQuestionsController(IWrittenSetRepository writtenSetRepository, IWrittenQuestionRepository writtenQuestionRepository
            ) {
        this.writtenSetRepository = writtenSetRepository;
        this.writtenQuestionRepository = writtenQuestionRepository;

    }

    /**
     * Finds if the given written set is defined in the database. The written set must contain both title
     * and topic of the questions set{@link }.
     *
     * @param writtenSet written set that should be queried
     * @return boolean value true if the set was found and false if it was not.
     * @throws TitleOrTopicAreNull in case the title or topic are empty
     */
    public boolean existsTitleAndTopicWrittenSet(WrittenSet writtenSet) throws TitleOrTopicAreNull {
        Optional<WrittenSet> queriedWrittenSet = Optional.empty();
        if (checkTitleTopicNotNull(writtenSet)) {
            queriedWrittenSet = writtenSetRepository.findById(writtenSet.getId());
        }
        return queriedWrittenSet.isPresent();
    }

    /**
     * Check if title and topic are not null.
     *
     * @param questionsSet the questions set
     * @return the boolean true if the check title and topic are not null
     * @throws TitleOrTopicAreNull the title or topic are null
     */
    public boolean checkTitleTopicNotNull(WrittenSet questionsSet) throws TitleOrTopicAreNull {
        String titleToQuery = questionsSet.getTitle();
        String topicToQuery = questionsSet.getTopic();
        if (!titleToQuery.isEmpty() && !topicToQuery.isEmpty()) {
            return true;
        } else {
            throw new TitleOrTopicAreNull("Title or topic are not set");
        }

    }
    /**
     * Create/update written set written set.
     *
     * @param writtenSet written set that should be created/updated
     * @return the written set
     */
    public WrittenSet createUpdateWrittenSet(WrittenSet writtenSet) {
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
     * @throws QuestionSetNotFound   in case the written set not found
     * @throws TitleOrTopicAreNull   the title or topic are null
     * @throws QuestionAlreadyExists the question already exists
     */
    public WrittenQuestion addQuestionToExistingWrittenSet(WrittenSet questionsSet, WrittenQuestion writtenQuestion) throws QuestionSetNotFound, TitleOrTopicAreNull, QuestionAlreadyExists {
// Check if the passed written set exists
        boolean questionNotFound = false;
        Optional<WrittenSet> tempQueriedWrittenSet = writtenSetRepository.findById(questionsSet.getId());
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


        } else throw new QuestionSetNotFound("Written set with given title and topic was not found");
    }

    /**
     * Find questions in written set. The written set must contain both title
     * and topic of the questions set
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
     * @throws QuestionSetNotFound in case the written set not found
     */
    public String removeWrittenSet(WrittenSet writtenSet) throws TitleOrTopicAreNull, QuestionSetNotFound {
        if (existsTitleAndTopicWrittenSet(writtenSet)) {
            writtenSetRepository.delete(writtenSet);
            return "Question set " + writtenSet.getTitle() + " was REMOVED";
        }
        throw new QuestionSetNotFound("Written set with given title and topic was not found");
    }



    /**
     * Find question in written set. Method checks if the passed WrittenSet exists, otherwise exception is thrown.
     * The list of written question are retrieved from the database based on the passed written set. The passed
     * written question is searched in the list and if it is found it will be returned.
     *
     * @param writtenSet      the written set
     * @param writtenQuestion the written question
     * @return the written question
     * @throws TitleOrTopicAreNull in case the title or topic are null
     * @throws QuestionNotFound    in case the question not found
     * @throws QuestionSetNotFound in case the written set not found
     */
    public WrittenQuestion findQuestionInWrittenSet(WrittenSet writtenSet, WrittenQuestion writtenQuestion) throws TitleOrTopicAreNull, QuestionNotFound, QuestionSetNotFound {
        if (existsTitleAndTopicWrittenSet(writtenSet)) {

            List<WrittenQuestion> fetchedWrittenQuestionsList = writtenQuestionRepository.findByWrittenSet(writtenSet);
            WrittenQuestion foundWrittenQuestion = null;
            for (var question : fetchedWrittenQuestionsList) {
                if (question.getQuestion().equals(writtenQuestion.getQuestion())
                        && question.getScore() == writtenQuestion.getScore()) {
                    foundWrittenQuestion = writtenQuestion;
                }
            }
            if (foundWrittenQuestion != null) {
                return foundWrittenQuestion;
            } else throw new QuestionNotFound("Question was not found");
        }
        throw new QuestionSetNotFound("Written set with given title and topic was not found");
    }

    public WrittenSet getWrittenSet(WrittenSet writtenSet) {
        return writtenSetRepository.findByTitleAndTopicAndUserId(writtenSet.getTitle(), writtenSet.getTopic(), writtenSet.getUser().getId());

    }

    public WrittenQuestion createUpdateWrittenSetQuestion(WrittenQuestion writtenSetQuestion) {
        return writtenQuestionRepository.save(writtenSetQuestion);
    }

    public WrittenQuestion getWrittenSetQuestion(WrittenQuestion writtenSetQuestion) {
        WrittenSet writtenSet=writtenSetQuestion.getWrittenSet();
        return writtenQuestionRepository.findByWrittenSetTopicAndWrittenSetTitleAndWrittenSetIdAndQuestionNumberAndQuestionAndScore(writtenSet.getTopic(),writtenSet.getTitle(),writtenSet.getId(),writtenSetQuestion.getQuestionNumber(),writtenSetQuestion.getQuestion(),writtenSetQuestion.getScore());
    }
    public List<WrittenSet> getAllWrittenSet(User userDeserialized) {
        return writtenSetRepository.findByUserId(userDeserialized.getId());
    }
}
