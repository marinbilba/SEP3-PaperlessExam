package com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice;


/**
 * Exception thrown if the multiple choice set option is empty.
 */
public class MultipleChoiceQuestionOptionError extends Exception{
    public MultipleChoiceQuestionOptionError(String message)
    {
        super(message);
    }
}
