package com.group10.databaselayer.exception.questions;

/**
 * Exception Question already exists.
 */
public class QuestionAlreadyExists extends Exception {
    /**
     * Instantiates a new Question already exists object with the error message.
     *
     * @param message the message
     */
    public QuestionAlreadyExists(String message) {
        super(message);
    }
}
