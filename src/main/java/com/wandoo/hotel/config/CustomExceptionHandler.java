package com.wandoo.hotel.config;

import com.wandoo.hotel.controller.ApiError;
import com.wandoo.hotel.exception.NotFoundException;
import com.wandoo.hotel.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    private static final String VALIDATION_ERROR = "Validation errors occurred";

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(AccessDeniedException ex) {
        logger.error(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), "Access Denied");
        return getObjectResponseEntity(apiError);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(NotFoundException ex) {
        logger.error(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "Information not found");
        return getObjectResponseEntity(apiError);
    }

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(ServiceException ex) {
        logger.error(ex.getMessage(), ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "Could not provide service");
        return getObjectResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleConflict(MethodArgumentTypeMismatchException ex) {
        logger.error(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), VALIDATION_ERROR);
        return getObjectResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        logger.error(ex.getMessage(), ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "error occurred");
        return getObjectResponseEntity(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConflict(ConstraintViolationException ex) {
        logger.error(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), VALIDATION_ERROR);
        return getObjectResponseEntity(apiError);
    }

    private ResponseEntity<Object> getObjectResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}