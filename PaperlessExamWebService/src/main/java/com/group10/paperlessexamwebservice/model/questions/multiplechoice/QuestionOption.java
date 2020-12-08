package com.group10.paperlessexamwebservice.model.questions.multiplechoice;

import com.group10.paperlessexamwebservice.annotations.hidden.Hidden;

import javax.persistence.*;


/**
 * Question option class.
 */

public class QuestionOption {

    private Long id;
    private boolean isCorrectAnswer;
    private String answer;
@Hidden
    private MultipleChoiceQuestion multipleChoiceQuestion;

    /**
     * Instantiates a new Question option.
     */
    public QuestionOption() {
    }

    /**
     * Instantiates a new Question option.
     *
     * @param isCorrectAnswer the is correct answer
     * @param answer          the answer
     */
    public QuestionOption(boolean isCorrectAnswer, String answer) {
        this.isCorrectAnswer = isCorrectAnswer;
        this.answer = answer;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Gets multiple choice question.
     *
     * @return the multiple choice question
     */
    public MultipleChoiceQuestion getMultipleChoiceQuestion() {
        return multipleChoiceQuestion;
    }

    /**
     * Sets multiple choice question.
     *
     * @param multipleChoiceQuestion the multiple choice question
     */
    public void setMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        this.multipleChoiceQuestion = multipleChoiceQuestion;
    }
}
