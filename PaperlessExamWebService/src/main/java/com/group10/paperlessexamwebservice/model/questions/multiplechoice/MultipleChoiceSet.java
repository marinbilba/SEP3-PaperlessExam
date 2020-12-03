package com.group10.paperlessexamwebservice.model.questions.multiplechoice;

import com.group10.paperlessexamwebservice.model.questions.QuestionsSet;
import com.group10.paperlessexamwebservice.model.user.User;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * MultipleChoiceSet class. Class provides the functionality to add/remove
 * questions and questions options
 *
 * @author Marin Bilba
 * @version v1.0
 */
public class MultipleChoiceSet extends QuestionsSet {

    private List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();

    private User user;

    /**
     * Instantiates a new Multiple choice set.
     */
    public MultipleChoiceSet() {
    }

    /**
     * Instantiates a new Multiple choice set.
     *
     * @param title the title
     * @param topic the topic
     * @param user  the user
     */
    public MultipleChoiceSet(String title, String topic, User user) {
        super(title, topic);
        this.user=user;
    }


    /**
     * Add question.
     *
     * @param multipleChoiceQuestion the multiple choice question
     */
    public void addQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        this.multipleChoiceQuestions.add(multipleChoiceQuestion);
        multipleChoiceQuestion.setMultipleChoiceSet(this);
    }

    /**
     * Remove question.
     *
     * @param multipleChoiceQuestions the multiple choice questions
     */
    public void removeQuestion(MultipleChoiceQuestion multipleChoiceQuestions) {
        this.multipleChoiceQuestions.remove(multipleChoiceQuestions);
        multipleChoiceQuestions.setMultipleChoiceSet(null);
    }

    /**
     * Add question option.
     *
     * @param multipleChoiceQuestion the multiple choice question
     * @param questionOption         the question option
     */
    public void addQuestionOption(MultipleChoiceQuestion multipleChoiceQuestion, QuestionOption questionOption) {
        for (var question : this.multipleChoiceQuestions) {
            if (question.equals(multipleChoiceQuestion)) {
                multipleChoiceQuestion.addQuestionOption(questionOption);
            }
        }
    }

    public String getTitle() {
        return super.getTitle();
    }
    public String getTopic() {
        return super.getTopic();
    }

    /**
     * Remove question option.
     *
     * @param multipleChoiceQuestion the multiple choice question
     * @param questionOption         the question option
     */
    public void removeQuestionOption(MultipleChoiceQuestion multipleChoiceQuestion, QuestionOption questionOption) {
        for (var question : this.multipleChoiceQuestions) {
            if (question.equals(multipleChoiceQuestion)) {
                multipleChoiceQuestion.removeQuestionOption(questionOption);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) return false;
        else {
            MultipleChoiceSet other = (MultipleChoiceSet) o;
            return multipleChoiceQuestions.equals(other.multipleChoiceQuestions)&&user.equals(other.user);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), multipleChoiceQuestions);
    }
}
