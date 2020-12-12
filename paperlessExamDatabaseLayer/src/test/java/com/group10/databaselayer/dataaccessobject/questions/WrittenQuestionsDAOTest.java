package com.group10.databaselayer.dataaccessobject.questions;


import com.group10.databaselayer.repositories.questions.written.IWrittenQuestionRepository;
import com.group10.databaselayer.repositories.questions.written.IWrittenSetRepository;

import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest

class WrittenQuestionsDAOTest {
@Autowired
    private IWrittenSetRepository writtenSetRepository;
@Autowired
    private IWrittenQuestionRepository writtenQuestionRepository;

    @Test
    void existsTitleAndTopicWrittenSet() {

    }

    @Test
    void createUpdateWrittenSet() {
    }

    @Test
    void addQuestionToExistingWrittenSet() {
    }

    @Test
    void getQuestionsByWrittenSet() {
    }

    @Test
    void removeWrittenSet() {
    }

    @Test
    void removeQuestionWrittenSet() {
    }

    @Test
    void findQuestionInWrittenSet() {
    }
}