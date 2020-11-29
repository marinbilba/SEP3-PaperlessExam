package com.group10.databaselayer.repositories.questions;

import com.group10.databaselayer.entity.questions.written.WrittenSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWrittenSetRepository extends JpaRepository<WrittenSet,String> {

}
