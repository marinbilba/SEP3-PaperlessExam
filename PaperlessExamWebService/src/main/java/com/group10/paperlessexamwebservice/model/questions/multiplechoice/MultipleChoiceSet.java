package com.group10.paperlessexamwebservice.model.questions.multiplechoice;

import com.group10.paperlessexamwebservice.annotations.hidden.Hidden;
import com.group10.paperlessexamwebservice.model.questions.QuestionsSet;
import com.group10.paperlessexamwebservice.model.user.User;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * MultipleChoiceSet class. Class provides the functionality to add/remove
 * questions and questions options
 *
 * @author Marin Bilba
 * @version v1.0
 */
public class MultipleChoiceSet extends QuestionsSet {

    private User user;
private Date updatedTimestamp;
    /**
     * Instantiates a new Multiple choice set.
     */
    public MultipleChoiceSet() {
    }

    /**
     * Instantiates a new Multiple choice set.
     *
     * @param title the title
     * @param topic the topic
     * @param user  the user
     */
    public MultipleChoiceSet(String title, String topic, User user) {
        super(title, topic);
        this.user=user;
    }

    /**
     * Instantiates a new Multiple choice set.
     *
     * @param title the title
     * @param topic the topic
     */
    public MultipleChoiceSet(String title, String topic) {
        super(title, topic);
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
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
            MultipleChoiceSet other = (MultipleChoiceSet) o;
            return user.equals(other.user);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
