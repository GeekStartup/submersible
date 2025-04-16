package com.ocean.submersible.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Builder
@Getter
public class ErrorResponse {
    private final LocalDateTime localDateTime;
    private final int httpStatus;
    private final String error;
    private final String message;
    private final Map<String, String> details;
}
