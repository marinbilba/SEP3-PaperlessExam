package com.group10.databaselayer.entity.examinationevent;

import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

// Owner of many to many relation
@Entity
public class ExaminationEvent {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String examTitle;

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
            inverseJoinColumns = {@JoinColumn(name = "written_set_id")})
    private List<User> usersAssigned = new ArrayList<>();

    private Date examDateAndTime;


    @CreationTimestamp
    private Date updatedTimestamp;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "created_by", updatable = false)
    private User createdBy;

    public ExaminationEvent() {
    }

    public ExaminationEvent(String examTitle, List<MultipleChoiceSet> multipleChoiceSets, List<WrittenSet> writtenSets, List<User> usersAssigned, Date examDateAndTime, User createdBy) {
        this.examTitle = examTitle;
        this.multipleChoiceSets = multipleChoiceSets;
        this.writtenSets = writtenSets;
        this.usersAssigned = usersAssigned;
        this.examDateAndTime = examDateAndTime;
        this.createdBy = createdBy;
    }

    public Date getExamDateAndTime() {
        return examDateAndTime;
    }

    public void setExamDateAndTime(Date examDateAndTime) {
        this.examDateAndTime = examDateAndTime;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public List<MultipleChoiceSet> getMultipleChoiceSets() {
        return multipleChoiceSets;
    }

    public void setMultipleChoiceSets(List<MultipleChoiceSet> multipleChoiceSets) {
        this.multipleChoiceSets = multipleChoiceSets;
    }

    public List<WrittenSet> getWrittenSets() {
        return writtenSets;
    }

    public void setWrittenSets(List<WrittenSet> writtenSets) {
        this.writtenSets = writtenSets;
    }

    public List<User> getUsersAssigned() {
        return usersAssigned;
    }

    public void setUsersAssigned(List<User> usersAssigned) {
        this.usersAssigned = usersAssigned;
    }

    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public User getCreatedBy() {
        return createdBy;
    }

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
                multipleChoiceSets.equals(that.multipleChoiceSets) &&
                writtenSets.equals(that.writtenSets) &&
                usersAssigned.equals(that.usersAssigned) &&
                examDateAndTime.equals(that.examDateAndTime) &&
                updatedTimestamp.equals(that.updatedTimestamp) &&
                createdBy.equals(that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, examTitle, multipleChoiceSets, writtenSets, usersAssigned, examDateAndTime, updatedTimestamp, createdBy);
    }
}

