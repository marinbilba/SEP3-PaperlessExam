package com.group10.databaselayer.entity.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.Question;
import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity Multiple choice question.
 */
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
    private List<QuestionOption> questionOptions = new ArrayList<>();


    /**
     * Instantiates a new Multiple choice question.
     */
    public MultipleChoiceQuestion() {

    }

    /**
     * Instantiates a new Multiple choice question.
     *
     * @param question the question
     * @param score    the score
     */
    public MultipleChoiceQuestion(String question, double score) {
        super(question, score);
    }

    /**
     * Gets multiple choice set.
     *
     * @return the multiple choice set
     */
    public MultipleChoiceSet getMultipleChoiceSet() {
        return multipleChoiceSet;
    }

    /**
     * Sets multiple choice set.
     *
     * @param multipleChoiceSet the multiple choice set
     */
    public void setMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) {
        this.multipleChoiceSet = multipleChoiceSet;
    }

    /**
     * Add question option. Method synchronizes both sides of the bidirectional association between
     * this entity {@link MultipleChoiceQuestion} and QuestionOption {@link QuestionOption}
     * in order to avoid very subtle state propagation issues
     *
     * @param questionOption the question option
     */
    public void addQuestionOption(QuestionOption questionOption) {
        this.questionOptions.add(questionOption);
        questionOption.setMultipleChoiceQuestion(this);
    }

    /**
     * Remove question option. Method synchronizes both sides of the bidirectional association between
     * this entity {@link MultipleChoiceQuestion} and QuestionOption {@link QuestionOption}
     * in order to avoid very subtle state propagation issues
     *
     * @param questionOption the question option
     */
    public void removeQuestionOption(QuestionOption questionOption) {
        this.questionOptions.remove(questionOption);
        questionOption.setMultipleChoiceQuestion(null);
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
            MultipleChoiceQuestion other = (MultipleChoiceQuestion) o;
            return multipleChoiceSet.equals(other.multipleChoiceSet) && questionOptions.equals(other.questionOptions);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), multipleChoiceSet, questionOptions);
    }
}