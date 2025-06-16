package com.buynbooze.Productservice.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "products")
public class ProductsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;

    private String type;

    private Double price;

    private String category;


    @Column(name = "image", columnDefinition = "BYTEA", nullable = true)
    private byte[] image;


}
