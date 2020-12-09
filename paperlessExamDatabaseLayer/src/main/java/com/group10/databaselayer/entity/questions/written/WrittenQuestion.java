package com.group10.databaselayer.entity.questions.written;

import com.group10.databaselayer.annotations.hidden.Hidden;
import com.group10.databaselayer.entity.questions.Question;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The type Written question.
 */
@Entity
@IdClass(Question.class)
public class WrittenQuestion extends Question {

    @ManyToOne()
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
