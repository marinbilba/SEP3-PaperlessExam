package com.group10.databaselayer.controller.questions;


import com.group10.databaselayer.entity.questions.Question;
import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.exception.questions.QuestionNotFound;
import com.group10.databaselayer.exception.questions.TitleOrTopicAreNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Class for multiple choice and written questions shared methods.
 */
@Component
public class WrittenMultipleChoiceQuestionsSharedMethods {


    /**
     * Check if title and topic are not null.
     *
     * @param questionsSet the questions set
     * @return the boolean true if the check title and topic are not null
     * @throws TitleOrTopicAreNull the title or topic are null
     */
    public boolean checkTitleTopicNotNull(QuestionsSet questionsSet) throws TitleOrTopicAreNull {
        String titleToQuery = questionsSet.getTitle();
        String topicToQuery = questionsSet.getTopic();
        if (!titleToQuery.isEmpty() && !topicToQuery.isEmpty()) {
          return true;
        } else {
            throw new TitleOrTopicAreNull("Title or topic are not set");
        }

    }


}
