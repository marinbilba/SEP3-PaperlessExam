package com.group10.paperlessexamwebservice.databaserequests.requests.examinationevent;

import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
import com.group10.paperlessexamwebservice.model.studentsubmitpaper.StudentSubmitExaminationPaper;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;

import java.util.List;

public interface IExaminationEventRequest {
    ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable;

    List<ExaminationEvent> getTeachersExamEvents(String teacherId) throws ServiceNotAvailable;

    List<ExaminationEvent> getStudentsExamEvents(String studentId) throws ServiceNotAvailable;

    ExaminationEvent getExaminationEventById(String examinationEventId) throws ServiceNotAvailable;

    List<MultipleChoiceSet> getExaminationEventMultipleChoiceSets(ExaminationEvent fetchedExaminationEvent) throws ServiceNotAvailable;

    List<WrittenSet> getExaminationEventWrittenSets(ExaminationEvent fetchedExaminationEvent) throws ServiceNotAvailable;

    StudentSubmitExaminationPaper submitStudentExaminationPaper(StudentSubmitExaminationPaper paperToSubmit) throws ServiceNotAvailable;

    StudentSubmitExaminationPaper getStudentSubmittedPaperByStudentIdAndExamId(String studentId, String examId) throws ServiceNotAvailable;
}
