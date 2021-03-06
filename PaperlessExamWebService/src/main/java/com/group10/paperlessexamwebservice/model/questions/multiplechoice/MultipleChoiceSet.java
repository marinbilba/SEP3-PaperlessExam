package com.group10.paperlessexamwebservice.model.questions.multiplechoice;

import com.group10.paperlessexamwebservice.annotations.hidden.Hidden;
import com.group10.paperlessexamwebservice.model.questions.QuestionsSet;
import com.group10.paperlessexamwebservice.model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * MultipleChoiceSet class. Class provides the functionality to add/remove
 * questions and questions options
 *
 * @author Marin Bilba
 * @version v1.0
 */
public class MultipleChoiceSet extends QuestionsSet {

    private User user;
    private Date updatedTimestamp;
    private boolean submittedMultipleChoiceSet;
    @Hidden
    private List<MultipleChoiceQuestion> multipleChoiceQuestionList = new ArrayList<>();

    /**
     * Instantiates a new Multiple choice set.
     */
    public MultipleChoiceSet() {
    }


    /**
     * Instantiates a new Multiple choice set.
     *
     * @param title the title
     * @param topic the topic
     * @param user  the user
     */
    public MultipleChoiceSet(String title, String topic, User user) {
        super(title, topic);
        this.user = user;
    }

    /**
     * Instantiates a new Multiple choice set.
     *
     * @param title the title
     * @param topic the topic
     */
    public MultipleChoiceSet(String title, String topic) {
        super(title, topic);
    }

    public boolean isSubmittedMultipleChoiceSet() {
        return submittedMultipleChoiceSet;
    }

    public void setSubmittedMultipleChoiceSet(boolean submittedMultipleChoiceSet) {
        this.submittedMultipleChoiceSet = submittedMultipleChoiceSet;
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

    public List<MultipleChoiceQuestion> getMultipleChoiceQuestionList() {
        return multipleChoiceQuestionList;
    }

    public void setMultipleChoiceQuestionList(List<MultipleChoiceQuestion> multipleChoiceQuestionList) {
        this.multipleChoiceQuestionList = multipleChoiceQuestionList;
    }

    private boolean sameAsFormer(User newUser) {
        return Objects.equals(user, newUser);
    }

    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getTitle() {
        return super.getTitle();
    }

    public String getTopic() {
        return super.getTopic();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultipleChoiceSet)) return false;
        if (!super.equals(o)) return false;
        MultipleChoiceSet that = (MultipleChoiceSet) o;
        return user.equals(that.user) &&
                updatedTimestamp.equals(that.updatedTimestamp) &&
                multipleChoiceQuestionList.equals(that.multipleChoiceQuestionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
