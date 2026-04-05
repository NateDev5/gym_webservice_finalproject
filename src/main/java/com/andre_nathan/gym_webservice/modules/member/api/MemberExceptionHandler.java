package com.andre_nathan.gym_webservice.modules.member.api;

import com.andre_nathan.gym_webservice.modules.member.application.exception.DuplicateMemberException;
import com.andre_nathan.gym_webservice.modules.member.application.exception.MemberNotFoundException;
import com.andre_nathan.gym_webservice.modules.member.application.exception.MembershipPlanNotFoundException;
import com.andre_nathan.gym_webservice.modules.member.domain.exception.ExpiredMembershipCannotBeActiveException;
import com.andre_nathan.gym_webservice.modules.member.domain.exception.InvalidEmailAddressException;
import com.andre_nathan.gym_webservice.modules.member.domain.exception.InvalidFullNameException;
import com.andre_nathan.gym_webservice.modules.member.domain.exception.InvalidMembershipPeriodException;
import com.andre_nathan.gym_webservice.modules.member.domain.exception.InvalidPhoneNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MemberExceptionHandler {
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleMemberNotFound(MemberNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MembershipPlanNotFoundException.class)
    public ResponseEntity<?> handleMembershipPlanNotFound(MembershipPlanNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<?> handleDuplicateMember(DuplicateMemberException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler({
            InvalidEmailAddressException.class,
            InvalidFullNameException.class,
            InvalidPhoneNumberException.class,
            InvalidMembershipPeriodException.class,
            ExpiredMembershipCannotBeActiveException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<?> handleBadRequest(RuntimeException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildError(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        ));
    }
}
