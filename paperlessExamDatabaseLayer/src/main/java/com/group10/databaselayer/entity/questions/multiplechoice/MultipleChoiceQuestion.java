package com.group10.databaselayer.entity.questions.multiplechoice;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entity Multiple choice question.
 */
@Entity

public class MultipleChoiceQuestion  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int questionNumber;

    private String question;

    private double score;
    @ManyToOne()
    private MultipleChoiceSet multipleChoiceSet;

    /**
     * Instantiates a new Multiple choice question.
     */
    public MultipleChoiceQuestion() {

    }

    public MultipleChoiceQuestion(int questionNumber, String question, double score, MultipleChoiceSet multipleChoiceSet) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.score = score;
        this.multipleChoiceSet = multipleChoiceSet;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultipleChoiceQuestion)) return false;
        MultipleChoiceQuestion that = (MultipleChoiceQuestion) o;
        return questionNumber == that.questionNumber &&
                Double.compare(that.score, score) == 0 &&
                id.equals(that.id) &&
                question.equals(that.question) &&
                multipleChoiceSet.equals(that.multipleChoiceSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionNumber, question, score, multipleChoiceSet);
    }
}
