package com.group10.databaselayer.dataaccessobject;

import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.entity.studentsubmitpaper.StudentSubmitExaminationPaper;
import com.group10.databaselayer.entity.teacherpaperevaluation.TeacherEvaluationPaperResult;
import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.repositories.examinationevent.IExaminationEventRepository;
import com.group10.databaselayer.repositories.examinationevent.ISubmitExaminationPaperRepository;
import com.group10.databaselayer.repositories.examinationevent.ITeacherEvaluationPaperResultRepository;
import com.group10.databaselayer.repositories.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The type Examination event dao.
 */
@Component
public class ExaminationEventDAO {
    /**
     * The Examination event repository.
     */
    @Autowired
    IExaminationEventRepository examinationEventRepository;

    /**
     * The User repository.
     */
    @Autowired
    IUserRepository userRepository;
    /**
     * The User repository.
     */
    @Autowired
    private ISubmitExaminationPaperRepository submitExaminationPaper;
    @Autowired
    private ITeacherEvaluationPaperResultRepository teacherEvaluationPaperResultRepository;

    /**
     * Create update examination event.
     *
     * @param examinationEvent the examination event
     * @return the examination event
     */
    public ExaminationEvent createUpdate(ExaminationEvent examinationEvent) {
        return examinationEventRepository.save(examinationEvent);

    }

    /**
     * Gets teachers examination events.
     *
     * @param teacherId the teacher id
     * @return the teachers examination events
     */
    public List<ExaminationEvent> getTeachersExaminationEvents(User teacherId) {
        return examinationEventRepository.findByCreatedBy(teacherId);
    }

    /**
     * Gets student examination events.
     *
     * @param user the user
     * @return the student examination events
     */
    public List<ExaminationEvent> getStudentExaminationEvents(User user) {
        return examinationEventRepository.findUsersExaminationEvents(user.getId());
    }

    /**
     * Gets examination event by id.
     *
     * @param examinationEventId the examination event id
     * @return the examination event by id
     */
    public Optional<ExaminationEvent> getExaminationEventById(long examinationEventId) {
        return examinationEventRepository.findById(examinationEventId);
    }


    /**
     * Submit student examination paper student submit examination paper.
     *
     * @param examinationPaperToSubmit the examination paper to submit
     * @return the student submit examination paper
     */
    public StudentSubmitExaminationPaper submitStudentExaminationPaper(StudentSubmitExaminationPaper examinationPaperToSubmit) {
        return submitExaminationPaper.save(examinationPaperToSubmit);
    }

    public StudentSubmitExaminationPaper getStudentSubmittedPaperByStudentIdAndExamId(String studentIdAndExamId) {
        long submitById = Long.parseLong(parseStudentId(studentIdAndExamId));
        long examinationEventId = Long.parseLong(parseExamId(studentIdAndExamId));
//        Optional<User> submitBy = userRepository.findById(submitById);
//        Optional<ExaminationEvent> examinationEvent = examinationEventRepository.findById(examinationEventId);

        return submitExaminationPaper.findByExaminationEvent_IdAndSubmitBy_Id(examinationEventId, submitById);

    }

    private String parseExamId(String studentIdAndExamId) {

        String parsedSting = null;
        if (studentIdAndExamId.contains("&")) {
            parsedSting = studentIdAndExamId.substring(studentIdAndExamId.lastIndexOf("&") + 1);
        }
        return parsedSting;
    }


    private String parseStudentId(String studentIdAndExamId) {
        String parsedSting = null;
        if (studentIdAndExamId.contains("&")) {
            int i = studentIdAndExamId.indexOf("&");
            parsedSting = studentIdAndExamId.substring(0, i);
        }
        return parsedSting;
    }

    public TeacherEvaluationPaperResult submitEvaluatedStudentPaper(TeacherEvaluationPaperResult teacherEvaluationPaperResult) {
        return teacherEvaluationPaperResultRepository.save(teacherEvaluationPaperResult);
    }
    public TeacherEvaluationPaperResult getExaminationEventResultByExamIdAndStudentId(String studentIdAndExamId) {
        long submitById = Long.parseLong(parseStudentId(studentIdAndExamId));
        long examinationEventId = Long.parseLong(parseExamId(studentIdAndExamId));
return teacherEvaluationPaperResultRepository.getExaminationEventResultByExamIdAndStudentId(examinationEventId,submitById);
    }
}
