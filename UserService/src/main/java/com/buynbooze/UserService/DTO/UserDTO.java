package com.buynbooze.UserService.DTO;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class UserDTO implements Serializable {

    private String username;
    private String password;
}
