package com.andre_nathan.gym_webservice.modules.trainer.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTrainerRepository extends JpaRepository<TrainerJpaEntity, String> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndTrainerIdNot(String email, String trainerId);
}
