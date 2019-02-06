package com.aldogrand.kfc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.aldogrand.kfc.msg.EventContentType;

/**
 * Created by aldogrand on 06/11/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentType {

    String value();
}
