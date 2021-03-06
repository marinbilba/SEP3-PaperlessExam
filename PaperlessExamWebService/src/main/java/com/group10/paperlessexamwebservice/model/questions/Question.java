package com.group10.paperlessexamwebservice.model.questions;

import java.io.Serializable;
import java.util.Objects;

/**
 * Mapped superclass for questions.
 *
 * @author Marin Bilba
 * @version v1.0
 */

public class Question implements Serializable {
    private Long id;
    private int questionNumber;
    private String question;
    private String questionAnswer;
    private double score;

    /**
     * Instantiates a new Question.
     */
    public Question() {
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

    public Question(int questionNumber, String question, String questionAnswer, double score) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.questionAnswer = questionAnswer;
        this.score = score;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
