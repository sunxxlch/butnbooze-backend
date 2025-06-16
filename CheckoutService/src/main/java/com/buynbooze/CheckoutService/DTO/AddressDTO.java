package com.buynbooze.CheckoutService.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter @Setter
public class AddressDTO implements Serializable {

    private String first_name;
    private String last_name;
    private Long mobile;
    private String address;
    private String city;
    private String state;
    private int zipcode;
}
