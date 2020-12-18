package com.group10.paperlessexamwebservice.service.exceptions.questionsets;

/**
 * Exception thrown if the question set already exists.
 */
public class QuestionSetAlreadyExists extends Exception {
    public QuestionSetAlreadyExists(String message) {
        super(message);
    }
}
