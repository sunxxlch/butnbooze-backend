package com.buynbooze.UserService.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter @Setter
@Builder
public class AccountDTO implements Serializable {

    private String username;
    private String email;
    private Long mobile;
    private LocalDate dob;
    private LocalDateTime created_at;

}
