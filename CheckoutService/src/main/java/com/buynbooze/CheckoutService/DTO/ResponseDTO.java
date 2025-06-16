package com.buynbooze.CheckoutService.DTO;

import lombok.*;

@Data
@Setter @Getter
@Builder
public class ResponseDTO {

    private int orderId;
    private String statusMsg;
}
