package com.group10.databaselayer.entity.questions.written;

import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.entity.user.User;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;


/**
 * WrittenSet entity. Class provides the functionality to add/remove questions as a requirement
 * of the bidirectional one-to-many mapping between WrittenSet entity and WrittenQuestion {@link WrittenQuestion}
 *
 * @author Marin Bilba
 * @version v1.0
 */
@Entity

public class WrittenSet {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String topic;

    @ManyToOne()
    @JoinColumn(name = "user_id", updatable = false)
    private User user;
    @UpdateTimestamp
    private Date updatedTimestamp;

    @ManyToMany(mappedBy = "writtenSets")
    private Set<ExaminationEvent> examinationEvents = new HashSet<>();



    /**
     * Instantiates a new Written set.
     */
    public WrittenSet() {
    }

    public WrittenSet(String title, String topic, User user) {
        this.title = title;
        this.topic = topic;
        this.user = user;
    }

    public Set<ExaminationEvent> getExaminationEvents() {
        return examinationEvents;
    }

    public void setExaminationEvents(Set<ExaminationEvent> examinationEvents) {
        this.examinationEvents = examinationEvents;
    }

    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WrittenSet)) return false;
        WrittenSet that = (WrittenSet) o;
        return id.equals(that.id) &&
                title.equals(that.title) &&
                topic.equals(that.topic) &&
                user.equals(that.user) &&
                updatedTimestamp.equals(that.updatedTimestamp) &&
                examinationEvents.equals(that.examinationEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, topic, user, updatedTimestamp, examinationEvents);
    }
}
