package com.buynbooze.UserService.DTO;

import lombok.*;

@Data
@Setter @Getter
@Builder
public class TokenDTO {

    private String accessToken;
    private String refreshToken;

}
