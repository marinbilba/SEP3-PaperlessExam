package com.group10.databaselayer.repositories.examinationevent;

import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IExaminationEventRepository extends JpaRepository<ExaminationEvent,Long> {
    List<ExaminationEvent> findByCreatedBy(User createdBy);
}
