package com.group10.databaselayer.repositories.questions.written;

import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * Repository that provides CRUD operations for WrittenSet entity{@link WrittenSet}
 */
@Repository
public interface IWrittenSetRepository extends JpaRepository<WrittenSet,QuestionsSet> {
// List<Map<String, Object>> findByTitleAndTopic(String title, String topic);
 }


