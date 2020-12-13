package com.group10.paperlessexamwebservice.model.questions.multiplechoice;


import com.group10.paperlessexamwebservice.annotations.hidden.Hidden;
import com.group10.paperlessexamwebservice.model.questions.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Multiple choice question class.
 */
public class MultipleChoiceQuestion extends Question {

    private MultipleChoiceSet multipleChoiceSet;
    @Hidden
    private List<QuestionOption> questionOptions=new ArrayList<>();


    /**
     * Instantiates a new Multiple choice question.
     */
    public MultipleChoiceQuestion() {

    }

    /**
     * Instantiates a new Multiple choice question.
     *
     * @param question          the question
     * @param score             the score
     * @param questionNumber    the question number
     * @param multipleChoiceSet the multiple choice set
     */
    public MultipleChoiceQuestion(String question, double score, int questionNumber, MultipleChoiceSet multipleChoiceSet) {
        super(question, score, questionNumber);
        this.multipleChoiceSet = multipleChoiceSet;
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
            return multipleChoiceSet.equals(other.multipleChoiceSet);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), multipleChoiceSet);
    }
}
