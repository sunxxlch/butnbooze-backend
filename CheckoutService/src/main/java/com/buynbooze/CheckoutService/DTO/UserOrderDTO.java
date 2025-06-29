package com.buynbooze.CheckoutService.DTO;

import com.buynbooze.CheckoutService.Entities.CheckoutEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter @Setter
@Builder
public class UserOrderDTO implements Serializable {
    private Long order;
    private String token;
}
