package com.andre_nathan.gym_webservice.modules.trainer.domain.exception;

public class InvalidCertificationPeriodException extends RuntimeException {
    public InvalidCertificationPeriodException() {
        super("Certification expiry date must be after the issued date.");
    }
}
