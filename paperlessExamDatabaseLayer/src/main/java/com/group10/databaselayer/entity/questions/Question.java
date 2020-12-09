package com.group10.databaselayer.entity.questions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Mapped superclass for questions. Class implements Serializable, because
 * that’s a JPA requirement since the identifier might be use as the key for a second-level cache entry.
 *
 * @author Marin Bilba
 * @version v1.0
 */
@MappedSuperclass
@Embeddable
public class Question implements Serializable {
    @Column(name = "questionNumber")
    private int questionNumber;
    @Id
    private String question;
    @Id
    private double score;

    /**
     * Instantiates a new Question.
     */
    public Question() {
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    /**
     * Instantiates a new Question.
     *
     * @param question the question
     * @param score    the score
     */
    public Question(String question, double score, int questionNumber) {
        this.question = question;
        this.score = score;
        this.questionNumber = questionNumber;
    }

    /**
     * Gets question.
     *
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets question.
     *
     * @param question the question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Question)) {
            return false;
        }
        Question other = (Question) o;
        return
                question.equals(other.question) && score == other.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, score);
    }
}
