package com.group10.databaselayer.entity.examinationevent;

import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.*;

/**
 * The type Examination event.
 */
// Owner of many to many relation
@Entity
public class ExaminationEvent {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String examTitle;
private String examTimeDuration;
    @ManyToMany()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "examination_event_multiple_choice_sets",
            joinColumns = {@JoinColumn(name = "examination_event_id")},
            inverseJoinColumns = {@JoinColumn(name = "multiple_choice_set_id")})
    private List<MultipleChoiceSet> multipleChoiceSets = new ArrayList<>();


    @ManyToMany()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "examination_event_written_sets",
            joinColumns = {@JoinColumn(name = "examination_event_id")},
            inverseJoinColumns = {@JoinColumn(name = "written_set_id")})
    private List<WrittenSet> writtenSets = new ArrayList<>();

    @ManyToMany()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "examination_event_users_assigned",
            joinColumns = {@JoinColumn(name = "examination_event_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> usersAssigned = new ArrayList<>();

    private Date examDateAndTime;


    @CreationTimestamp
    private Date updatedTimestamp;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "created_by", updatable = false)
    private User createdBy;

    /**
     * Instantiates a new Examination event.
     */
    public ExaminationEvent() {
    }


    /**
     * Instantiates a new Examination event.
     *
     * @param examTitle          the exam title
     * @param examTimeDuration   the exam time duration
     * @param multipleChoiceSets the multiple choice sets
     * @param writtenSets        the written sets
     * @param usersAssigned      the users assigned
     * @param examDateAndTime    the exam date and time
     * @param createdBy          the created by
     */
    public ExaminationEvent(String examTitle, String examTimeDuration, List<MultipleChoiceSet> multipleChoiceSets, List<WrittenSet> writtenSets, List<User> usersAssigned, Date examDateAndTime, User createdBy) {
        this.examTitle = examTitle;
        this.examTimeDuration = examTimeDuration;
        this.multipleChoiceSets = multipleChoiceSets;
        this.writtenSets = writtenSets;
        this.usersAssigned = usersAssigned;
        this.examDateAndTime = examDateAndTime;
        this.createdBy = createdBy;
    }

    /**
     * Gets exam time duration.
     *
     * @return the exam time duration
     */
    public String getExamTimeDuration() {
        return examTimeDuration;
    }

    /**
     * Sets exam time duration.
     *
     * @param examTimeDuration the exam time duration
     */
    public void setExamTimeDuration(String examTimeDuration) {
        this.examTimeDuration = examTimeDuration;
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
     * Gets exam date and time.
     *
     * @return the exam date and time
     */
    public Date getExamDateAndTime() {
        return examDateAndTime;
    }

    /**
     * Sets exam date and time.
     *
     * @param examDateAndTime the exam date and time
     */
    public void setExamDateAndTime(Date examDateAndTime) {
        this.examDateAndTime = examDateAndTime;
    }

    /**
     * Gets exam title.
     *
     * @return the exam title
     */
    public String getExamTitle() {
        return examTitle;
    }

    /**
     * Sets exam title.
     *
     * @param examTitle the exam title
     */
    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    /**
     * Gets multiple choice sets.
     *
     * @return the multiple choice sets
     */
    public List<MultipleChoiceSet> getMultipleChoiceSets() {
        return multipleChoiceSets;
    }

    /**
     * Sets multiple choice sets.
     *
     * @param multipleChoiceSets the multiple choice sets
     */
    public void setMultipleChoiceSets(List<MultipleChoiceSet> multipleChoiceSets) {
        this.multipleChoiceSets = multipleChoiceSets;
    }

    /**
     * Gets written sets.
     *
     * @return the written sets
     */
    public List<WrittenSet> getWrittenSets() {
        return writtenSets;
    }

    /**
     * Sets written sets.
     *
     * @param writtenSets the written sets
     */
    public void setWrittenSets(List<WrittenSet> writtenSets) {
        this.writtenSets = writtenSets;
    }

    /**
     * Gets users assigned.
     *
     * @return the users assigned
     */
    public List<User> getUsersAssigned() {
        return usersAssigned;
    }

    /**
     * Sets users assigned.
     *
     * @param usersAssigned the users assigned
     */
    public void setUsersAssigned(List<User> usersAssigned) {
        this.usersAssigned = usersAssigned;
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
     * Gets created by.
     *
     * @return the created by
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param user the user
     */
    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExaminationEvent)) return false;
        ExaminationEvent that = (ExaminationEvent) o;
        return id.equals(that.id) &&
                examTitle.equals(that.examTitle) &&
                examTimeDuration.equals(that.examTimeDuration) &&
                multipleChoiceSets.equals(that.multipleChoiceSets) &&
                writtenSets.equals(that.writtenSets) &&
                usersAssigned.equals(that.usersAssigned) &&
                examDateAndTime.equals(that.examDateAndTime) &&
                updatedTimestamp.equals(that.updatedTimestamp) &&
                createdBy.equals(that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, examTitle, examTimeDuration, multipleChoiceSets, writtenSets, usersAssigned, examDateAndTime, updatedTimestamp, createdBy);
    }
}

