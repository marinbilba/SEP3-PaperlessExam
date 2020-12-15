package com.group10.databaselayer.repositories.examinationevent;

import com.group10.databaselayer.entity.teacherpaperevaluation.TeacherEvaluationPaperResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITeacherEvaluationPaperResultRepository extends JpaRepository<TeacherEvaluationPaperResult,Long> {

@Query("select distinct examResults \n" +
        "from TeacherEvaluationPaperResult examResults  \n" +
        "where examResults.studentSubmitExaminationPaper.examinationEvent.id = :examId AND examResults.studentSubmitExaminationPaper.submitBy.id=:studentId")
    TeacherEvaluationPaperResult getExaminationEventResultByExamIdAndStudentId(long examId,long studentId);
}
