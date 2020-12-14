package com.group10.databaselayer.repositories.examinationevent;

import com.group10.databaselayer.entity.studentsubmitpaper.StudentSubmitExaminationPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubmitExaminationPaper extends JpaRepository<StudentSubmitExaminationPaper,Long> {
}
