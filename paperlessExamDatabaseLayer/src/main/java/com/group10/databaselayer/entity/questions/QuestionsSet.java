package com.group10.databaselayer.entity.questions;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;


@MappedSuperclass
@Embeddable
public  class QuestionsSet implements Serializable {
@Id
    private String title;
  @Id
    private String topic;



    public QuestionsSet() {
    }

    public QuestionsSet(String title, String topic) {
        this.title = title;
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
