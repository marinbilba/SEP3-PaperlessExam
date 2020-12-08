package com.group10.paperlessexamwebservice.service.exceptions.questionsets.multiplechoice;

public class EmptyOptionAnswerException extends Exception{
    public EmptyOptionAnswerException(String message)
    {
        super(message);
    }
}
