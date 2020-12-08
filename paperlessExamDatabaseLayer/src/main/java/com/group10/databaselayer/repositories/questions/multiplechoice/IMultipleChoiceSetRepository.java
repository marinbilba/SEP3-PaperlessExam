package com.group10.databaselayer.repositories.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository that provides CRUD operations for MultipleChoiceSet entity{@link MultipleChoiceSet}
 */
@Repository
public interface IMultipleChoiceSetRepository extends JpaRepository<MultipleChoiceSet, QuestionsSet> {
   MultipleChoiceSet findByTitleAndTopicAndUserId(String title, String topic, Long user);

   List<MultipleChoiceSet> findByUserId(Long id);
}
