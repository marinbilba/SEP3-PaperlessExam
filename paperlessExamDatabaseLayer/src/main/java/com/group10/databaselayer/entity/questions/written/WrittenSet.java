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
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
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


    public String getTitle() {
       return super.getTitle();
    }
    public String getTopic() {
        return super.getTopic();
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
            return user.equals(other.user);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
