package com.andre_nathan.gym_webservice.modules.enrollment.application.exception;

import com.andre_nathan.gym_webservice.modules.enrollment.domain.model.EnrollmentId;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException(EnrollmentId enrollmentId) {
        super("Enrollment not found: " + enrollmentId);
    }
}
