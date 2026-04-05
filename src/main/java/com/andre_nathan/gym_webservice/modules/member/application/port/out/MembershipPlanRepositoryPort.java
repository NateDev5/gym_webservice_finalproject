package com.andre_nathan.gym_webservice.modules.member.application.port.out;

import com.andre_nathan.gym_webservice.modules.member.domain.model.MembershipPlan;

import java.util.Optional;
import java.util.UUID;

public interface MembershipPlanRepositoryPort {
    Optional<MembershipPlan> findById(UUID planId);
}
