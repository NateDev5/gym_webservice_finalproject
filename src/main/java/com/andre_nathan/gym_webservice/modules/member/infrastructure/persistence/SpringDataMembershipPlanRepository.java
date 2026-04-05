package com.andre_nathan.gym_webservice.modules.member.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataMembershipPlanRepository extends JpaRepository<MembershipPlanJpaEntity, UUID> {
}
