//package com.group10.databaselayer.entity.studentsubmitpaper;
//
//import com.group10.databaselayer.entity.examinationevent.ExaminationEvent;
//import com.sun.istack.NotNull;
//import org.hibernate.annotations.CreationTimestamp;
//
//import javax.persistence.*;
//import java.util.Date;
//
//public class StudentSubmitExaminationPaper {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    private Long id;
//    @CreationTimestamp
//    private Date submitTimestamp;
//
//    @OneToOne
//    @NotNull
//    @JoinColumn(name = "fk_examination_event_id")
//    private ExaminationEvent examinationEvent;
//
//   @OneToMany
//}
