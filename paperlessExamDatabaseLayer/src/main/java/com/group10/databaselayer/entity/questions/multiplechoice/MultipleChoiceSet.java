package com.group10.databaselayer.entity.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@IdClass(QuestionsSet.class)
public class MultipleChoiceSet extends QuestionsSet {

    @OneToMany(
            mappedBy = "multipleChoiceSet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();


    public MultipleChoiceSet() {
    }

    public MultipleChoiceSet(String title, String topic) {
        super(title, topic);
    }


    public void addQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        this.multipleChoiceQuestions.add(multipleChoiceQuestion);
        multipleChoiceQuestion.setMultipleChoiceSet(this);
    }

    public void removeQuestion(MultipleChoiceQuestion multipleChoiceQuestions) {
        this.multipleChoiceQuestions.remove(multipleChoiceQuestions);
        multipleChoiceQuestions.setMultipleChoiceSet(null);
    }

    public void addQuestionOption(MultipleChoiceQuestion multipleChoiceQuestion, QuestionOption questionOption) {
        for (var question : this.multipleChoiceQuestions) {
            if (question.equals(multipleChoiceQuestion)){
                multipleChoiceQuestion.addQuestionOption(questionOption);
            }
        }
    }

    public void removeQuestionOption(MultipleChoiceQuestion multipleChoiceQuestion, QuestionOption questionOption) {
        for (var question : this.multipleChoiceQuestions) {
            if (question.equals(multipleChoiceQuestion)){
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
            return multipleChoiceQuestions.equals(other.multipleChoiceQuestions);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), multipleChoiceQuestions);
    }
}
