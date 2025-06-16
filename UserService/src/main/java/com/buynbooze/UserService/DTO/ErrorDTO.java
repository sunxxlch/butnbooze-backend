package com.buynbooze.UserService.DTO;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

@Data
@Setter @Getter
@Builder
public class ErrorDTO {

    private HttpStatus errorCode;
    private String errorMessage;
    private String apiPath;
    private LocalTime time;
}
