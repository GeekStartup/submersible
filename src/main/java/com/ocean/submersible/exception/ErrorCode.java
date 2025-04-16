package com.ocean.submersible.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    //400
    GRID_NOT_FOUND(HttpStatus.NOT_FOUND, "exception.grid_not_found"),
    OBSTACLE_OUTSIDE_GRID(HttpStatus.BAD_REQUEST, "exception.obstacle_outside_grid"),
    PROBE_NOT_FOUND(HttpStatus.NOT_FOUND, "exception.probe_not_found"),
    PROBE_MOVEMENT_OUTSIDE_GRID(HttpStatus.BAD_REQUEST, "exception.probe_movement_outside_grid"),
    PROBE_OBSTACLE_COLLISION(HttpStatus.BAD_REQUEST, "exception.probe_obstacle_collision"),

    //500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "exception.internal_server_error");


    private final HttpStatus httpStatus;
    private final String messageKey;
}
