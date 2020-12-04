package com.group10.databaselayer.repositories.questions.written;

import com.group10.databaselayer.entity.questions.Question;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWrittenQuestionRepository extends JpaRepository<WrittenQuestion, Question> {
    List<WrittenQuestion> findByWrittenSet(WrittenSet writtenSet);
}