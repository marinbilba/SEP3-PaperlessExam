package com.group10.databaselayer.dataaccessobject;

import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.repositories.examinationevent.IExaminationEventRepository;
import com.group10.databaselayer.repositories.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class ExaminationEventDAO {
    @Autowired
    IExaminationEventRepository examinationEventRepository;

    @Autowired
    IUserRepository userRepository;

    public ExaminationEvent createUpdate(ExaminationEvent examinationEvent) {
     return examinationEventRepository.save(examinationEvent);

    }

    public List<ExaminationEvent> getTeachersExaminationEvents(User teacherId) {
        return examinationEventRepository.findByCreatedBy(teacherId);
    }

    public List<ExaminationEvent> getStudentExaminationEvents(User user) {
     return examinationEventRepository.findUsersExaminationEvents(user.getId());
    }
}
