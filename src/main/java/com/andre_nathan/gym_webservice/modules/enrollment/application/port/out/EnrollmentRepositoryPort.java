package com.andre_nathan.gym_webservice.modules.enrollment.application.port.out;

import com.andre_nathan.gym_webservice.modules.enrollment.domain.model.Enrollment;
import com.andre_nathan.gym_webservice.modules.enrollment.domain.model.EnrollmentId;
import com.andre_nathan.gym_webservice.modules.member.domain.model.MemberId;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepositoryPort {
    Enrollment save(Enrollment enrollment);
    Optional<Enrollment> findById(EnrollmentId id);
    boolean existsById(EnrollmentId id);
    List<Enrollment> findAll();
    List<Enrollment> findAllForMember (MemberId memberId);
    void deleteById(EnrollmentId id);
}
