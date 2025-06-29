package com.buynbooze.CheckoutService.DTO;

import lombok.*;

@Data
@Setter @Getter
@Builder
public class ResponseDTO {

    private Long orderId;
    private String statusMsg;
}
