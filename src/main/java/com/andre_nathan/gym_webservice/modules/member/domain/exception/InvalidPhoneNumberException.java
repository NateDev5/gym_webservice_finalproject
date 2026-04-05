package com.andre_nathan.gym_webservice.modules.member.domain.exception;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException() {
        super("Invalid phone number");
    }
}
