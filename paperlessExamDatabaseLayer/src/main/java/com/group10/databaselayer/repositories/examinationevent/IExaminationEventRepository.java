package com.group10.databaselayer.repositories.examinationevent;

import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import com.group10.databaselayer.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IExaminationEventRepository extends JpaRepository<ExaminationEvent, Long> {
    List<ExaminationEvent> findByCreatedBy(User createdBy);


    @Query("select distinct examEvent \n" +
            "from ExaminationEvent examEvents  \n" +
            "join examEvents.usersAssigned users \n" +
            "join users.examinationEvents examEvent \n" +
            "where users.id = :id")
    List<ExaminationEvent> findUsersExaminationEvents(long id);


//    List <ExaminationEvent> findByExaminationEvent()
}
