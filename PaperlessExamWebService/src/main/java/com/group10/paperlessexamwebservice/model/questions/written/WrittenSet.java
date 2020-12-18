package com.group10.paperlessexamwebservice.model.questions.written;


import com.group10.paperlessexamwebservice.annotations.hidden.Hidden;
import com.group10.paperlessexamwebservice.model.questions.QuestionsSet;
import com.group10.paperlessexamwebservice.model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Class provides the functionality to add/remove questions
 *
 * @author Marin Bilba
 * @version v1.0
 */
public class WrittenSet extends QuestionsSet {

    private Date updatedTimestamp;
    private User user;
    private boolean submittedWrittenSet;

    @Hidden
    private List<WrittenQuestion> writtenQuestions = new ArrayList<>();

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
        super(title, topic);
        this.user = user;
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

    public String getTitle() {
        return super.getTitle();
    }

    public String getTopic() {
        return super.getTopic();
    }

    /**
     * Gets written questions.
     *
     * @return the written questions
     */
    public List<WrittenQuestion> getWrittenQuestions() {
        return writtenQuestions;
    }

    /**
     * Sets written questions.
     *
     * @param writtenQuestions the written questions
     */
    public void setWrittenQuestions(List<WrittenQuestion> writtenQuestions) {
        this.writtenQuestions = writtenQuestions;
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
        if (!super.equals(o)) return false;
        WrittenSet that = (WrittenSet) o;
        return submittedWrittenSet == that.submittedWrittenSet &&
                updatedTimestamp.equals(that.updatedTimestamp) &&
                user.equals(that.user) &&
                writtenQuestions.equals(that.writtenQuestions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), updatedTimestamp, user, submittedWrittenSet, writtenQuestions);
    }
}
