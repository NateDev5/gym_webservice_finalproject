package com.andre_nathan.gym_webservice.modules.member.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMemberRepository extends JpaRepository<MemberJpaEntity, String> {
    boolean existsByEmail(String email);
}
