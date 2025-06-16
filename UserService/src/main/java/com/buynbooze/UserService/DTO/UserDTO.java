package com.buynbooze.UserService.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter @Getter @NoArgsConstructor
public class UserDTO {

    private String username;
    private String password;
}
