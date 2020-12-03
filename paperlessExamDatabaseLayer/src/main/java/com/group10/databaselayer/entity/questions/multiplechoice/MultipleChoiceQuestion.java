package com.group10.databaselayer.entity.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.Question;
import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;

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

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
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
    public MultipleChoiceQuestion(int questionNumber,String question, double score) {
        super(question, score,questionNumber);
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

    public String getQuestion(){
        return super.getQuestion();
    }

    /**
     * Get question score double.
     *
     * @return the double
     */
    public double getQuestionScore(){
        return super.getScore();
    }
    /**
     * Sets question.
     *
     * @param question the question
     */
    public void setQuestion(String question) {
        super.setQuestion(question);
    }
    /**
     * Sets score.
     *
     * @param score the question
     */
    public void setScore(double score) {
        super.setScore(score);
    }

    /**
     * Gets question options.
     *
     * @return the question options
     */
    public List<QuestionOption> getQuestionOptions() {
        return questionOptions;
    }

    /**
     * Sets question options.
     *
     * @param questionOptions the question options
     */
    public void setQuestionOptions(List<QuestionOption> questionOptions) {
        this.questionOptions = questionOptions;
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
