package com.group10.databaselayer.repositories.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMultipleChoiceQuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
    QuestionOption findByAnswerAndCorrectAnswerAndMultipleChoiceQuestion_QuestionAndMultipleChoiceQuestion_ScoreAndMultipleChoiceQuestion_QuestionNumber(String answer, boolean correctAnswer,String question,double score, int questionNumber );

    List<QuestionOption> findByMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion);
}
