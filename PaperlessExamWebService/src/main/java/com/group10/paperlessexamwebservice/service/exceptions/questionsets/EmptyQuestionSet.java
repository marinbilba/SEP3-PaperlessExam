package com.group10.paperlessexamwebservice.service.exceptions.questionsets;

/**
 * Exception thrown if the question set is empty or null.
 */
public class EmptyQuestionSet extends Exception{
    public EmptyQuestionSet(String message)
    {
        super(message);
    }
}
