package com.group10.databaselayer.entity.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.annotations.hidden.Hidden;
import com.group10.databaselayer.entity.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
@IdClass(QuestionsSet.class)
public class MultipleChoiceSet extends QuestionsSet {
    @OneToMany(
            mappedBy = "multipleChoiceSet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  //  @JoinColumn(name = "user_id", updatable = false)
    @Hidden
    private User user;

    /**
     * Instantiates a new Multiple choice set.
     */
    public MultipleChoiceSet() {
    }

    public MultipleChoiceSet(String title, String topic) {
        super(title, topic);
    }

    /**
     * Instantiates a new Multiple choice set.
     *
     * @param title the title
     * @param topic the topic
     */
    public MultipleChoiceSet(String title, String topic, User user) {
        super(title, topic);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Add question. Method synchronizes both sides of the bidirectional association between
     * this entity {@link MultipleChoiceSet} and MultipleChoiceQuestion {@link MultipleChoiceQuestion}
     * in order to avoid very subtle state propagation issues
     *
     * @param multipleChoiceQuestion the multiple choice question
     */
    public void addQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        this.multipleChoiceQuestions.add(multipleChoiceQuestion);
        multipleChoiceQuestion.setMultipleChoiceSet(this);
    }

    /**
     * Remove question. Method synchronizes both sides of the bidirectional association between
     * this entity {@link MultipleChoiceSet} and MultipleChoiceQuestion {@link MultipleChoiceQuestion}
     * in order to avoid very subtle state propagation issues
     *
     * @param multipleChoiceQuestions the multiple choice questions
     */
    public void removeQuestion(MultipleChoiceQuestion multipleChoiceQuestions) {
        this.multipleChoiceQuestions.remove(multipleChoiceQuestions);
        multipleChoiceQuestions.setMultipleChoiceSet(null);
    }

    /**
     * Add question option.
     *
     * @param multipleChoiceQuestion the multiple choice question
     * @param questionOption         the question option
     */
    public void addQuestionOption(MultipleChoiceQuestion multipleChoiceQuestion, QuestionOption questionOption) {
        for (var question : this.multipleChoiceQuestions) {
            if (question.equals(multipleChoiceQuestion)) {
                multipleChoiceQuestion.addQuestionOption(questionOption);
            }
        }
    }

    public String getTitle() {
        return super.getTitle();
    }

    public String getTopic() {
        return super.getTopic();
    }

    /**
     * Remove question option.
     *
     * @param multipleChoiceQuestion the multiple choice question
     * @param questionOption         the question option
     */
    public void removeQuestionOption(MultipleChoiceQuestion multipleChoiceQuestion, QuestionOption questionOption) {
        for (var question : this.multipleChoiceQuestions) {
            if (question.equals(multipleChoiceQuestion)) {
                multipleChoiceQuestion.removeQuestionOption(questionOption);
            }
        }
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
            return multipleChoiceQuestions.equals(other.multipleChoiceQuestions) && user.equals(other.user);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), multipleChoiceQuestions);
    }
}
