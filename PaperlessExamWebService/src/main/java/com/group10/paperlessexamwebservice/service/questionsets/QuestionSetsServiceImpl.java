package com.group10.paperlessexamwebservice.service.questionsets;

import com.group10.paperlessexamwebservice.databaserequests.requests.questionsets.IQuestionSetsRequests;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionSetsServiceImpl implements IQuestionSetsService {

    @Autowired
    private IQuestionSetsRequests questionSetsRequests;

    @Override
    public boolean validateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws EmptyQuestionSet, EmptyQuestionSetTitleOrTopic, QuestionSetAlreadyExists, ServiceNotAvailable {
        // Validate multiple choice set on null. Check if title and  topic are not empty
        if (multipleChoiceSet == null) {
            throw new EmptyQuestionSet("Multiple choice set has no elements");
        } else if (multipleChoiceSet.getTitle().isEmpty() || multipleChoiceSet.getTopic().isEmpty()) {
            throw new EmptyQuestionSetTitleOrTopic("Title or topic are empty");
        }
        // Send request to check if set exists
        boolean existsMultipleChoiceSet = questionSetsRequests.existsMultipleChoiceSet(multipleChoiceSet);
        System.out.println(existsMultipleChoiceSet);
        if (existsMultipleChoiceSet) {
            throw new QuestionSetAlreadyExists("Multiple Choice Set" + "with title:" + multipleChoiceSet.getTitle() +
                    "and topic" + multipleChoiceSet.getTopic() + "ALREADY EXISTS");
        }
        return true;
    }

    @Override
    public boolean validateMultipleChoiceSetQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws EmptyQuestionSetQuestions, MultipleChoiceQuestionOptionError {
        int count = 0;
        if (multipleChoiceQuestion == null) {
            throw new EmptyQuestionSetQuestions("Multiple choice question set must contain at least one question");
        } else if (multipleChoiceQuestion.getQuestion().isEmpty()) {
            throw new EmptyQuestionSetQuestions("Multiple choice question set must have a question with a valid title ");
        }
        for (var questionOption : multipleChoiceQuestion.getQuestionOptions()) {
            count++;
            if (questionOption.getAnswer().isEmpty()) {
                throw new MultipleChoiceQuestionOptionError("Multiple choice question option must contain an answer");
            }
        }
        if (count < 2) {
            throw new MultipleChoiceQuestionOptionError("Multiple choice question option must contain at least 2 options");
        }
        return true;
    }

    @Override
    public MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet) throws ServiceNotAvailable, UnexpectedError {
        MultipleChoiceSet createdMultipleChoiceSet = questionSetsRequests.createMultipleChoiceSet(multipleChoiceSet);
        if (createdMultipleChoiceSet==null){
            throw new UnexpectedError("Something went wrong");
        }
            return createdMultipleChoiceSet;
    }
}

