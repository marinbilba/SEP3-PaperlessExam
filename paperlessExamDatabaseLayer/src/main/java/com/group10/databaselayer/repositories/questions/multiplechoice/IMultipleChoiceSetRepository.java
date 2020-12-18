package com.group10.databaselayer.repositories.questions.multiplechoice;

import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository that provides CRUD operations for MultipleChoiceSet entity{@link MultipleChoiceSet}
 */
@Repository
public interface IMultipleChoiceSetRepository extends JpaRepository<MultipleChoiceSet, Long> {
    MultipleChoiceSet findByTitleAndTopicAndUserIdAndSubmittedMultipleChoiceSet(String title, String topic, Long user, boolean submittedMultipleChoiceSet);

    List<MultipleChoiceSet> findByUserId(Long id);

    @Query("select distinct multipleSets \n" +
            "from MultipleChoiceSet multipleSets  \n" +
            "join multipleSets.examinationEvents examEvents \n" +
            "join examEvents.multipleChoiceSets multipleSet \n" +
            "where examEvents.id = :id")
    List<MultipleChoiceSet> getExaminationEventMultipleChoiceSet(long id);


}
