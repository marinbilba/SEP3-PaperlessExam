package com.group10.databaselayer.entity.examinationevent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ExaminationEvent {
    @GeneratedValue()
    @Id
    private Long id;
    private String examTitle;
}
