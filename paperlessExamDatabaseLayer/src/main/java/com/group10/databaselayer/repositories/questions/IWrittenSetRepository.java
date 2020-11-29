package com.group10.databaselayer.repositories.questions;

import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


/**
 * Repository that provides CRUD operations for WrittenSet entity{@link WrittenSet}
 */
@Repository
public interface IWrittenSetRepository extends JpaRepository<WrittenSet,QuestionsSet> {
 }


