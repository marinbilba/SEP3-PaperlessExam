package com.group10.paperlessexamwebservice.model.questions.written;


import com.group10.paperlessexamwebservice.model.questions.QuestionsSet;
import com.group10.paperlessexamwebservice.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Class provides the functionality to add/remove questions
 *
 * @author Marin Bilba
 * @version v1.0
 */
public class WrittenSet extends QuestionsSet {
    private List<WrittenQuestion> writtenQuestions = new ArrayList<WrittenQuestion>();
    private User user;

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
    public WrittenSet(String title, String topic,User user) {
        super(title, topic);
        this.user=user;
    }

    /**
     * Add question.
     *
     * @param writtenQuestion the written question
     */
    public void addQuestion(WrittenQuestion writtenQuestion) {
        this.writtenQuestions.add(writtenQuestion);
        writtenQuestion.setWrittenSet(this);
    }

    /**
     * Remove question.
     *
     * @param writtenQuestion the written question
     */
    public void removeQuestion(WrittenQuestion writtenQuestion) {
        this.writtenQuestions.remove(writtenQuestion);
        writtenQuestion.setWrittenSet(null);
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
            WrittenSet other = (WrittenSet) o;
            return writtenQuestions.equals(other.writtenQuestions)&&user.equals(other.user);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writtenQuestions);
    }
}
