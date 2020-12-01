package com.group10.databaselayer.repositories.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.multiplechoice.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMultipleChoiceQuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
}
