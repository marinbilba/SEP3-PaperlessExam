package com.group10.databaselayer.entity.questions.written;

import com.group10.databaselayer.entity.questions.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@IdClass(Question.class)
public class WrittenQuestion extends Question {

    @ManyToOne(fetch = FetchType.LAZY)
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
    public WrittenQuestion(String question, double score) {
        super(question, score);
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
