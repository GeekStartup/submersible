package com.ocean.submersible.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Locale;

import static com.ocean.submersible.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(SubmersibleException.class)
    public ResponseEntity<ErrorResponse> handleSubmersibleException(SubmersibleException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        String message = messageSource.getMessage(errorCode.getMessageKey(), null, Locale.ENGLISH);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .httpStatus(errorCode.getHttpStatus().value())
                .error(errorCode.getHttpStatus().getReasonPhrase())
                .message(message)
                .details(ex.getDetails())
                .build();

        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        String message = messageSource.getMessage(INTERNAL_SERVER_ERROR.getMessageKey(), null, Locale.ENGLISH);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .httpStatus(INTERNAL_SERVER_ERROR.getHttpStatus().value())
                .error(INTERNAL_SERVER_ERROR.getHttpStatus().getReasonPhrase())
                .message(message)
                .details(Collections.singletonMap("reason", ex.getMessage()))
                .build();

        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR.getHttpStatus());
    }
}
