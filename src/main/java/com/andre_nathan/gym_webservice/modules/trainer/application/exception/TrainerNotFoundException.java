package com.andre_nathan.gym_webservice.modules.trainer.application.exception;

import com.andre_nathan.gym_webservice.modules.trainer.domain.model.TrainerId;

public class TrainerNotFoundException extends RuntimeException {
    public TrainerNotFoundException(TrainerId trainerId) {
        super("Trainer " + trainerId.value() + " was not found.");
    }
}
