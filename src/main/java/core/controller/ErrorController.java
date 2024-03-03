package core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import core.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> constraViolationException(ConstraintViolationException e) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setStatusCode(400);
        response.setMessage("Bad request");
        response.setData(null);
        response.setErrors(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<String>> apiException(ResponseStatusException e) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setStatusCode(400);
        response.setMessage("Bad request");
        response.setData(null);
        response.setErrors(e.getReason());

        return ResponseEntity.status(e.getStatusCode()).body(response);
    }
}
