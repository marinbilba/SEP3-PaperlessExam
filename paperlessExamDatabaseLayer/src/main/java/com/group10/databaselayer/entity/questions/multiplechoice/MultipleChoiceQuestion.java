package com.group10.databaselayer.entity.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.Question;
import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@IdClass(Question.class)
public class MultipleChoiceQuestion extends Question {

    @ManyToOne(fetch = FetchType.LAZY)
    private MultipleChoiceSet multipleChoiceSet;
    @OneToMany(
            mappedBy = "multipleChoiceQuestion",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<QuestionOption> questionOptions=new ArrayList<>();


    public MultipleChoiceQuestion() {

    }

    public MultipleChoiceQuestion(String question, double score) {
        super(question, score);
    }

    public MultipleChoiceSet getMultipleChoiceSet() {
        return multipleChoiceSet;
    }

    public void setMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) {
        this.multipleChoiceSet = multipleChoiceSet;
    }

    public void addQuestionOption(QuestionOption questionOption) {
        this.questionOptions.add(questionOption);
       questionOption.setMultipleChoiceQuestion(this);
    }

    public void removeQuestionOption(QuestionOption questionOption) {
        this.questionOptions.remove(questionOption);
        questionOption.setMultipleChoiceQuestion(null);
    }



    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (getClass() != o.getClass()) { return false; }
        if (! super.equals(o)) return false;
        else {
            MultipleChoiceQuestion other=(MultipleChoiceQuestion) o;
            return multipleChoiceSet.equals(other.multipleChoiceSet)&&questionOptions.equals(other.questionOptions);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), multipleChoiceSet, questionOptions);
    }
}
