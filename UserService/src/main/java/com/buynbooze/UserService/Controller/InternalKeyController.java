package com.buynbooze.UserService.Controller;

import com.buynbooze.UserService.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class InternalKeyController {

    @Autowired
    private JwtService jwtService;

    @GetMapping("/jwt-key")
    public ResponseEntity<String> getJwtSecretKey(@RequestHeader("Internal-Token") String internalToken) {
        if (!internalToken.equals("super-internal-secret")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
        }
        return ResponseEntity.ok(jwtService.getSecretKey());
    }
}
