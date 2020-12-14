package com.group10.databaselayer.entity.questions.multiplechoice;


import com.group10.databaselayer.annotations.hidden.Hidden;
import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.studentsubmitpaper.StudentSubmitExaminationPaper;
import com.group10.databaselayer.entity.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

/**
 * MultipleChoiceSet entity. Class provides the functionality to add/remove
 * questions and questions options as a requirement
 * of the bidirectional one-to-many mapping between
 * MultipleChoiceSet entity and MultipleChoiceQuestion {@link MultipleChoiceQuestion}
 *
 * @author Marin Bilba
 * @version v1.0
 */
@Entity


public class MultipleChoiceSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String topic;

    @UpdateTimestamp
    private Date updatedTimestamp;

    @ManyToOne()
    @JoinColumn(name = "user_id", updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @Hidden
    @ManyToMany(mappedBy = "multipleChoiceSets")
    private List<ExaminationEvent> examinationEvents = new ArrayList<>();

    @Hidden
    @ManyToMany(mappedBy = "submitMultipleChoiceSets")
    private List<StudentSubmitExaminationPaper> studentSubmitExaminationPapers = new ArrayList<>();



    /**
     * Instantiates a new Multiple choice set.
     */
    public MultipleChoiceSet() {
    }

    public MultipleChoiceSet(String title, String topic, User user) {
        this.title = title;
        this.topic = topic;
        this.user = user;
    }

    public List<ExaminationEvent> getExaminationEvents() {
        return examinationEvents;
    }

    public void setExaminationEvents(List<ExaminationEvent> examinationEvents) {
        this.examinationEvents = examinationEvents;
    }

    public List<StudentSubmitExaminationPaper> getStudentSubmitExaminationPapers() {
        return studentSubmitExaminationPapers;
    }

    public void setStudentSubmitExaminationPapers(List<StudentSubmitExaminationPaper> studentSubmitExaminationPapers) {
        this.studentSubmitExaminationPapers = studentSubmitExaminationPapers;
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


//    public List<ExaminationEvent> getExaminationEvents() {
//        return examinationEvents;
//    }
//
//    public void setExaminationEvents(List<ExaminationEvent> examinationEvents) {
//        this.examinationEvents = examinationEvents;
//    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultipleChoiceSet)) return false;
        MultipleChoiceSet that = (MultipleChoiceSet) o;
        return id.equals(that.id) &&
                title.equals(that.title) &&
                topic.equals(that.topic) &&
                updatedTimestamp.equals(that.updatedTimestamp) &&
                user.equals(that.user) &&
                examinationEvents.equals(that.examinationEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, topic, updatedTimestamp, user, examinationEvents);
    }
}
