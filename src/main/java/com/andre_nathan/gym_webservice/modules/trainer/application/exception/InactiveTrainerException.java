package com.andre_nathan.gym_webservice.modules.trainer.application.exception;

import com.andre_nathan.gym_webservice.modules.trainer.domain.model.TrainerId;

public class InactiveTrainerException extends RuntimeException {
    public InactiveTrainerException(TrainerId trainerId) {
        super("Trainer " + trainerId.value() + " is inactive and cannot be assigned.");
    }
}
