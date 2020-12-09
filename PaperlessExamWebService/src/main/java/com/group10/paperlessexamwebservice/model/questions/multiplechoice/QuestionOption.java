package com.group10.paperlessexamwebservice.model.questions.multiplechoice;


/**
 * Question option class.
 */
public class QuestionOption {

    private Long id;
    private boolean correctAnswer;
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
     * @param correctAnswer        the is correct answer
     * @param answer                 the answer
     * @param multipleChoiceQuestion the multiple choice question
     */
    public QuestionOption(boolean correctAnswer, String answer, MultipleChoiceQuestion multipleChoiceQuestion) {
        this.correctAnswer = correctAnswer;
        this.answer = answer;
        this.multipleChoiceQuestion=multipleChoiceQuestion;
    }

    public boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

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
