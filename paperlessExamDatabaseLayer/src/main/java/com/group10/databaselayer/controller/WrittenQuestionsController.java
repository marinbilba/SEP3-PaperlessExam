package com.group10.databaselayer.controller;

import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.repositories.questions.IWrittenSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class WrittenQuestionsController {

    @Autowired
    IWrittenSetRepository writtenSetRepository;

    public WrittenSet createWrittenSet(WrittenSet writtenSet){
        WrittenSet writtenSet1=new WrittenSet("Pula","Java");
        writtenSet1.addQuestion(new WrittenQuestion("Tu esti loh?",30));
        writtenSet1.addQuestion(new WrittenQuestion("Esti pedor",25));
       return writtenSetRepository.save(writtenSet1);
    }
}
