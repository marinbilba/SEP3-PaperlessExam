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
public class Question implements Serializable {
    @Id
    private String question;
    @Id
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
    public Question(String question, double score) {
        this.question = question;
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
