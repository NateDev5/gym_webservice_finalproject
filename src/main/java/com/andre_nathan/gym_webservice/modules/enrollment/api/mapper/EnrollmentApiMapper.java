package com.andre_nathan.gym_webservice.modules.enrollment.api.mapper;

import com.andre_nathan.gym_webservice.modules.enrollment.api.dto.EnrollmentResponse;
import com.andre_nathan.gym_webservice.modules.enrollment.domain.model.Enrollment;
import com.andre_nathan.gym_webservice.modules.enrollment.domain.model.EnrollmentItem;

import java.util.List;

public class EnrollmentApiMapper {

    public static EnrollmentResponse toResponse(Enrollment enrollment) {
        List<EnrollmentResponse.EnrollmentItemResponse> items = enrollment.getRegisteredClasses().stream()
                .map(EnrollmentApiMapper::toItemResponse)
                .toList();

        return new EnrollmentResponse(
                enrollment.getEnrollmentId().value(),
                enrollment.getMemberId().value(),
                items
        );
    }

    private static EnrollmentResponse.EnrollmentItemResponse toItemResponse(EnrollmentItem item) {
        return new EnrollmentResponse.EnrollmentItemResponse(
                item.getRegistrationId(),
                item.getEnrollmentDate().value(),
                item.getEnrollmentStatus().name(),
                item.getClassSessionId().value(),
                item.getSeatNumber()
                // TODO: item.getTrainerId().value(),
                // TODO: item.getScheduleId().value()
        );
    }
}
