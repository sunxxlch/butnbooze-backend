package com.buynbooze.CheckoutService.Entities;

import com.buynbooze.CheckoutService.DTO.AddressDTO;
import com.buynbooze.CheckoutService.DTO.ProductDTO;
import com.buynbooze.CheckoutService.DTO.SubtotalDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter @Getter
@Table(name="transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;

    private String user_id;

    private String user_email;

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
