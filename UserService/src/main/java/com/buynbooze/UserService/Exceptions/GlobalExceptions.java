package com.buynbooze.UserService.Exceptions;

import com.buynbooze.UserService.DTO.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> globalexceptions(Exception exception, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDTO.builder()
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                        .errorMessage(exception.getMessage())
                        .apiPath(webRequest.getDescription(false))
                        .time(LocalTime.now())
                        .build()
                );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> userexistsException(UserAlreadyExistsException userAlreadyExistsException, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .errorCode(HttpStatus.BAD_REQUEST)
                        .errorMessage(userAlreadyExistsException.getMessage())
                        .apiPath(webRequest.getDescription(false))
                        .time(LocalTime.now())
                        .build()
                );
    }

    @ExceptionHandler(OldPasswordNotMatchExcepion.class)
    public ResponseEntity<ErrorDTO> oldPasswordException(OldPasswordNotMatchExcepion oldPasswordNotMatchExcepion, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .errorCode(HttpStatus.BAD_REQUEST)
                        .errorMessage(oldPasswordNotMatchExcepion.getMessage())
                        .apiPath(webRequest.getDescription(false))
                        .time(LocalTime.now())
                        .build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();
        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

}
