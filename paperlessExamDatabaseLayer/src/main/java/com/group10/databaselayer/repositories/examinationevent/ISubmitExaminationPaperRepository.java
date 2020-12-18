package com.group10.databaselayer.repositories.examinationevent;

import com.group10.databaselayer.entity.studentsubmitpaper.StudentSubmitExaminationPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubmitExaminationPaperRepository extends JpaRepository<StudentSubmitExaminationPaper, Long> {
    //StudentSubmitExaminationPaper findByExaminationEventAndSubmitBy(ExaminationEvent examinationEvent, User submitBy);
//    StudentSubmitExaminationPaper findByExaminationEventAndSubmitBy(ExaminationEvent examinationEvent, User submitBy);
    StudentSubmitExaminationPaper findByExaminationEvent_IdAndSubmitBy_Id(long examinationEventId, long submitById);

}
