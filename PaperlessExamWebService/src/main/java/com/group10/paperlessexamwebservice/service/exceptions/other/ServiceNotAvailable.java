package com.group10.paperlessexamwebservice.service.exceptions.other;

/**
 * Exception thrown if there are connection problems with the third tier
 */
public class ServiceNotAvailable extends Exception {
    public ServiceNotAvailable(String message) {
        super(message);
    }
}
