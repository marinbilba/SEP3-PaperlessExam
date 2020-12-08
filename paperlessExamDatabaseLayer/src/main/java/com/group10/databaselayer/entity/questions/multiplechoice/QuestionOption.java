package com.group10.databaselayer.entity.questions.multiplechoice;

import com.group10.databaselayer.annotations.hidden.Hidden;

import javax.persistence.*;


/**
 * Entity Question option.
 */
@Entity
@Table(name = "question_option")
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isCorrectAnswer;
    private String answer;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "multiple_choice_question_number"),
            @JoinColumn(name = "multiple_choice_question_question"),
            @JoinColumn(name = "multiple_choice_set_question_score")
    })

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
    public QuestionOption( boolean isCorrectAnswer, String answer) {
        this.isCorrectAnswer = isCorrectAnswer;
        this.answer = answer;
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
