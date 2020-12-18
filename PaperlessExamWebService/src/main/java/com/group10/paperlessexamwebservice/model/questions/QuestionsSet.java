package com.group10.paperlessexamwebservice.model.questions;

import java.util.Objects;

/**
 * Superclass for question sets.
 *
 * @author Marin Bilba
 * @version v1.0
 */

public class QuestionsSet {
    private Long id;
    private String title;
    private String topic;


    /**
     * Instantiates a new Questions set.
     */
    public QuestionsSet() {
    }

    /**
     * Instantiates a new Questions set.
     *
     * @param title the title
     * @param topic the topic
     */
    public QuestionsSet(String title, String topic) {
        this.title = title;
        this.topic = topic;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets topic.
     *
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Sets topic.
     *
     * @param topic the topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuestionsSet)) {
            return false;
        }
        QuestionsSet other = (QuestionsSet) o;
        return title.equals(other.title) && topic.equals(other.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, topic);
    }
}
