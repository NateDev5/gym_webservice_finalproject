package com.andre_nathan.gym_webservice.modules.enrollment.application.service;

import com.andre_nathan.gym_webservice.modules.enrollment.application.exception.EnrollmentNotFoundException;
import com.andre_nathan.gym_webservice.modules.enrollment.application.exception.InvalidMembershipException;
import com.andre_nathan.gym_webservice.modules.enrollment.application.port.out.EnrollmentRepositoryPort;
import com.andre_nathan.gym_webservice.modules.enrollment.domain.model.ClassSessionId;
import com.andre_nathan.gym_webservice.modules.enrollment.domain.model.Enrollment;
import com.andre_nathan.gym_webservice.modules.enrollment.domain.model.EnrollmentId;
import com.andre_nathan.gym_webservice.modules.member.application.exception.MemberNotFoundException;
import com.andre_nathan.gym_webservice.modules.member.application.port.out.MemberRepositoryPort;
import com.andre_nathan.gym_webservice.modules.member.domain.model.Member;
import com.andre_nathan.gym_webservice.modules.member.domain.model.MemberId;
import com.andre_nathan.gym_webservice.modules.member.domain.model.MembershipStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class EnrollmentOrchestrator {
    private final EnrollmentRepositoryPort enrollmentRepository;
    private final MemberRepositoryPort memberRepository;
    // TODO: private final TrainerRepositoryPort trainerRepository;
    // TODO: private final ScheduleRepositoryPort scheduleRepository;

    public EnrollmentOrchestrator(
            EnrollmentRepositoryPort enrollmentRepository,
            MemberRepositoryPort memberRepository
            // TODO: TrainerRepositoryPort trainerRepository,
            // TODO: ScheduleRepositoryPort scheduleRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.memberRepository = memberRepository;
        // TODO: this.trainerRepository = trainerRepository;
        // TODO: this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public Enrollment enrollMemberInClass(String memberId, String classSessionId) {
        MemberId parsedMemberId = MemberId.of(requireText(memberId, "memberId"));
        ClassSessionId parsedClassSessionId = ClassSessionId.of(requireText(classSessionId, "classSessionId"));

        Member member = getMemberById(parsedMemberId);
        validateMembershipIsActive(member);

        // TODO: Validate trainer availability via TrainerRepositoryPort
        // TODO: Validate class session exists and is not full via ScheduleRepositoryPort

        Enrollment enrollment = getOrCreateEnrollmentForMember(parsedMemberId);
        enrollment.enroll(parsedClassSessionId);

        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public Enrollment cancelEnrollment(String memberId, String classSessionId) {
        MemberId parsedMemberId = MemberId.of(requireText(memberId, "memberId"));
        ClassSessionId parsedClassSessionId = ClassSessionId.of(requireText(classSessionId, "classSessionId"));

        getMemberById(parsedMemberId);

        Enrollment enrollment = getEnrollmentForMember(parsedMemberId);
        enrollment.cancelEnrollment(parsedClassSessionId);

        // TODO: Notify schedule module to decrement enrolled count
        // TODO: Process waitlist if applicable

        return enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public Enrollment getEnrollmentById(String enrollmentId) {
        EnrollmentId parsedEnrollmentId = EnrollmentId.of(requireText(enrollmentId, "enrollmentId"));
        return enrollmentRepository.findById(parsedEnrollmentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(parsedEnrollmentId));
    }

    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsForMember(String memberId) {
        MemberId parsedMemberId = MemberId.of(requireText(memberId, "memberId"));
        getMemberById(parsedMemberId);
        return enrollmentRepository.findAllForMember(parsedMemberId);
    }

    @Transactional(readOnly = true)
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    private Member getMemberById(MemberId memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private void validateMembershipIsActive(Member member) {
        if (!member.isMembershipActive() || member.isMembershipExpired()) {
            throw new InvalidMembershipException(member.getMemberId());
        }
    }

    private Enrollment getOrCreateEnrollmentForMember(MemberId memberId) {
        List<Enrollment> enrollments = enrollmentRepository.findAllForMember(memberId);
        if (enrollments.isEmpty()) {
            Enrollment newEnrollment = new Enrollment(
                    EnrollmentId.newId(),
                    memberId,
                    List.of()
            );
            return enrollmentRepository.save(newEnrollment);
        }
        return enrollments.get(0);
    }

    private Enrollment getEnrollmentForMember(MemberId memberId) {
        List<Enrollment> enrollments = enrollmentRepository.findAllForMember(memberId);
        if (enrollments.isEmpty()) {
            throw new EnrollmentNotFoundException(EnrollmentId.of(memberId.value()));
        }
        return enrollments.get(0);
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }

        return value.trim();
    }

    private <T> T requireNonNull(T value, String fieldName) {
        return Objects.requireNonNull(value, fieldName + " cannot be null");
    }
}
