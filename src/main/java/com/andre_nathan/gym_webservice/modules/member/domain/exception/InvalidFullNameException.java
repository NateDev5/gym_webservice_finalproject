package com.andre_nathan.gym_webservice.modules.member.domain.exception;

public class InvalidFullNameException extends RuntimeException {
    public InvalidFullNameException() {
        super("Invalid full name");
    }
}
