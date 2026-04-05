package com.andre_nathan.gym_webservice.modules.member.application.port.out;

import com.andre_nathan.gym_webservice.modules.member.domain.model.Member;
import com.andre_nathan.gym_webservice.modules.member.domain.model.MemberId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepositoryPort {
    Member save(Member member);
    Optional<Member> findById(MemberId id);
    boolean existsByEmail(String email);
    List<Member> findAll();
    void deleteById(MemberId id);
}
