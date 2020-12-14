package com.group10.databaselayer.repositories.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.multiplechoice.QuestionOption;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository that provides CRUD operations for MultipleChoiceSet entity{@link MultipleChoiceSet}
 */
@Repository
public interface IMultipleChoiceSetRepository extends JpaRepository<MultipleChoiceSet, Long> {
   MultipleChoiceSet findByTitleAndTopicAndUserId(String title, String topic, Long user);

   List<MultipleChoiceSet> findByUserId(Long id);
   @Query("select distinct multipleSets \n" +
           "from MultipleChoiceSet multC  \n" +
           "join multC.examinationEvents examEvents \n" +
           "join examEvents.multipleChoiceSets multipleSets \n" +
           "where multipleSets.id = :id")
   List<MultipleChoiceSet>getExaminationEventMultipleChoiceSet(long id);
}
