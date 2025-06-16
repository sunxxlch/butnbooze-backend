package com.buynbooze.Productservice.DTO;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Getter @Setter @AllArgsConstructor
@Builder
public class ErrorDto {
    private HttpStatus errorCode;
    private String errorMessage;
    private String errorEndPoint;
    private LocalDateTime errorTime;
}
