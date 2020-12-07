package com.group10.databaselayer.entity.questions.written;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Hidden annotation used to hide the field or class from gson serialization
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Hidden {
    //some implementation here
}
