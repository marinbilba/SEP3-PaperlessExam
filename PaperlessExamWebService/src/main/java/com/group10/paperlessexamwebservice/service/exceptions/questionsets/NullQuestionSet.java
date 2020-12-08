package com.group10.paperlessexamwebservice.service.exceptions.questionsets;

/**
 * Exception thrown if the question set is empty or null.
 */
public class NullQuestionSet extends Exception{
    public NullQuestionSet(String message)
    {
        super(message);
    }
}
