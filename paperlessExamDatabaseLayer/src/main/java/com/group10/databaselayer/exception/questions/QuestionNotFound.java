package com.group10.databaselayer.exception.questions;

/**
 * Exception Question not found.
 */
public class QuestionNotFound extends Exception {
    /**
     * Instantiates a new Question not found object with the error message.
     *
     * @param message the message
     */
    public QuestionNotFound(String message) {
        super(message);
    }
}
