package com.buynbooze.CheckoutService.DTO;

import com.buynbooze.CheckoutService.Entities.CheckoutEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@Builder
public class UserOrderDTO {
    private CheckoutEntity order;
    private String token;
}
