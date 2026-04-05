package com.andre_nathan.gym_webservice.modules.member.application.exception;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException(String emailAddress) {
        super("Member already exists with email: " + emailAddress);
    }
}
