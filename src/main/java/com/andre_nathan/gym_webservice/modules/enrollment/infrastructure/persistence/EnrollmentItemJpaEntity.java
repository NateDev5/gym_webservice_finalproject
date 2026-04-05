package com.andre_nathan.gym_webservice.modules.enrollment.infrastructure.persistence;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollment_items")
public class EnrollmentItemJpaEntity {
    @Id
    @Column(name = "registration_id", nullable = false, updatable = false)
    public UUID registrationId;

    @Column(name = "enrollment_date", nullable = false)
    public LocalDateTime enrollmentDate;

    @Column(name = "enrollment_status", nullable = false)
    public String enrollmentStatus;

    @Column(name = "class_session_id", nullable = false)
    public String classSessionId;

    // TODO: @Column(name = "trainer_id")
    // TODO: public String trainerId;

    // TODO: @Column(name = "schedule_id")
    // TODO: public String scheduleId;

    @Column(name = "seat_number")
    public Integer seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    public EnrollmentJpaEntity enrollment;

    protected EnrollmentItemJpaEntity() {
    }
}
