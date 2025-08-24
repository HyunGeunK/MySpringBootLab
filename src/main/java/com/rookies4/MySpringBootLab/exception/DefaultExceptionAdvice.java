package com.rookies4.MySpringBootLab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class DefaultExceptionAdvice {

    // 기존 404 처리 핸들러
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorObject> handleBusinessException(BusinessException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    // [추가] @Valid 유효성 검증 실패 시 400 처리 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorObject errorObject = new ErrorObject();
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        errorObject.setMessage(errorMessage);
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }
}