package com.buynbooze.CheckoutService.Entities;

import com.buynbooze.CheckoutService.DTO.AddressDTO;
import com.buynbooze.CheckoutService.DTO.ProductDTO;
import com.buynbooze.CheckoutService.DTO.SubtotalDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter @Getter @NoArgsConstructor
@Table(name = "orders")
public class CheckoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

    private LocalDateTime created_at;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private AddressDTO address;

    private String payment_type;

    private String status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<ProductDTO> products;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private SubtotalDTO subtotal;

}
