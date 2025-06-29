package com.buynbooze.CheckoutService.DTO;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter @Setter
public class OrderDTO implements Serializable {
    private Long order_id;
    private AddressDTO address;
    private String payment_type;
    private String status;
    private List<ProductDTO> products;
    private SubtotalDTO subtotal;
}
