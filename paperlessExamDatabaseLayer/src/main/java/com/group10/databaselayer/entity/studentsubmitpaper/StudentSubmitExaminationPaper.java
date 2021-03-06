package com.group10.databaselayer.entity.studentsubmitpaper;

import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.user.User;
import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The type Student submit examination paper.
 */
@Entity
public class StudentSubmitExaminationPaper {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @CreationTimestamp
    private Date submitTimestamp;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "submit_by", updatable = false)
    private User submitBy;

    @OneToOne
    @NotNull
    @JoinColumn(name = "fk_examination_event_id")
    private ExaminationEvent examinationEvent;

    @ManyToMany()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "submit_paper_written_sets",
            joinColumns = {@JoinColumn(name = "student_submit_examination_paper_id")},
            inverseJoinColumns = {@JoinColumn(name = "written_set_id")})
    private List<WrittenSet> submitWrittenSets = new ArrayList<>();

    @ManyToMany()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "submit_paper_multiple_choice_sets",
            joinColumns = {@JoinColumn(name = "student_submit_examination_paper_id")},
            inverseJoinColumns = {@JoinColumn(name = "multiple_choice_set_id")})
    private List<MultipleChoiceSet> submitMultipleChoiceSets = new ArrayList<>();

    /**
     * Instantiates a new Student submit examination paper.
     */
    public StudentSubmitExaminationPaper() {
    }

    /**
     * Instantiates a new Student submit examination paper.
     *
     * @param submitTimestamp          the submit timestamp
     * @param submitBy                 the submit by
     * @param examinationEvent         the examination event
     * @param submitWrittenSets        the submit written sets
     * @param submitMultipleChoiceSets the submit multiple choice sets
     */
    public StudentSubmitExaminationPaper(Date submitTimestamp, User submitBy, ExaminationEvent examinationEvent, List<WrittenSet> submitWrittenSets, List<MultipleChoiceSet> submitMultipleChoiceSets) {
        this.submitTimestamp = submitTimestamp;
        this.submitBy = submitBy;
        this.examinationEvent = examinationEvent;
        this.submitWrittenSets = submitWrittenSets;
        this.submitMultipleChoiceSets = submitMultipleChoiceSets;
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
     * Gets submit timestamp.
     *
     * @return the submit timestamp
     */
    public Date getSubmitTimestamp() {
        return submitTimestamp;
    }

    /**
     * Sets submit timestamp.
     *
     * @param submitTimestamp the submit timestamp
     */
    public void setSubmitTimestamp(Date submitTimestamp) {
        this.submitTimestamp = submitTimestamp;
    }

    /**
     * Gets submit by.
     *
     * @return the submit by
     */
    public User getSubmitBy() {
        return submitBy;
    }

    /**
     * Sets submit by.
     *
     * @param submitBy the submit by
     */
    public void setSubmitBy(User submitBy) {
        this.submitBy = submitBy;
    }

    /**
     * Gets examination event.
     *
     * @return the examination event
     */
    public ExaminationEvent getExaminationEvent() {
        return examinationEvent;
    }

    /**
     * Sets examination event.
     *
     * @param examinationEvent the examination event
     */
    public void setExaminationEvent(ExaminationEvent examinationEvent) {
        this.examinationEvent = examinationEvent;
    }

    /**
     * Gets submit written sets.
     *
     * @return the submit written sets
     */
    public List<WrittenSet> getSubmitWrittenSets() {
        return submitWrittenSets;
    }

    /**
     * Sets submit written sets.
     *
     * @param submitWrittenSets the submit written sets
     */
    public void setSubmitWrittenSets(List<WrittenSet> submitWrittenSets) {
        this.submitWrittenSets = submitWrittenSets;
    }

    /**
     * Gets submit multiple choice sets.
     *
     * @return the submit multiple choice sets
     */
    public List<MultipleChoiceSet> getSubmitMultipleChoiceSets() {
        return submitMultipleChoiceSets;
    }

    /**
     * Sets submit multiple choice sets.
     *
     * @param submitMultipleChoiceSets the submit multiple choice sets
     */
    public void setSubmitMultipleChoiceSets(List<MultipleChoiceSet> submitMultipleChoiceSets) {
        this.submitMultipleChoiceSets = submitMultipleChoiceSets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentSubmitExaminationPaper)) return false;
        StudentSubmitExaminationPaper that = (StudentSubmitExaminationPaper) o;
        return id.equals(that.id) &&
                submitTimestamp.equals(that.submitTimestamp) &&
                submitBy.equals(that.submitBy) &&
                examinationEvent.equals(that.examinationEvent) &&
                submitWrittenSets.equals(that.submitWrittenSets) &&
                submitMultipleChoiceSets.equals(that.submitMultipleChoiceSets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, submitTimestamp, submitBy, examinationEvent, submitWrittenSets, submitMultipleChoiceSets);
    }
}