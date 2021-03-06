package com.group10.paperlessexamwebservice.service.examinaationevents;

import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.model.studentsubmitpaper.StudentSubmitExaminationPaper;
import com.group10.paperlessexamwebservice.model.teacherpaperevaluation.TeacherEvaluationPaperResult;
import com.group10.paperlessexamwebservice.service.exceptions.examinationevent.ExaminationEventException;
import com.group10.paperlessexamwebservice.service.exceptions.other.NegativeNumberException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.EmptyQuestionSetTitleOrTopic;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.NullQuestionSet;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.QuestionSetAlreadyExists;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.EmptyMultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.NullQuestionSetQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.submitpaper.SubmitExaminationPaperException;
import com.group10.paperlessexamwebservice.service.exceptions.user.UserNotFound;

import java.util.List;

public interface IExaminationEventService {
    ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable, UnexpectedError;

    List<ExaminationEvent> getTeachersUpcomingExamEvents(String teacherId) throws ServiceNotAvailable, ExaminationEventException;

    List<ExaminationEvent> getTeachersPassedExamEvents(String teacherId) throws ExaminationEventException, ServiceNotAvailable;

    List<ExaminationEvent> getStudentsUpcomingExamEvents(String studentId) throws ExaminationEventException, ServiceNotAvailable;

    List<ExaminationEvent> getStudentsPassedExamEvents(String studentId) throws ExaminationEventException, ServiceNotAvailable;

    List<ExaminationEvent> getStudentsOngoingExamEvents(String studentId) throws ExaminationEventException, ServiceNotAvailable;

    ExaminationEvent getExaminationPaper(String examinationEventId) throws Exception;

    StudentSubmitExaminationPaper submitStudentExaminationPaper(StudentSubmitExaminationPaper paperToSubmit) throws SubmitExaminationPaperException, NegativeNumberException, UnexpectedError, EmptyMultipleChoiceQuestion, NullQuestionSet, EmptyQuestionSetTitleOrTopic, QuestionSetAlreadyExists, ServiceNotAvailable, NullQuestionSetQuestion, UserNotFound;

    StudentSubmitExaminationPaper getStudentSubmittedPaper(String studentId, String examId) throws ServiceNotAvailable, UnexpectedError;

    TeacherEvaluationPaperResult submitEvaluatedStudentPaper(TeacherEvaluationPaperResult teacherEvaluationPaperResult) throws ServiceNotAvailable;

    TeacherEvaluationPaperResult getExaminationEventResultByExamIdAndStudentId(String studentId, String examId) throws UnexpectedError, ServiceNotAvailable;

}
