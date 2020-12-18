package com.group10.paperlessexamwebservice.service.exceptions.questionsets;

/**
 * Exception thrown if the question set title or topic are empty or null.
 */
public class EmptyQuestionSetTitleOrTopic extends Exception {
    public EmptyQuestionSetTitleOrTopic(String message) {
        super(message);
    }
}
