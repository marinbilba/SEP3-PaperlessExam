package com.group10.databaselayer.entity.questions.written;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;


/**
 * The type Written question.
 */
@Entity

public class WrittenQuestion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private int questionNumber;

    private String question;
    private String questionAnswer;
    private double score;
    private boolean submittedQuestion;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(FetchMode.SELECT)
    private WrittenSet writtenSet;

    /**
     * Instantiates a new Written question.
     */
    public WrittenQuestion() {

    }

    /**
     * Instantiates a new Written question.
     *
     * @param questionNumber the question number
     * @param question       the question
     * @param score          the score
     * @param writtenSet     the written set
     */
    public WrittenQuestion(int questionNumber, String question, double score, WrittenSet writtenSet) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.score = score;
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
        this.questionNumber = questionNumber;
        this.question = question;
        this.questionAnswer = questionAnswer;
        this.score = score;
        this.writtenSet = writtenSet;
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
     * Gets question answer.
     *
     * @return the question answer
     */
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    /**
     * Sets question answer.
     *
     * @param questionAnswer the question answer
     */
    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets question number.
     *
     * @return the question number
     */
    public int getQuestionNumber() {
        return questionNumber;
    }

    /**
     * Sets question number.
     *
     * @param questionNumber the question number
     */
    public void setQuestionNumber(int questionNumber) {
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
        if (this == o) return true;
        if (!(o instanceof WrittenQuestion)) return false;
        WrittenQuestion that = (WrittenQuestion) o;
        return questionNumber == that.questionNumber &&
                Double.compare(that.score, score) == 0 &&
                id.equals(that.id) &&
                question.equals(that.question) &&
                writtenSet.equals(that.writtenSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionNumber, question, score, writtenSet);
    }
}
