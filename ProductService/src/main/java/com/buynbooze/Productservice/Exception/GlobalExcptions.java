package com.buynbooze.Productservice.Exception;

import com.buynbooze.Productservice.DTO.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExcptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> globalexceptions(Exception exception, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder()
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                        .errorMessage(exception.getMessage())
                        .errorEndPoint(webRequest.getDescription(false))
                        .errorTime(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<ErrorDto> productNotFound(ProductNotFoundException productNotFoundException, WebRequest webRequest){
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST,
                productNotFoundException.getMessage(),
                webRequest.getDescription(false),
                LocalDateTime.now()
        );

        return  new ResponseEntity<>(errorDto,HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String,Object> allErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();
        validationErrorList.forEach(vr->{
            String fieldName = ((FieldError) vr).getField();
            String validationMsg = vr.getDefaultMessage();
            allErrors.put(fieldName,validationMsg);
        });
        return new ResponseEntity<>(allErrors, HttpStatus.BAD_REQUEST);
    }
}
