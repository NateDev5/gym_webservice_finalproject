package com.andre_nathan.gym_webservice.modules.enrollment.domain.model;

import java.util.Objects;
import java.util.UUID;

public class EnrollmentItem {
    private final UUID registrationId;
    private EnrollmentDate enrollmentDate;
    private EnrollmentStatus enrollmentStatus;
    private ClassSessionId classSessionId;

    //TODO: private TrainerId trainerId;

    //TODO: private ScheduleId scheduleId;

    private Integer seatNumber;

    public EnrollmentItem(
            UUID registrationId,
            EnrollmentDate enrollmentDate,
            EnrollmentStatus enrollmentStatus,
            ClassSessionId classSessionId,
            Integer seatNumber
    ) {
        this.registrationId = Objects.requireNonNull(registrationId, "registrationId cannot be null");
        this.enrollmentDate = Objects.requireNonNull(enrollmentDate, "enrollmentDate cannot be null");
        this.enrollmentStatus = Objects.requireNonNull(enrollmentStatus, "enrollmentStatus cannot be null");
        this.classSessionId = Objects.requireNonNull(classSessionId, "classSessionId cannot be null");
        this.seatNumber = seatNumber;
    }

    public UUID getRegistrationId() {
        return registrationId;
    }

    public EnrollmentDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public ClassSessionId getClassSessionId() {
        return classSessionId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void cancel() {
        this.enrollmentStatus = EnrollmentStatus.CANCELLED;
    }
}
