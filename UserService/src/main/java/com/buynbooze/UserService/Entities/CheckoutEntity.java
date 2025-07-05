package com.buynbooze.UserService.Entities;

import com.buynbooze.UserService.DTO.AddressDTO;
import com.buynbooze.UserService.DTO.ProductDTO;
import com.buynbooze.UserService.DTO.SubtotalDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter @Getter @NoArgsConstructor
public class CheckoutEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

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
