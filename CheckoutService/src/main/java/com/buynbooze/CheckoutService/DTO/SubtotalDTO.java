package com.buynbooze.CheckoutService.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class SubtotalDTO implements Serializable {

    private Double price;
    private int discount;
    private Double deliveryCharges;
    private Double subtotal;
}
