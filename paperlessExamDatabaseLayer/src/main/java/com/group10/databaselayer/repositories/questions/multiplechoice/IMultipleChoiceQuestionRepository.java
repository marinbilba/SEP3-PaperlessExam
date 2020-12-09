package com.group10.databaselayer.repositories.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion, Long> {
    List<MultipleChoiceQuestion> findByMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);


    MultipleChoiceQuestion findByMultipleChoiceSetTopicAndMultipleChoiceSetTitleAndMultipleChoiceSetIdAndQuestionNumberAndQuestionAndScore(String topic, String title, Long id, int questionNumber, String question, double questionScore);


}
