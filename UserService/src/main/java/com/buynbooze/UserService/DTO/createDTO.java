package com.buynbooze.UserService.DTO;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter @Setter
public class createDTO {

    private String username;
    private String password;
    private String email;
    private Long mobile;
    private LocalDate dob;
}
