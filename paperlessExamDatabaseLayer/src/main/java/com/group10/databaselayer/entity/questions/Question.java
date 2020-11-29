package com.group10.databaselayer.entity.questions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public  class Question implements Serializable {
    @Id
    private String question;
    @Id
    private double score;

    public Question() {
    }

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
