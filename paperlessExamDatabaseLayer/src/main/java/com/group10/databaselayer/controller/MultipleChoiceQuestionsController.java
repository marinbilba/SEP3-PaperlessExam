package com.group10.databaselayer.controller;

import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.multiplechoice.QuestionOption;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.repositories.questions.IMultipleChoiceSetRepository;
import com.group10.databaselayer.repositories.questions.IWrittenSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultipleChoiceQuestionsController {
    @Autowired
    IMultipleChoiceSetRepository multipleChoiceSetRepository;

    public MultipleChoiceSet createMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet){
      MultipleChoiceSet multipleChoiceSet1=new MultipleChoiceSet("Pula","DNP");

       MultipleChoiceQuestion multipleChoiceQuestion=new MultipleChoiceQuestion("Vase cum la tine?",50);
       multipleChoiceSet1.addQuestion(multipleChoiceQuestion);

       multipleChoiceSet1.addQuestionOption(multipleChoiceQuestion,new QuestionOption(true,"zaebisi"));

        return multipleChoiceSetRepository.save(multipleChoiceSet1);
    }
}
