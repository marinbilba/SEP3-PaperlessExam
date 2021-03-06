package com.group10.paperlessexamwebservice.service.examinaationevents;

import com.group10.paperlessexamwebservice.databaserequests.requests.examinationevent.IExaminationEventRequest;
import com.group10.paperlessexamwebservice.model.examinationevent.ExaminationEvent;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.multiplechoice.QuestionOption;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
import com.group10.paperlessexamwebservice.model.studentsubmitpaper.StudentSubmitExaminationPaper;
import com.group10.paperlessexamwebservice.model.teacherpaperevaluation.TeacherEvaluationPaperResult;
import com.group10.paperlessexamwebservice.service.exceptions.examinationevent.ExaminationEventException;
import com.group10.paperlessexamwebservice.service.exceptions.other.NegativeNumberException;
import com.group10.paperlessexamwebservice.service.exceptions.other.ServiceNotAvailable;
import com.group10.paperlessexamwebservice.service.exceptions.other.UnexpectedError;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.EmptyQuestionSetTitleOrTopic;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.NullQuestionSet;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.QuestionSetAlreadyExists;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.EmptyMultipleChoiceQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice.NullQuestionSetQuestion;
import com.group10.paperlessexamwebservice.service.exceptions.submitpaper.SubmitExaminationPaperException;
import com.group10.paperlessexamwebservice.service.exceptions.user.UserNotFound;
import com.group10.paperlessexamwebservice.service.questionsets.IQuestionSetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Examination event service.
 */
@Service
public class ExaminationEventService implements IExaminationEventService {
    @Autowired
    private IExaminationEventRequest examinationEventRequest;
    @Autowired
    private IQuestionSetsService questionSetsService;


    //todo check if only students were assigned
    @Override
    public ExaminationEvent createExaminationEvent(ExaminationEvent examinationEvent) throws ServiceNotAvailable, UnexpectedError {
        double examTotalScore = 0;

        if (examinationEvent.getMultipleChoiceSets() != null && !examinationEvent.getMultipleChoiceSets().isEmpty()) {
            double multipleChoiceSetScore = calculateTotalMultipleChoiceSetQuestions(examinationEvent.getMultipleChoiceSets(), false);
            examinationEvent.setMultipleChoiceSetsTotalScore(multipleChoiceSetScore);
            examTotalScore += multipleChoiceSetScore;
        }
        if (examinationEvent.getWrittenSets() != null && !examinationEvent.getWrittenSets().isEmpty()) {
            double writtenSetScore = calculateTotalWrittenSetQuestions(examinationEvent.getWrittenSets());
            examinationEvent.setWrittenSetsTotalScore(writtenSetScore);
            examTotalScore += writtenSetScore;
        }
        examinationEvent.setTotalScore(examTotalScore);
        return examinationEventRequest.createExaminationEvent(examinationEvent);
    }

    @Override
    public List<ExaminationEvent> getTeachersUpcomingExamEvents(String teacherId) throws ServiceNotAvailable, ExaminationEventException {
        List<ExaminationEvent> fetchedExaminationEvents = examinationEventRequest.getTeachersExamEvents(teacherId);
        if (fetchedExaminationEvents == null) {
            throw new ExaminationEventException("You do not have any scheduled examination events");
        }
        List<ExaminationEvent> upcomingEvents = showOnlyUpcomingExaminationEvents(fetchedExaminationEvents);
        if (upcomingEvents.isEmpty()) {
            throw new ExaminationEventException("You do not  have any scheduled upcoming examination events");

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

    @Override
    public ExaminationEvent getExaminationPaper(String examinationEventId) throws ServiceNotAvailable, UnexpectedError {
        ExaminationEvent fetchedExaminationEvent = examinationEventRequest.getExaminationEventById(examinationEventId);
        List<WrittenSet> fetchedWrittenSet = examinationEventRequest.getExaminationEventWrittenSets(fetchedExaminationEvent);

        List<WrittenSet> writtenSetWithAllQuestions = new ArrayList<>();
        List<MultipleChoiceSet> multipleChoiceSetWithAllQuestions = new ArrayList<>();
        if (fetchedWrittenSet.isEmpty()) {
            try {
//                Just to log this fact
                throw new Exception("Written Sets for this exam event were NOT FOUND");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (var writtenSet : fetchedWrittenSet) {
                WrittenSet temp = questionSetsService.getWrittenSetWithAllChildElements(writtenSet.getId());
                writtenSetWithAllQuestions.add(temp);
            }

        }
        List<MultipleChoiceSet> fetchedMultipleChoiceSet = examinationEventRequest.getExaminationEventMultipleChoiceSets(fetchedExaminationEvent);
        if (fetchedMultipleChoiceSet.isEmpty()) {
            try {
                //                Just to log this fact
                throw new Exception("Multiple Choice Sets for this exam event were NOT FOUND");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (var multipleChoiceSet : fetchedMultipleChoiceSet) {
                MultipleChoiceSet temp = questionSetsService.getMultipleChoiceSetWithAllChildElements(multipleChoiceSet.getId());
                multipleChoiceSetWithAllQuestions.add(temp);
            }
        }
        fetchedExaminationEvent.setMultipleChoiceSets(multipleChoiceSetWithAllQuestions);
        fetchedExaminationEvent.setWrittenSets(writtenSetWithAllQuestions);
        System.out.println("te");
        return fetchedExaminationEvent;
    }

    @Override
    public StudentSubmitExaminationPaper submitStudentExaminationPaper(StudentSubmitExaminationPaper paperToSubmit) throws SubmitExaminationPaperException, NegativeNumberException, UnexpectedError, EmptyMultipleChoiceQuestion, NullQuestionSet, EmptyQuestionSetTitleOrTopic, QuestionSetAlreadyExists, ServiceNotAvailable, NullQuestionSetQuestion, UserNotFound {
        validateStudentExaminationPaper(paperToSubmit);
        List<MultipleChoiceSet> multipleChoiceSetsToSubmit = paperToSubmit.getSubmitMultipleChoiceSets();
        List<WrittenSet> writtenSetsToSubmit = paperToSubmit.getSubmitWrittenSets();


        if (multipleChoiceSetsToSubmit != null && !multipleChoiceSetsToSubmit.isEmpty()) {
            List<MultipleChoiceSet> multipleChoiceSetsUpdatedState = submitMultipleChoice(multipleChoiceSetsToSubmit);
            List<MultipleChoiceSet> submittedMultipleChoiceSets = getSubmittedMultipleChoiceSet(multipleChoiceSetsUpdatedState);
            paperToSubmit.setSubmitMultipleChoiceSets(submittedMultipleChoiceSets);
        }
        if (writtenSetsToSubmit != null && !writtenSetsToSubmit.isEmpty()) {
            List<WrittenSet> writtenSetsUpdatedState = submitWrittenSet(writtenSetsToSubmit);
            paperToSubmit.setSubmitWrittenSets(writtenSetsUpdatedState);
        }

        StudentSubmitExaminationPaper submittedPaper = examinationEventRequest.submitStudentExaminationPaper(paperToSubmit);
        if (submittedPaper == null) {
            throw new UnexpectedError("Something went wrong");
        }


        return submittedPaper;
    }

    @Override
    public StudentSubmitExaminationPaper getStudentSubmittedPaper(String studentId, String examId) throws ServiceNotAvailable, UnexpectedError {
        StudentSubmitExaminationPaper submitExaminationPaper = examinationEventRequest.getStudentSubmittedPaperByStudentIdAndExamId(studentId, examId);
        if (submitExaminationPaper == null) {
            throw new UnexpectedError("Something went wrong");
        }
        List<MultipleChoiceSet> multipleChoiceSetsWithQuestions = new ArrayList<>();
        List<WrittenSet> writtenSetsWithQuestions = new ArrayList<>();
        // Get question sets questions
        if (!submitExaminationPaper.getSubmitMultipleChoiceSets().isEmpty() && submitExaminationPaper.getSubmitMultipleChoiceSets() != null) {
            for (var multipleChoiceSet : submitExaminationPaper.getSubmitMultipleChoiceSets()) {
                MultipleChoiceSet temp = questionSetsService.getMultipleChoiceSetWithAllChildElements(multipleChoiceSet.getId());
                multipleChoiceSetsWithQuestions.add(temp);
            }
            List<MultipleChoiceSet> verifiedMultipleChoiceSet = checkMultipleChoiceAnswersAndSetTheCorrectScore(multipleChoiceSetsWithQuestions, submitExaminationPaper.getExaminationEvent());
            submitExaminationPaper.setSubmitMultipleChoiceSets(verifiedMultipleChoiceSet);
        }
        if (!submitExaminationPaper.getSubmitWrittenSets().isEmpty() && submitExaminationPaper.getSubmitWrittenSets() != null) {
            for (var writtenSet : submitExaminationPaper.getSubmitWrittenSets()) {
                WrittenSet temp = questionSetsService.getWrittenSetWithAllChildElements(writtenSet.getId());
                writtenSetsWithQuestions.add(temp);
            }
            submitExaminationPaper.setSubmitWrittenSets(writtenSetsWithQuestions);
        }
        return submitExaminationPaper;
    }

    //todo check if teacher has put wrritten set score
    @Override
    public TeacherEvaluationPaperResult submitEvaluatedStudentPaper(TeacherEvaluationPaperResult teacherEvaluationPaperResult) throws ServiceNotAvailable {
        double studentScore = 0;

        List<MultipleChoiceSet> multipleChoiceQuestionsTemp = teacherEvaluationPaperResult.getStudentSubmitExaminationPaper().getSubmitMultipleChoiceSets();
        List<WrittenSet> writtenSetsTemp = teacherEvaluationPaperResult.getStudentSubmitExaminationPaper().getSubmitWrittenSets();

        if (multipleChoiceQuestionsTemp != null && !multipleChoiceQuestionsTemp.isEmpty()) {
            double multipleChoiceSetScore = calculateTotalMultipleChoiceSetQuestions(multipleChoiceQuestionsTemp, true);
            teacherEvaluationPaperResult.setMultipleChoiceSetsTotalScore(multipleChoiceSetScore);
            studentScore += multipleChoiceSetScore;
        }
        if (writtenSetsTemp != null && !writtenSetsTemp.isEmpty()) {
            double writtenSetScore = calculateStudentTotalWrittenSetQuestions(writtenSetsTemp);
            teacherEvaluationPaperResult.setWrittenSetsTotalScore(writtenSetScore);
            studentScore += writtenSetScore;
        }
        teacherEvaluationPaperResult.setScore(studentScore);

        return examinationEventRequest.submitEvaluatedStudentPaper(teacherEvaluationPaperResult);


    }

    @Override
    public TeacherEvaluationPaperResult getExaminationEventResultByExamIdAndStudentId(String studentId, String examId) throws UnexpectedError, ServiceNotAvailable {
        TeacherEvaluationPaperResult evaluationPaperResult = examinationEventRequest.getExaminationEventResultByExamIdAndStudentId(studentId, examId);
        if (evaluationPaperResult == null) {
            throw new UnexpectedError("Evaluation paper result not found");
        }
        return evaluationPaperResult;
    }

    private double calculateStudentTotalWrittenSetQuestions(List<WrittenSet> submitWrittenSets) {
        double score = 0;
        for (var writtenSet : submitWrittenSets) {
            for (var question : writtenSet.getWrittenQuestions()) {
                score += question.getStudentScore();
            }
        }
        return score;
    }

    private double calculateTotalWrittenSetQuestions(List<WrittenSet> submitWrittenSets) throws ServiceNotAvailable, UnexpectedError {
        double score = 0;
        for (var writtenSet : submitWrittenSets) {
            var questionSet = questionSetsService.getWrittenSetWithAllChildElements(writtenSet.getId());

            for (var question : questionSet.getWrittenQuestions()) {
                score += question.getQuestionScore();
            }
        }
        return score;
    }

    private double calculateTotalMultipleChoiceSetQuestions(List<MultipleChoiceSet> submitMultipleChoiceSets, boolean teacherEvaluation) throws ServiceNotAvailable {
        double score = 0;
        if (!teacherEvaluation) {
            for (var multipleChoiceSet : submitMultipleChoiceSets) {
                var questionSet = questionSetsService.getMultipleChoiceSetWithAllChildElements(multipleChoiceSet.getId());
                for (var question : questionSet.getMultipleChoiceQuestionList()
                ) {
                    score += question.getScore();
                }
            }
        } else {

            for (var multipleChoiceSet : submitMultipleChoiceSets) {
                for (var question : multipleChoiceSet.getMultipleChoiceQuestionList()
                ) {
                    score += question.getScore();
                }
            }
        }
        return score;
    }

    private List<MultipleChoiceSet> checkMultipleChoiceAnswersAndSetTheCorrectScore(List<MultipleChoiceSet> multipleChoiceSet, ExaminationEvent examinationEvent) throws ServiceNotAvailable {
        // set with correct answers
        List<MultipleChoiceSet> originalMultipleChoiceSets = examinationEventRequest.getExaminationEventMultipleChoiceSets(examinationEvent);

        // add children to original multiple choice set
        List<MultipleChoiceSet> originalMultipleChoiceSetsWithChildren = new ArrayList<>();
        for (var multipleChoiceSetWithoutChildren : originalMultipleChoiceSets) {
            originalMultipleChoiceSetsWithChildren.add(questionSetsService.getMultipleChoiceSetWithAllChildElements(multipleChoiceSetWithoutChildren.getId()));
        }
//        validate question options if different set question score to 0
        for (int i = 0; i < originalMultipleChoiceSetsWithChildren.size(); i++) {
            QuestionOption originalQuestionOption1 = originalMultipleChoiceSetsWithChildren.get(i).getMultipleChoiceQuestionList().get(i).getQuestionOptions().get(i);
            QuestionOption studentQuestionOption1 = multipleChoiceSet.get(i).getMultipleChoiceQuestionList().get(i).getQuestionOptions().get(i);

            if (originalQuestionOption1.getCorrectAnswer() != studentQuestionOption1.getCorrectAnswer()) {
                multipleChoiceSet.get(i).getMultipleChoiceQuestionList().get(i).setScore(0);
            }
        }
        return multipleChoiceSet;
    }


    private List<WrittenSet> submitWrittenSet(List<WrittenSet> writtenSetsToSubmit) throws UnexpectedError, EmptyMultipleChoiceQuestion, NullQuestionSet, EmptyQuestionSetTitleOrTopic, UserNotFound, ServiceNotAvailable, QuestionSetAlreadyExists {
        List<WrittenSet> writtenSetUpdatedState = new ArrayList<>();
        for (var writtenSet : writtenSetsToSubmit) {
            writtenSet.setSubmittedWrittenSet(true);
            writtenSet.setId(null);
            for (var question : writtenSet.getWrittenQuestions()) {
                question.setSubmittedQuestion(true);
                question.setId(null);
            }
            WrittenSet temp = questionSetsService.createWrittenSet(writtenSet);
            writtenSetUpdatedState.add(temp);
        }
        return writtenSetUpdatedState;
    }

    private List<MultipleChoiceSet> submitMultipleChoice(List<MultipleChoiceSet> multipleChoiceSetsToSubmit) throws ServiceNotAvailable, UnexpectedError, UserNotFound, EmptyQuestionSetTitleOrTopic, NullQuestionSet, QuestionSetAlreadyExists, NullQuestionSetQuestion, NegativeNumberException, EmptyMultipleChoiceQuestion {
        List<MultipleChoiceSet> multipleChoiceSetsUpdatedState = new ArrayList<>();
        for (var multipleChoiceSet : multipleChoiceSetsToSubmit) {
            multipleChoiceSet.setSubmittedMultipleChoiceSet(true);
            multipleChoiceSet.setId(null);
            for (var submitQuestion : multipleChoiceSet.getMultipleChoiceQuestionList()) {
                submitQuestion.setSubmittedMultipleChoiceQuestion(true);
                submitQuestion.setId(null);
                // questionSetsService.addMultipleChoiceQuestion(submitQuestion);
                for (var submitQuestionOption : submitQuestion.getQuestionOptions()) {
                    submitQuestionOption.setSubmittedQuestionOption(true);
                    submitQuestionOption.setId(null);
                    //   questionSetsService.addMultipleChoiceQuestionOption(submitQuestionOption);
                }
            }
            MultipleChoiceSet temp = questionSetsService.createMultipleChoiceSet(multipleChoiceSet);
            multipleChoiceSetsUpdatedState.add(temp);
        }
        return multipleChoiceSetsUpdatedState;
    }

    private List<MultipleChoiceSet> getSubmittedMultipleChoiceSet(List<MultipleChoiceSet> multipleChoiceSetsUpdatedState) throws ServiceNotAvailable {
        List<MultipleChoiceSet> fetchedMultipleChoiceSets = new ArrayList<>();
        for (var multipleChoiceSet : multipleChoiceSetsUpdatedState) {
            fetchedMultipleChoiceSets.add(questionSetsService.getMultipleChoiceSetWithAllChildElements(multipleChoiceSet.getId()));
        }
        return fetchedMultipleChoiceSets;
    }

    private boolean validateStudentExaminationPaper(StudentSubmitExaminationPaper paperToSubmit) throws SubmitExaminationPaperException {
        if (paperToSubmit == null) {
            throw new SubmitExaminationPaperException("Paper to submit is null");
        } else if (paperToSubmit.getSubmitBy() == null) {
            throw new SubmitExaminationPaperException("Paper doesn't have an author");
        } else if (paperToSubmit.getExaminationEvent() == null) {
            throw new SubmitExaminationPaperException("Examination event is not set");
        }
        return true;
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

    // todo bug here, when examDuration is 1h or less the system can not calculate the passed milliseconds
    // our solution was adding 1h to eliminate the time zone offset but that is not the best choice
    private List<ExaminationEvent> showOnlyOngoingExaminationEvents(List<ExaminationEvent> fetchedExaminationEvents) {
        List<ExaminationEvent> ongoingExaminationEvents = new ArrayList<>();
        Date date = null;
        for (var exam : fetchedExaminationEvents) {
            Date startDate = null;
            Date endDate = null;
            try {
                String examDuration = exam.getExamTimeDuration();

                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                dateFormat.setTimeZone(TimeZone.getDefault());
                Date parsedDate = dateFormat.parse(examDuration);

                System.out.println(parsedDate.getTime());
                Timestamp timestamp = new Timestamp(parsedDate.getTime());

                startDate = (Date) exam.getExamDateAndTime().clone();
                endDate = (Date) exam.getExamDateAndTime().clone();

                long originalTime = timestamp.getTime() + 3600000;
                long addTime = startDate.getTime() + 3600000;
                long result = originalTime + addTime;
                endDate.setTime(result);
            } catch (Exception e) { //this generic but you can control another types of exception
                // look the origin of excption
                e.printStackTrace();
            }
            Date currentTimeDate = new Date();
            long currentTime = currentTimeDate.getTime();
            long startTimeInMils = startDate.getTime();
            long endTimeInMils = endDate.getTime();
            if (currentTime >= startTimeInMils && currentTime <= endTimeInMils) {
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
