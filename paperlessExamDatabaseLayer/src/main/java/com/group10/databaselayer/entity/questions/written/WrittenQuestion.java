package com.group10.databaselayer.entity.questions.written;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.Expose;
import com.group10.databaselayer.entity.questions.Question;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@IdClass(Question.class)
public class WrittenQuestion extends Question {

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    @Hidden
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
    public WrittenQuestion(String question, double score,int questionNumber) {
        super(question, score,questionNumber);

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
public String getQuestion(){
        return super.getQuestion();
}

    /**
     * Get question score double.
     *
     * @return the double
     */
    public double getQuestionScore(){
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
        if (o == null) { return false; }
        if (getClass() != o.getClass()) { return false; }
        if (! super.equals(o)) return false;
        else {
            WrittenQuestion other=(WrittenQuestion) o;
       return writtenSet.equals(other.writtenSet);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writtenSet);
    }
}
