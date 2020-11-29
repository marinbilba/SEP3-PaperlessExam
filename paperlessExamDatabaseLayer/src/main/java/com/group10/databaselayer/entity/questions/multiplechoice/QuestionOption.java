package com.group10.databaselayer.entity.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.QuestionsSet;

import javax.persistence.*;


@Entity
@Table(name = "question_option")
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isCorrectAnswer;
    private String answer;


    @ManyToOne(fetch = FetchType.LAZY)
    private MultipleChoiceQuestion multipleChoiceQuestion;

    public QuestionOption() {
    }

    public QuestionOption(boolean isCorrectAnswer, String answer) {
        this.isCorrectAnswer = isCorrectAnswer;
        this.answer = answer;
    }

    public MultipleChoiceQuestion getMultipleChoiceQuestion() {
        return multipleChoiceQuestion;
    }

    public void setMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        this.multipleChoiceQuestion = multipleChoiceQuestion;
    }
}
