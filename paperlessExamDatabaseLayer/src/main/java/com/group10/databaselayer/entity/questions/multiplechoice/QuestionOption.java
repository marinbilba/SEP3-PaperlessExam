package com.group10.databaselayer.entity.questions.multiplechoice;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;


/**
 * Entity Question option.
 */
@Entity
@Table(name = "question_option")
public class QuestionOption {
    @Id
    @GeneratedValue()
    private Long id;
    private boolean correctAnswer;
    private String answer;

    @Fetch(FetchMode.SELECT)
    @ManyToOne()
    private MultipleChoiceQuestion multipleChoiceQuestion;

    /**
     * Instantiates a new Question option.
     */
    public QuestionOption() {
    }


    /**
     * Instantiates a new Question option.
     *
     * @param correctAnswer          the is correct answer
     * @param answer                 the answer
     * @param multipleChoiceQuestion the multiple choice question
     */
    public QuestionOption(boolean correctAnswer, String answer, MultipleChoiceQuestion multipleChoiceQuestion) {
        this.correctAnswer = correctAnswer;
        this.answer = answer;
        this.multipleChoiceQuestion = multipleChoiceQuestion;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean setCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionOption)) return false;
        QuestionOption that = (QuestionOption) o;
        return correctAnswer == that.correctAnswer &&
                id.equals(that.id) &&
                answer.equals(that.answer) &&
                multipleChoiceQuestion.equals(that.multipleChoiceQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, correctAnswer, answer, multipleChoiceQuestion);
    }
}
