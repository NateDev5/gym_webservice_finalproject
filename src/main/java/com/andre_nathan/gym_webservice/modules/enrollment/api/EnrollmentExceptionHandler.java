package com.andre_nathan.gym_webservice.modules.enrollment.api;

import com.andre_nathan.gym_webservice.modules.enrollment.application.exception.ClassSessionFullException;
import com.andre_nathan.gym_webservice.modules.enrollment.application.exception.EnrollmentNotFoundException;
import com.andre_nathan.gym_webservice.modules.enrollment.application.exception.InvalidMembershipException;
import com.andre_nathan.gym_webservice.modules.enrollment.domain.exception.AlreadyRegisteredException;
import com.andre_nathan.gym_webservice.modules.member.application.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(assignableTypes = EnrollmentController.class)
public class EnrollmentExceptionHandler {

    @ExceptionHandler(EnrollmentNotFoundException.class)
    public ResponseEntity<?> handleEnrollmentNotFound(EnrollmentNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleMemberNotFound(MemberNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ResponseEntity<?> handleAlreadyRegistered(AlreadyRegisteredException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidMembershipException.class)
    public ResponseEntity<?> handleInvalidMembership(InvalidMembershipException ex) {
        return buildError(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(ClassSessionFullException.class)
    public ResponseEntity<?> handleClassSessionFull(ClassSessionFullException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    // TODO: Add handler for TrainerNotFoundException
    // TODO: Add handler for ScheduleNotFoundException

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
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
