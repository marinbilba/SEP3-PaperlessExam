package com.group10.paperlessexamwebservice.service.examinaationevents;

import com.group10.paperlessexamwebservice.databaserequests.requests.examinationevent.IExaminationEventRequest;
import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.service.exceptions.examinationevent.ExaminationEventException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The type Examination event service.
 */
@Service
public class ExaminationEventService implements IExaminationEventService {
    @Autowired
    private IExaminationEventRequest examinationEventRequest;

    //todo check if only students were assigned
    @Override
    public ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable {
        return examinationEventRequest.createExaminationEvent(examinationEvent);
    }

    @Override
    public List<ExaminationEvent> getTeachersUpcomingExamEvents(String teacherId) throws ServiceNotAvailable, ExaminationEventException {
        List<ExaminationEvent> fetchedExaminationEvents = examinationEventRequest.getTeachersExamEvents(teacherId);
        if (fetchedExaminationEvents == null) {
            throw new ExaminationEventException("This user does not have any scheduled examination events");
        }
        List<ExaminationEvent> upcomingEvents = showOnlyUpcomingExaminationEvents(fetchedExaminationEvents);
        if (upcomingEvents.isEmpty()) {
            throw new ExaminationEventException("This user does not have any scheduled upcoming examination events");

        }
        return upcomingEvents;
    }

    @Override
    public List<ExaminationEvent> getTeachersPassedExamEvents(String teacherId) throws ExaminationEventException, ServiceNotAvailable {
        List<ExaminationEvent> fetchedExaminationEvents = examinationEventRequest.getTeachersExamEvents(teacherId);
        if (fetchedExaminationEvents == null) {
            throw new ExaminationEventException("This user does not have any scheduled examination events");
        }
        List<ExaminationEvent> passedEvents = showOnlyPassedExaminationEvents(fetchedExaminationEvents);
        if (passedEvents.isEmpty()) {
            throw new ExaminationEventException("This user does not have any passed examination events");

        }
        return passedEvents;
    }

    @Override
    public List<ExaminationEvent> getStudentsUpcomingExamEvents(String studentId) throws ExaminationEventException, ServiceNotAvailable {
        List<ExaminationEvent> fetchedExaminationEvents = examinationEventRequest.getStudentsExamEvents(studentId);
        if (fetchedExaminationEvents == null) {
            throw new ExaminationEventException("This user does not have any scheduled examination events");
        }
        List<ExaminationEvent> upcomingEvents = showOnlyUpcomingExaminationEvents(fetchedExaminationEvents);
        if (upcomingEvents.isEmpty()) {
            throw new ExaminationEventException("This user does not have any scheduled upcoming examination events");

        }
        return upcomingEvents;
    }

    @Override
    public List<ExaminationEvent> getStudentsPassedExamEvents(String studentId) throws ExaminationEventException, ServiceNotAvailable {
        List<ExaminationEvent> fetchedExaminationEvents = examinationEventRequest.getStudentsExamEvents(studentId);
        if (fetchedExaminationEvents == null) {
            throw new ExaminationEventException("This user does not have any scheduled examination events");
        }
        List<ExaminationEvent> passedEvents = showOnlyPassedExaminationEvents(fetchedExaminationEvents);
        if (passedEvents.isEmpty()) {
            throw new ExaminationEventException("This user does not have any passed examination events");

        }
        return passedEvents;
    }

    @Override
    public List<ExaminationEvent> getStudentsOngoingExamEvents(String studentId) throws ExaminationEventException, ServiceNotAvailable {
        List<ExaminationEvent> fetchedExaminationEvents = examinationEventRequest.getStudentsExamEvents(studentId);
        if (fetchedExaminationEvents == null) {
            throw new ExaminationEventException("This user does not have any scheduled examination events");
        }
        List<ExaminationEvent> ongoingEvents = showOnlyOngoingExaminationEvents(fetchedExaminationEvents);
        if (ongoingEvents.isEmpty()) {
            throw new ExaminationEventException("This user does not have any ongoing examination events");

        }
        return ongoingEvents;
    }

    private List<ExaminationEvent> showOnlyPassedExaminationEvents(List<ExaminationEvent> fetchedExaminationEvents) {
        List<ExaminationEvent> passedEvents = new ArrayList<>();
        for (var exam : fetchedExaminationEvents) {
            if (exam.getExamDateAndTime().before(new Date(System.currentTimeMillis()))) {
                passedEvents.add(exam);
            }
        }
        return passedEvents;
    }

    private List<ExaminationEvent> showOnlyUpcomingExaminationEvents(List<ExaminationEvent> fetchedExaminationEvents) {
        List<ExaminationEvent> upcomingEvents = new ArrayList<>();
        for (var exam : fetchedExaminationEvents) {
            if (exam.getExamDateAndTime().after(new Date(System.currentTimeMillis()))) {
                upcomingEvents.add(exam);
            }
        }
        return upcomingEvents;
    }

    private List<ExaminationEvent> showOnlyOngoingExaminationEvents(List<ExaminationEvent> fetchedExaminationEvents) {
        List<ExaminationEvent> ongoingExaminationEvents = new ArrayList<>();
        Date date = null;
        for (var exam : fetchedExaminationEvents) {
            Date startDate=null;
            Date endDate=null;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
                Date parsedDate = dateFormat.parse(exam.getExamTimeDuration());
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

                 startDate = (Date) exam.getExamDateAndTime().clone();

                 endDate = (Date) exam.getExamDateAndTime().clone();
                 endDate.setTime(timestamp.getTime()+startDate.getTime());
            } catch (Exception e) { //this generic but you can control another types of exception
                // look the origin of excption
                e.printStackTrace();
            }
            Date currentTimeDate = new Date();
            long currentTime=currentTimeDate.getTime();
            long startTimeInMils=startDate.getTime();
            long endTimeInMils=endDate.getTime();
            if(currentTime>=startTimeInMils&&currentTime<=endTimeInMils){
                ongoingExaminationEvents.add(exam);
            }




        }
        return ongoingExaminationEvents;
    }

    private boolean checkIfCurrentTimeInBetweenRegardlessOfDate(long timeOne, long timeTwo) {

        Calendar calendarOne = Calendar.getInstance();
        calendarOne.setTimeInMillis(timeOne);
        Calendar calendarTwo = Calendar.getInstance();
        calendarTwo.setTimeInMillis(timeTwo);

        Calendar calendarCurrentTime = Calendar.getInstance();
        calendarCurrentTime.set(Calendar.HOUR_OF_DAY, 22);

        Calendar calendarOneToCompare = Calendar.getInstance();
        calendarOneToCompare.setTimeInMillis(calendarCurrentTime.getTimeInMillis());
        calendarOneToCompare.set(Calendar.HOUR_OF_DAY, calendarOne.get(Calendar.HOUR_OF_DAY));
        calendarOneToCompare.set(Calendar.MINUTE, calendarOne.get(Calendar.MINUTE));
        calendarOneToCompare.set(Calendar.SECOND, calendarOne.get(Calendar.SECOND));
        calendarOneToCompare.set(Calendar.MILLISECOND, calendarOne.get(Calendar.MILLISECOND));

        Calendar calendarTwoToCompare = Calendar.getInstance();
        calendarTwoToCompare.setTimeInMillis(calendarCurrentTime.getTimeInMillis());
        calendarTwoToCompare.set(Calendar.HOUR_OF_DAY, calendarTwo.get(Calendar.HOUR_OF_DAY));
        calendarTwoToCompare.set(Calendar.MINUTE, calendarTwo.get(Calendar.MINUTE));
        calendarTwoToCompare.set(Calendar.SECOND, calendarTwo.get(Calendar.SECOND));
        calendarTwoToCompare.set(Calendar.MILLISECOND, calendarTwo.get(Calendar.MILLISECOND));

        int AM_CALENDAR = Calendar.AM;
        int PM_CALENDAR = Calendar.PM;

        if (Integer.parseInt(String.valueOf(calendarOne.get(Calendar.AM_PM))) == PM_CALENDAR
                && Integer.parseInt(String.valueOf(calendarTwo.get(Calendar.AM_PM))) == AM_CALENDAR) {
            calendarTwoToCompare.add(Calendar.DATE, 1);
        }

        return (calendarOneToCompare.compareTo(calendarCurrentTime) < 0
                && calendarCurrentTime.compareTo(calendarTwoToCompare) < 0);
    }
}
