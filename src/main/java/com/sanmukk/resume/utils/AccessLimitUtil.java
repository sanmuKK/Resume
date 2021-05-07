package com.sanmukk.resume.utils;


import java.lang.annotation.*;


@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimitUtil {

    int times() default 3;

    int second() default 10;
}