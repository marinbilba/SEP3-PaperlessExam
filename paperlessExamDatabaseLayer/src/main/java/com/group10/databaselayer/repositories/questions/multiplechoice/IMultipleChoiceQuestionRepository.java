package com.group10.databaselayer.repositories.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.Question;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion, Question> {
}
