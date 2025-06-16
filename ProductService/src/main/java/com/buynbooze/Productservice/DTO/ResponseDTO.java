package com.buynbooze.Productservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter @AllArgsConstructor
public class ResponseDTO {

    private String statusCode;
    private String statusMsg;
}
