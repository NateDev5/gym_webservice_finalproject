package com.andre_nathan.gym_webservice.modules.trainer.domain.exception;

public class InvalidTrainerSpecialtyException extends RuntimeException {
    public InvalidTrainerSpecialtyException() {
        super("Trainer specialty must contain at least 3 characters.");
    }
}
