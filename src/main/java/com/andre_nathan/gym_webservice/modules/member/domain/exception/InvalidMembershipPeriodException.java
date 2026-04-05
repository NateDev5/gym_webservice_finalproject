package com.andre_nathan.gym_webservice.modules.member.domain.exception;

public class InvalidMembershipPeriodException extends RuntimeException {
    public InvalidMembershipPeriodException() {
        super("Membership end date must be after membership start date");
    }
}
