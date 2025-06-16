package com.buynbooze.Productservice.DTO;

import lombok.*;

@Data
@Getter @Setter
@AllArgsConstructor
@Builder
public class ProductDTO {
    private int id;
    private String brand;
    private String type;
    private Double price;
    private String category;
    private byte[] image;
}
