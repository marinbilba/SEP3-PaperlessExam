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

    private double score;
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumns({
//            @JoinColumn(name = "multiple_choice_set_id"),
//            @JoinColumn(name = "multiple_choice_set_title"),
//            @JoinColumn(name = "multiple_choice_set_topic"),
//    })
    @Fetch(FetchMode.SELECT)
    private WrittenSet writtenSet;
    /**
     * Instantiates a new Written question.
     */
    public WrittenQuestion() {

    }

    public WrittenQuestion(int questionNumber, String question, double score, WrittenSet writtenSet) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.score = score;
        this.writtenSet = writtenSet;
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
