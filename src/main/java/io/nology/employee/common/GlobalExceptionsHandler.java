package io.nology.employee.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.nology.employee.common.exceptions.NotFoundException;
import io.nology.employee.common.exceptions.UnprocessableContentException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice 
public class GlobalExceptionsHandler {
    
     @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest req) {
        ApiErrorResponse response = ApiErrorResponse.of(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnprocessableContentException.class)
    public ResponseEntity<ApiErrorResponse> handleUnprocessableContentException(UnprocessableContentException ex, HttpServletRequest req)
    {
        ApiErrorResponse response = ApiErrorResponse.of(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_CONTENT);
    }

}
