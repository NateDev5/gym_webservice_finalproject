package com.andre_nathan.gym_webservice.shared.web;

import java.util.List;
import java.time.Instant;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> details
) { }
