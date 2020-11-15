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

//    User request
@Enumerated
    USERNAME_EXISTS
}
