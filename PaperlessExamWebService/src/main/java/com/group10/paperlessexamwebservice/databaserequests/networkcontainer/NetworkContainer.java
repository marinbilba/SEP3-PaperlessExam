package com.group10.paperlessexamwebservice.databaserequests.networkcontainer;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * NetworkContainer class is used to send objects across the network
 *
 * @author Marin Bilba
 * @version 1.0
 */
public class NetworkContainer implements Serializable {
    private RequestOperation requestOperation;
    private Object object;
    private HttpStatus httpStatus;
    private String message;

    public NetworkContainer(Object object) {
        this.object = object;
    }

    public NetworkContainer(RequestOperation requestOperation, Object object) {
        this.requestOperation = requestOperation;
        this.object = object;
    }

    public NetworkContainer(RequestOperation requestOperation, Object object, HttpStatus httpStatus, String message) {
       this.requestOperation=requestOperation;
        this.object = object;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public RequestOperation getRequestOperation() {
        return requestOperation;
    }

    public void setRequestOperation(RequestOperation requestOperation) {
        this.requestOperation = requestOperation;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
