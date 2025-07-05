package com.buynbooze.UserService.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class ProductDTO implements Serializable {

    private int id;

    private String brand;

    private String type;

    private Double price;

    private String category;

    private int quantity;
}
