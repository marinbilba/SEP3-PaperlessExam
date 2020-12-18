package com.group10.paperlessexamwebservice.service.exceptions.questionsets;

/**
 * Exception thrown if the question set's questions are empty or null.
 */
public class EmptyQuestionSetQuestions extends Exception {
    public EmptyQuestionSetQuestions(String message) {
        super(message);
    }
}
