package com.andre_nathan.gym_webservice.modules.enrollment.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Relation(collectionRelation = "enrollments", itemRelation = "enrollment")
public class EnrollmentResponse extends RepresentationModel<EnrollmentResponse> {
    private final String enrollmentId;
    private final String memberId;
    private final List<EnrollmentItemResponse> registeredClasses;

    public EnrollmentResponse(
            String enrollmentId,
            String memberId,
            List<EnrollmentItemResponse> registeredClasses
    ) {
        this.enrollmentId = enrollmentId;
        this.memberId = memberId;
        this.registeredClasses = registeredClasses;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public String getMemberId() {
        return memberId;
    }

    public List<EnrollmentItemResponse> getRegisteredClasses() {
        return registeredClasses;
    }

    public record EnrollmentItemResponse(
            UUID registrationId,
            LocalDateTime enrollmentDate,
            String enrollmentStatus,
            String classSessionId,
            Integer seatNumber
            // TODO: String trainerId,
            // TODO: String scheduleId
    ) {
    }
}
