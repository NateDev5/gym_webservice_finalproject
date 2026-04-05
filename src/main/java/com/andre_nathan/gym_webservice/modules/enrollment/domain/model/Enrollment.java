package com.andre_nathan.gym_webservice.modules.enrollment.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Enrollment {
    private UUID enrollmentId;
    private UUID memberId;
    private LocalDateTime enrollmentData;
    private EnrollmentStatus status;
    private List<EnrollmentItem> registredClasses;

    public void enroll (UUID classSessionId) {

    }

    public void cancelEnrollment() {

    }
}
