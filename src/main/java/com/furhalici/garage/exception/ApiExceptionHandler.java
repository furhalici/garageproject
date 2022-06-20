package com.furhalici.garage.exception;

import com.furhalici.garage.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(value = ParkBaseCheckedException.class)
    public ResponseEntity<ErrorResponse> handle(ParkBaseCheckedException exception) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorResponse.builder()
                                .message(exception.getMessage())
                                .success(false)
                                .build()
                );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        log.error("Exception: ", e);
        return ResponseEntity.internalServerError()
                .body(
                        ErrorResponse.builder()
                                .message(":(")
                                .success(false)
                                .build()
                );
    }

}
