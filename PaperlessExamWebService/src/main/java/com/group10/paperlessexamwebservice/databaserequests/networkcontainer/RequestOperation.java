package com.group10.paperlessexamwebservice.databaserequests.networkcontainer;

import org.springframework.stereotype.Component;

import javax.persistence.Enumerated;

/**
 * Object defining the request operations to the database
 *
 * @author Marin Bilba
 * @version 1.0
 */

public enum RequestOperation {

    //    [User request]
    @Enumerated
    GET_USER_BY_USERNAME,
    CREATE_UPDATE_USER,
    GET_ROLE_ID_BY_NAME,
    GET_USERS_BY_FIRST_NAME,
    DELETE_USER,

    //    [Question sets]

    //    MULTIPLE_CHOICE_SET
    GET_MULTIPLE_CHOICE_SET,
    CREATE_MULTIPLE_CHOICE_SET,

    //    MULTIPLE_CHOICE_SET_QUESTIONS
    CREATE_MULTIPLE_CHOICE_SET_QUESTION,
    GET_MULTIPLE_CHOICE_SET_QUESTION,
    //    MULTIPLE_CHOICE_SET_QUESTIONS_OPTION
    CREATE_MULTIPLE_CHOICE_SET_QUESTION_OPTION,
}
