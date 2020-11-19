package com.group10.databaselayer.controller.networkcontainer;

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
      GET_USER_BY_USERNAME,
    CREATE_USER

}
