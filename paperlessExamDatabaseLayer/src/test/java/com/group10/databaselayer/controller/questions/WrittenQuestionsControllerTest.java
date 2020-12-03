package com.group10.databaselayer.controller.questions;


import com.group10.databaselayer.entity.questions.QuestionsSet;
import com.group10.databaselayer.exception.questions.TitleOrTopicAreNull;
import com.group10.databaselayer.repositories.questions.written.IWrittenQuestionRepository;
import com.group10.databaselayer.repositories.questions.written.IWrittenSetRepository;

import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest

class WrittenQuestionsControllerTest {
@Autowired
    private IWrittenSetRepository writtenSetRepository;
@Autowired
    private IWrittenQuestionRepository writtenQuestionRepository;
@Autowired
    private WrittenMultipleChoiceQuestionsSharedMethods writtenMultipleChoiceQuestionsSharedMethods;

    @Test
    void existsTitleAndTopicWrittenSet() {
        try {
            writtenMultipleChoiceQuestionsSharedMethods.checkTitleTopicNotNull(new QuestionsSet("ads","sda"));
        } catch (TitleOrTopicAreNull titleOrTopicAreNull) {
            titleOrTopicAreNull.printStackTrace();
        }
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