package com.group10.databaselayer.repositories.questions.written;

import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWrittenQuestionRepository extends JpaRepository<WrittenQuestion, Long> {
    List<WrittenQuestion> findByWrittenSet(WrittenSet writtenSet);

    WrittenQuestion findByWrittenSetTopicAndWrittenSetTitleAndWrittenSetIdAndQuestionNumberAndQuestionAndScore(String topic, String title, Long id, int questionNumber, String question, double score);


}
