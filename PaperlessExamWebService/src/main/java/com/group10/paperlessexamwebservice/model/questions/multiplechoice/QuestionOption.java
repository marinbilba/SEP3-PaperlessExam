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
    private MultipleChoiceQuestion multipleChoiceQuestion;

    /**
     * Instantiates a new Question option.
     */
    public QuestionOption() {
    }

    /**
     * Instantiates a new Question option.
     *
     * @param isCorrectAnswer        the is correct answer
     * @param answer                 the answer
     * @param multipleChoiceQuestion the multiple choice question
     */
    public QuestionOption(boolean isCorrectAnswer, String answer,MultipleChoiceQuestion multipleChoiceQuestion) {
        this.isCorrectAnswer = isCorrectAnswer;
        this.answer = answer;
        this.multipleChoiceQuestion=multipleChoiceQuestion;
    }

    /**
     * Is correct answer boolean.
     *
     * @return the boolean
     */
    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    /**
     * Sets correct answer.
     *
     * @param correctAnswer the correct answer
     */
    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }

    /**
     * Gets answer.
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets answer.
     *
     * @param answer the answer
     */
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
