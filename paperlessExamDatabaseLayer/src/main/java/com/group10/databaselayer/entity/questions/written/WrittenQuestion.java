package com.group10.databaselayer.entity.questions.written;

import com.group10.databaselayer.annotations.hidden.Hidden;
import com.group10.databaselayer.entity.questions.Question;

import javax.persistence.*;
import java.util.Objects;


@Entity
@IdClass(Question.class)
public class WrittenQuestion extends Question {

    @ManyToOne()
//    @JoinColumns({
//            @JoinColumn(name = "written_set_id"),
//            @JoinColumn(name = "written_set_title"),
//            @JoinColumn(name = "written_set_topic"),
//    })
    private WrittenSet writtenSet;


    /**
     * Instantiates a new Written question.
     */
    public WrittenQuestion() {

    }

    /**
     * Instantiates a new Written question.
     *
     * @param question the question
     * @param score    the score
     */
    public WrittenQuestion(String question, double score, int questionNumber) {
        super(question, score, questionNumber);

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
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) return false;
        else {
            WrittenQuestion other = (WrittenQuestion) o;
            return writtenSet.equals(other.writtenSet);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writtenSet);
    }
}
