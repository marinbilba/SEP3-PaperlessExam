package com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice;
/**
 * Exception thrown if the multiple choice question is null.
 */
public class NullQuestionSetQuestion extends Exception {
    public NullQuestionSetQuestion(String message)
    {
        super(message);
    }
}
