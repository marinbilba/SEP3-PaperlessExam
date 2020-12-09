package com.group10.databaselayer.entity.questions.written;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.group10.databaselayer.annotations.hidden.Hidden;
import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.user.User;
import com.sun.istack.NotNull;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.*;


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
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;
    @UpdateTimestamp
    private Date updatedTimestamp;
    @OneToMany(mappedBy = "writtenSet")
    transient private Set<WrittenQuestion> writtenQuestions = new HashSet<WrittenQuestion>();
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

    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getTitle() {
       return super.getTitle();
    }
    public String getTopic() {
        return super.getTopic();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<WrittenQuestion> getWrittenQuestions() {
        return writtenQuestions;
    }

    public void setWrittenQuestions(Set<WrittenQuestion> writtenQuestions) {
        this.writtenQuestions = writtenQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WrittenSet)) return false;
        if (!super.equals(o)) return false;
        WrittenSet that = (WrittenSet) o;
        return user.equals(that.user) &&
                updatedTimestamp.equals(that.updatedTimestamp) &&
                writtenQuestions.equals(that.writtenQuestions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, updatedTimestamp, writtenQuestions);
    }
}
