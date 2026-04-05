package com.andre_nathan.gym_webservice.modules.enrollment.application.exception;

import com.andre_nathan.gym_webservice.modules.enrollment.domain.model.ClassSessionId;

public class ClassSessionFullException extends RuntimeException {
    public ClassSessionFullException(ClassSessionId classSessionId) {
        super("Class session is full: " + classSessionId);
    }
}
