package com.group10.paperlessexamwebservice.model.teacherpaperevaluation;


import com.group10.paperlessexamwebservice.model.studentsubmitpaper.StudentSubmitExaminationPaper;
import com.group10.paperlessexamwebservice.model.user.User;

import java.util.Date;

/**
 * The type Teacher evaluation paper result.
 */

public class TeacherEvaluationPaperResult {

    private Long id;

    private Date submitTimestamp;

    private User evaluatedBy;


    private StudentSubmitExaminationPaper studentSubmitExaminationPaper;

    private double score;
    private double multipleChoiceSetsTotalScore;
    private double writtenSetsTotalScore;
    public TeacherEvaluationPaperResult() {
    }

    /**
     * Instantiates a new Teacher evaluation paper result.
     *
     * @param submitTimestamp               the submit timestamp
     * @param evaluatedBy                   the evaluated by
     * @param studentSubmitExaminationPaper the student submit examination paper
     * @param score                         the score
     */
    public TeacherEvaluationPaperResult(Date submitTimestamp, User evaluatedBy, StudentSubmitExaminationPaper studentSubmitExaminationPaper, double score) {
        this.submitTimestamp = submitTimestamp;
        this.evaluatedBy = evaluatedBy;
        this.studentSubmitExaminationPaper = studentSubmitExaminationPaper;
        this.score = score;
    }

    public double getMultipleChoiceSetsTotalScore() {
        return multipleChoiceSetsTotalScore;
    }

    public void setMultipleChoiceSetsTotalScore(double multipleChoiceSetsTotalScore) {
        this.multipleChoiceSetsTotalScore = multipleChoiceSetsTotalScore;
    }

    public double getWrittenSetsTotalScore() {
        return writtenSetsTotalScore;
    }

    public void setWrittenSetsTotalScore(double writtenSetsTotalScore) {
        this.writtenSetsTotalScore = writtenSetsTotalScore;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets submit timestamp.
     *
     * @return the submit timestamp
     */
    public Date getSubmitTimestamp() {
        return submitTimestamp;
    }

    /**
     * Sets submit timestamp.
     *
     * @param submitTimestamp the submit timestamp
     */
    public void setSubmitTimestamp(Date submitTimestamp) {
        this.submitTimestamp = submitTimestamp;
    }

    /**
     * Gets evaluated by.
     *
     * @return the evaluated by
     */
    public User getEvaluatedBy() {
        return evaluatedBy;
    }

    /**
     * Sets evaluated by.
     *
     * @param evaluatedBy the evaluated by
     */
    public void setEvaluatedBy(User evaluatedBy) {
        this.evaluatedBy = evaluatedBy;
    }

    /**
     * Gets student submit examination paper.
     *
     * @return the student submit examination paper
     */
    public StudentSubmitExaminationPaper getStudentSubmitExaminationPaper() {
        return studentSubmitExaminationPaper;
    }

    /**
     * Sets student submit examination paper.
     *
     * @param studentSubmitExaminationPaper the student submit examination paper
     */
    public void setStudentSubmitExaminationPaper(StudentSubmitExaminationPaper studentSubmitExaminationPaper) {
        this.studentSubmitExaminationPaper = studentSubmitExaminationPaper;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(double score) {
        this.score = score;
    }
}
