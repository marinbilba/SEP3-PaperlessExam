package com.group10.databaselayer.entity.questions.written;

import com.group10.databaselayer.annotations.hidden.Hidden;
import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.entity.studentsubmitpaper.StudentSubmitExaminationPaper;
import com.group10.databaselayer.entity.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String topic;

    @ManyToOne()
    @JoinColumn(name = "user_id", updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @UpdateTimestamp
    private Date updatedTimestamp;
    private boolean submittedWrittenSet;
@Hidden
    @ManyToMany(mappedBy = "writtenSets")
    private List<ExaminationEvent> examinationEvents = new ArrayList<>();

    @Hidden
    @ManyToMany(mappedBy = "submitMultipleChoiceSets")
    private List<StudentSubmitExaminationPaper> submitMultipleChoiceSets = new ArrayList<>();


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
     * @param user  the user
     */
    public WrittenSet(String title, String topic, User user) {
        this.title = title;
        this.topic = topic;
        this.user = user;
    }

    /**
     * Gets examination events.
     *
     * @return the examination events
     */
    public List<ExaminationEvent> getExaminationEvents() {
        return examinationEvents;
    }

    /**
     * Sets examination events.
     *
     * @param examinationEvents the examination events
     */
    public void setExaminationEvents(List<ExaminationEvent> examinationEvents) {
        this.examinationEvents = examinationEvents;
    }

    /**
     * Gets submit multiple choice sets.
     *
     * @return the submit multiple choice sets
     */
    public List<StudentSubmitExaminationPaper> getSubmitMultipleChoiceSets() {
        return submitMultipleChoiceSets;
    }

    /**
     * Is submitted written set boolean.
     *
     * @return the boolean
     */
    public boolean isSubmittedWrittenSet() {
        return submittedWrittenSet;
    }

    /**
     * Sets submitted written set.
     *
     * @param submittedWrittenSet the submitted written set
     */
    public void setSubmittedWrittenSet(boolean submittedWrittenSet) {
        this.submittedWrittenSet = submittedWrittenSet;
    }

    /**
     * Sets submit multiple choice sets.
     *
     * @param submitMultipleChoiceSets the submit multiple choice sets
     */
    public void setSubmitMultipleChoiceSets(List<StudentSubmitExaminationPaper> submitMultipleChoiceSets) {
        this.submitMultipleChoiceSets = submitMultipleChoiceSets;
    }

    /**
     * Gets updated timestamp.
     *
     * @return the updated timestamp
     */
    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    /**
     * Sets updated timestamp.
     *
     * @param updatedTimestamp the updated timestamp
     */
    public void setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
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
