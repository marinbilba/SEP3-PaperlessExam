package com.group10.databaselayer.repositories.examinationevent;

import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExaminationEvent extends JpaRepository<ExaminationEvent,Long> {
}
