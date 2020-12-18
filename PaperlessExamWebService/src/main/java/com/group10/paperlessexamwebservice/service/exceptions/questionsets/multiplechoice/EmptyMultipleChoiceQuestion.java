package com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice;

/**
 * Exception thrown if the multiple choice question  is empty.
 */
public class EmptyMultipleChoiceQuestion extends Exception {
    public EmptyMultipleChoiceQuestion(String message) {
        super(message);
    }
}

