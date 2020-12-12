package com.group10.paperlessexamwebservice.model.examinationevent;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
import com.group10.paperlessexamwebservice.model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The type Examination event.
 */
public class ExaminationEvent {

    private Long id;
    private String examTitle;
    private List<MultipleChoiceSet> multipleChoiceSets = new ArrayList<>();
    private List<WrittenSet> writtenSets = new ArrayList<>();
    private List<User> usersAssigned = new ArrayList<>();
    private Date examDateAndTime;
    private Date updatedTimestamp;
    private User createdBy ;

    /**
     * Instantiates a new Examination event.
     */
    public ExaminationEvent() {
    }

    /**
     * Instantiates a new Examination event.
     *
     * @param examTitle          the exam title
     * @param multipleChoiceSets the multiple choice sets
     * @param writtenSets        the written sets
     * @param usersAssigned      the users assigned
     * @param examDateAndTime    the exam date and time
     * @param createdBy          the created by
     */
    public ExaminationEvent(String examTitle, List<MultipleChoiceSet> multipleChoiceSets, List<WrittenSet> writtenSets, List<User> usersAssigned, Date examDateAndTime, User createdBy) {
        this.examTitle = examTitle;
        this.multipleChoiceSets = multipleChoiceSets;
        this.writtenSets = writtenSets;
        this.usersAssigned = usersAssigned;
        this.examDateAndTime = examDateAndTime;
        this.createdBy = createdBy;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
