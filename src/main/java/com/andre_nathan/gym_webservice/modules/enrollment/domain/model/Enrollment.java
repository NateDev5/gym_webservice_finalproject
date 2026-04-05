package com.andre_nathan.gym_webservice.modules.enrollment.domain.model;

import com.andre_nathan.gym_webservice.modules.enrollment.domain.exception.AlreadyRegisteredException;
import com.andre_nathan.gym_webservice.modules.member.domain.model.MemberId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Enrollment {
    private final EnrollmentId enrollmentId;
    private MemberId memberId;
    private List<EnrollmentItem> registeredClasses;

    public Enrollment(
            EnrollmentId enrollmentId,
            MemberId memberId,
            List<EnrollmentItem> registeredClasses
    ) {
        this.enrollmentId = Objects.requireNonNull(enrollmentId, "enrollmentId cannot be null");
        this.memberId = Objects.requireNonNull(memberId, "memberId cannot be null");
        this.registeredClasses = List.copyOf(Objects.requireNonNull(registeredClasses, "registeredClasses cannot be null"));
    }

    public EnrollmentId getEnrollmentId() {
        return enrollmentId;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public List<EnrollmentItem> getRegisteredClasses() {
        return registeredClasses;
    }

    public boolean hasRegisteredClasses() {
        return !registeredClasses.isEmpty();
    }

    public void enroll(ClassSessionId classSessionId) {
        // Membership validity must be checked outside this aggregate because it only holds MemberId.
        // TODO: check if enrolled session is full
        Objects.requireNonNull(classSessionId, "classSessionId cannot be null");

        boolean alreadyRegistered = registeredClasses.stream()
                .anyMatch(item -> item.getClassSessionId().equals(classSessionId)
                        && item.getEnrollmentStatus() != EnrollmentStatus.CANCELLED);

        if (alreadyRegistered) {
            throw new AlreadyRegisteredException();
        }

        List<EnrollmentItem> updatedRegisteredClasses = new ArrayList<>(registeredClasses);
        updatedRegisteredClasses.add(
                new EnrollmentItem(
                        UUID.randomUUID(),
                        EnrollmentDate.now(),
                        EnrollmentStatus.ENROLLED,
                        classSessionId,
                        null
                )
        );
        this.registeredClasses = List.copyOf(updatedRegisteredClasses);
    }

    public void cancelEnrollment(ClassSessionId classSessionId) {
        Objects.requireNonNull(classSessionId, "classSessionId cannot be null");

        List<EnrollmentItem> updatedRegisteredClasses = new ArrayList<>(registeredClasses);
        updatedRegisteredClasses.stream()
                .filter(item -> item.getClassSessionId().equals(classSessionId))
                .findFirst()
                .ifPresent(EnrollmentItem::cancel);

        this.registeredClasses = List.copyOf(updatedRegisteredClasses);
    }
}
