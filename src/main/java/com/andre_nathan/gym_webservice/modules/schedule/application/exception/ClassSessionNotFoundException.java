package com.andre_nathan.gym_webservice.modules.schedule.application.exception;

public class ClassSessionNotFoundException extends RuntimeException {
    public ClassSessionNotFoundException(String classSessionId) {
        super("Class session " + classSessionId + " was not found.");
    }
}
