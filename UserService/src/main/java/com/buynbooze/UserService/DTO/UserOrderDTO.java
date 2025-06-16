package com.buynbooze.UserService.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter @Setter
@Builder
public class UserOrderDTO implements Serializable {
    private Object order;
    private String token;
}
