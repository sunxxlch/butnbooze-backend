package com.buynbooze.UserService.DTO;

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
    private String house;
    private String street;
    private String area;
    private String city;
    private int zipcode;
    private String country;
}
