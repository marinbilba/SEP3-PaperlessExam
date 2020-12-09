package com.group10.databaselayer.entity.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.Question;
import com.group10.databaselayer.annotations.hidden.Hidden;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity Multiple choice question.
 */
@Entity
@IdClass(Question.class)
public class MultipleChoiceQuestion extends Question {
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private MultipleChoiceSet multipleChoiceSet;



    /**
     * Instantiates a new Multiple choice question.
     */
    public MultipleChoiceQuestion() {

    }

    public MultipleChoiceQuestion( double score,String question, int questionNumber) {
        super(question, score, questionNumber);
    }

    /**
     * Instantiates a new Multiple choice question.
     *
     * @param question the question
     * @param score    the score
     */public MultipleChoiceQuestion(int questionNumber, String question, double score,MultipleChoiceSet multipleChoiceSet) {
        super(question, score, questionNumber);
        this.multipleChoiceSet=multipleChoiceSet;
    }

    /**
     * Gets multiple choice set.
     *
     * @return the multiple choice set
     */
    public MultipleChoiceSet getMultipleChoiceSet() {
        return multipleChoiceSet;
    }

    /**
     * Sets multiple choice set.
     *
     * @param multipleChoiceSet the multiple choice set
     */
    public void setMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) {
        this.multipleChoiceSet = multipleChoiceSet;
    }


    public String getQuestion() {
        return super.getQuestion();
    }

    /**
     * Get question score double.
     *
     * @return the double
     */
    public double getQuestionScore() {
        return super.getScore();
    }

    /**
     * Sets question.
     *
     * @param question the question
     */
    public void setQuestion(String question) {
        super.setQuestion(question);
    }

    /**
     * Sets score.
     *
     * @param score the question
     */
    public void setScore(double score) {
        super.setScore(score);
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
            MultipleChoiceQuestion other = (MultipleChoiceQuestion) o;
            return multipleChoiceSet.equals(other.multipleChoiceSet);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), multipleChoiceSet);
    }
}
