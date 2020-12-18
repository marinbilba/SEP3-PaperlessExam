package com.group10.paperlessexamwebservice.model.questions.written;


import com.group10.paperlessexamwebservice.annotations.hidden.Hidden;
import com.group10.paperlessexamwebservice.model.questions.Question;

import javax.persistence.*;
import java.util.Objects;


/**
 * Written question class.
 */
public class WrittenQuestion extends Question {

    private WrittenSet writtenSet;
    private boolean submittedQuestion;
    private double studentScore;

    /**
     * Instantiates a new Written question.
     */
    public WrittenQuestion() {

    }


    /**
     * Instantiates a new Written question.
     *
     * @param question       the question
     * @param score          the score
     * @param questionNumber the question number
     * @param writtenSet     the written set
     */
    public WrittenQuestion(String question, double score, int questionNumber, WrittenSet writtenSet) {
        super(question, score, questionNumber);
        this.writtenSet = writtenSet;
    }

    /**
     * Instantiates a new Written question.
     *
     * @param questionNumber the question number
     * @param question       the question
     * @param questionAnswer the question answer
     * @param score          the score
     * @param writtenSet     the written set
     */
    public WrittenQuestion(int questionNumber, String question, String questionAnswer, double score, WrittenSet writtenSet) {
        super(questionNumber, question, questionAnswer, score);
        this.writtenSet = writtenSet;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        super.setId(id);
    }

    public double getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(double studentScore) {
        this.studentScore = studentScore;
    }

    public Long getId() {
        return super.getId();
    }

    public String getQuestionAnswer() {
        return super.getQuestionAnswer();
    }

    /**
     * Is submitted question boolean.
     *
     * @return the boolean
     */
    public boolean isSubmittedQuestion() {
        return submittedQuestion;
    }

    /**
     * Sets submitted question.
     *
     * @param submittedQuestion the submitted question
     */
    public void setSubmittedQuestion(boolean submittedQuestion) {
        this.submittedQuestion = submittedQuestion;
    }

    /**
     * Get question answer.
     *
     * @param answer the answer
     */
    public void getQuestionAnswer(String answer) {
        super.setQuestion(answer);
    }

    /**
     * Gets written set.
     *
     * @return the written set
     */
    public WrittenSet getWrittenSet() {
        return writtenSet;
    }

    /**
     * Sets written set.
     *
     * @param writtenSet the written set
     */
    public void setWrittenSet(WrittenSet writtenSet) {
        this.writtenSet = writtenSet;
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
        if (this == o) return true;
        if (!(o instanceof WrittenQuestion)) return false;
        if (!super.equals(o)) return false;
        WrittenQuestion that = (WrittenQuestion) o;
        return writtenSet.equals(that.writtenSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writtenSet);
    }
}
