package com.game.match3server.exception;

import com.game.match3server.web.ErrorCode;
import com.game.match3server.web.Status;
import com.game.match3server.web.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final int FIRST_ERROR_REQUEST = 0;
    @Autowired
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String errorMsg = fieldErrors.get(FIRST_ERROR_REQUEST).getDefaultMessage();
        log.error("ERROR: method arguments are not valid: " +
                fieldErrors.get(FIRST_ERROR_REQUEST).getField() + " = " + errorMsg);
        GenericResponse<?> genericResponse = new GenericResponse<>(new Status(ErrorCode.BAD_REQUEST, errorMsg));
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CommonException.class)
    protected ResponseEntity<GenericResponse<?>> handleAuthException(CommonException e) {
        GenericResponse<?> genericResponse = new GenericResponse<>(new Status((int) e.getCode() ,e.getMessage()));
        log.error("Failed server", e);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}
