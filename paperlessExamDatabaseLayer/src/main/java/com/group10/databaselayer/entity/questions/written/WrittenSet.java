package com.group10.databaselayer.entity.questions.written;

import com.group10.databaselayer.entity.questions.QuestionsSet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@IdClass(QuestionsSet.class)
public class WrittenSet extends QuestionsSet {

    @OneToMany(
            mappedBy = "writtenSet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WrittenQuestion> writtenQuestions=new ArrayList<>();

    public WrittenSet() {
    }

    public WrittenSet(String title, String topic) {
        super(title, topic);
    }

    public void addQuestion(WrittenQuestion writtenQuestion) {
        this.writtenQuestions.add(writtenQuestion);
        writtenQuestion.setWrittenSet(this);
    }

    public void removeQuestion(WrittenQuestion writtenQuestion) {
        this.writtenQuestions.remove(writtenQuestion);
        writtenQuestion.setWrittenSet(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (getClass() != o.getClass()) { return false; }
        if (! super.equals(o)) return false;
        else {
            WrittenSet other=(WrittenSet) o;
            return writtenQuestions.equals(other.writtenQuestions);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writtenQuestions);
    }
}
