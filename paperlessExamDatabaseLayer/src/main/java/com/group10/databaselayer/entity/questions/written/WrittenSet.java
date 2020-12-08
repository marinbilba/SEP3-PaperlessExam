package com.group10.databaselayer.entity.questions.written;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.group10.databaselayer.annotations.hidden.Hidden;
import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.user.User;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * WrittenSet entity. Class provides the functionality to add/remove questions as a requirement
 * of the bidirectional one-to-many mapping between WrittenSet entity and WrittenQuestion {@link WrittenQuestion}
 *
 * @author Marin Bilba
 * @version v1.0
 */
@Entity
@IdClass(QuestionsSet.class)
public class WrittenSet extends QuestionsSet {
    @OneToMany(
            mappedBy = "writtenSet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WrittenQuestion> writtenQuestions = new ArrayList<WrittenQuestion>();
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", updatable = false)
    @Hidden
    private User user;

    /**
     * Instantiates a new Written set.
     */
    public WrittenSet() {
    }

    /**
     * Instantiates a new Written set.
     *
     * @param title the title
     * @param topic the topic
     */
    public WrittenSet(String title, String topic,User user) {
        super(title, topic);
        this.user=user;
    }

    /**
     * Add question. Method synchronizes both sides of the bidirectional association between
     * this entity {@link WrittenSet} and WrittenSet {@link WrittenSet}
     * in order to avoid very subtle state propagation issues
     *
     * @param writtenQuestion the written question
     */
    public void addQuestion(WrittenQuestion writtenQuestion) {
        this.writtenQuestions.add(writtenQuestion);
        writtenQuestion.setWrittenSet(this);
    }

    /**
     * Remove question. Method synchronizes both sides of the bidirectional association between
     * this entity {@link WrittenSet} and WrittenSet {@link WrittenSet}
     * in order to avoid very subtle state propagation issues
     *
     * @param writtenQuestion the written question
     */
    public void removeQuestion(WrittenQuestion writtenQuestion) {
        this.writtenQuestions.remove(writtenQuestion);
        writtenQuestion.setWrittenSet(null);
    }

    public String getTitle() {
       return super.getTitle();
    }
    public String getTopic() {
        return super.getTopic();
    }



    public List<WrittenQuestion> getWrittenQuestions() {
        return writtenQuestions;
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
            WrittenSet other = (WrittenSet) o;
            return writtenQuestions.equals(other.writtenQuestions)&&user.equals(other.user);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writtenQuestions);
    }
}
