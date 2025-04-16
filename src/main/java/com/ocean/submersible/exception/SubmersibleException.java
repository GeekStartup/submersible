package com.ocean.submersible.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
@Builder
public class SubmersibleException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Map<String, String> details;

    public SubmersibleException(ErrorCode errorCode) {
        this(errorCode, null);
    }
}
