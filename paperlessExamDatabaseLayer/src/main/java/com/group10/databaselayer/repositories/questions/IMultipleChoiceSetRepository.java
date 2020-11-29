package com.group10.databaselayer.repositories.questions;

import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository that provides CRUD operations for MultipleChoiceSet entity{@link MultipleChoiceSet}
 */
@Repository
public interface IMultipleChoiceSetRepository extends JpaRepository<MultipleChoiceSet, QuestionsSet> {
}
