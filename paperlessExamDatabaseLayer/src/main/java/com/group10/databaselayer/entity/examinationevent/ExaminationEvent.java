package com.group10.databaselayer.entity.examinationevent;

import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.user.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

// Owner of many to many relation
@Entity
public class ExaminationEvent {
    @GeneratedValue()
    @Id
    private Long id;
    private String examTitle;

    @ManyToMany()
    @JoinTable(name = "examination_event_multiple_choice_sets",
            joinColumns = {@JoinColumn(name = "examination_event_id")},
            inverseJoinColumns = {@JoinColumn(name = "multiple_choice_set_id")})
    private Set<MultipleChoiceSet> multipleChoiceSets = new HashSet<MultipleChoiceSet>();


    @ManyToMany()
    @JoinTable(name = "examination_event_written_sets",
            joinColumns = {@JoinColumn(name = "examination_event_id")},
            inverseJoinColumns = {@JoinColumn(name = "written_set_id")})
    private Set<WrittenSet> writtenSets = new HashSet<WrittenSet>();

    @ManyToMany()
    @JoinTable(name = "examination_event_users_assigned",
            joinColumns = {@JoinColumn(name = "examination_event_id")},
            inverseJoinColumns = {@JoinColumn(name = "written_set_id")})
    private Set<User> usersAssigned = new HashSet<User>();



    @CreationTimestamp
    private Date updatedTimestamp;

    public ExaminationEvent() {
    }

    public ExaminationEvent(String examTitle, Set<WrittenSet> writtenSets) {
        this.examTitle = examTitle;
        this.writtenSets = writtenSets;
    }

    public Set<WrittenSet> getWrittenSets() {
        return writtenSets;
    }

    public void setWrittenSets(Set<WrittenSet> writtenSets) {
        this.writtenSets = writtenSets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public Set<MultipleChoiceSet> getMultipleChoiceSets() {
        return multipleChoiceSets;
    }

    public void setMultipleChoiceSets(Set<MultipleChoiceSet> multipleChoiceSets) {
        this.multipleChoiceSets = multipleChoiceSets;
    }
}
