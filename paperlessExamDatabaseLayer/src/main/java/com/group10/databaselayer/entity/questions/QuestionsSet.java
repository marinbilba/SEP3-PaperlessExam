package com.group10.databaselayer.entity.questions;

import com.group10.databaselayer.entity.user.Role;
import com.group10.databaselayer.entity.user.User;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Mapped superclass for question sets. Class implements Serializable, because
 * that’s a JPA requirement since the identifier might be use as the key for a second-level cache entry.
 *
 * @author Marin Bilba
 * @version v1.0
 */
@MappedSuperclass
@Embeddable
public class QuestionsSet implements Serializable {
    @Id
    private String title;
    @Id
    private String topic;
    @OneToOne
    @NotNull
    @JoinColumn(name="fk_user_id")
    private User user;

    /**
     * Instantiates a new Questions set.
     */
    public QuestionsSet() {
    }

    /**
     * Instantiates a new Questions set.
     *
     * @param title the title
     * @param topic the topic
     */
    public QuestionsSet(String title, String topic,User user) {
        this.title = title;
        this.topic = topic;
        this.user=user;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets topic.
     *
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Sets topic.
     *
     * @param topic the topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuestionsSet)) {
            return false;
        }
        QuestionsSet other = (QuestionsSet) o;
        return title.equals(other.title) && topic.equals(other.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, topic);
    }
}
