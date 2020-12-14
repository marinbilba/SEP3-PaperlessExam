package com.group10.databaselayer.dataaccessobject;

import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.studentsubmitpaper.StudentSubmitExaminationPaper;
import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.repositories.examinationevent.IExaminationEventRepository;
import com.group10.databaselayer.repositories.examinationevent.ISubmitExaminationPaper;
import com.group10.databaselayer.repositories.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
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
    private ISubmitExaminationPaper submitExaminationPaper;

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
}
