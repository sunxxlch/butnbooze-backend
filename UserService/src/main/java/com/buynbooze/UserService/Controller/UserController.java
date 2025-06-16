package com.buynbooze.UserService.Controller;

import com.buynbooze.UserService.DTO.AccountDTO;
import com.buynbooze.UserService.DTO.TokenDTO;
import com.buynbooze.UserService.DTO.UserDTO;
import com.buynbooze.UserService.DTO.createDTO;
import com.buynbooze.UserService.Services.JwtService;
import com.buynbooze.UserService.Services.JwtServiceRefresh;
import com.buynbooze.UserService.Services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtServiceRefresh jwtServiceRefresh;

    public UserController(UserServiceImpl usi){
        this.userService= usi;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody UserDTO userDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword())
        );

        if(authentication.isAuthenticated()){
            String access = jwtService.generateToken(userDTO.getUsername(),3 * 60 * 1000);
            String refresh = jwtServiceRefresh.generateToken(userDTO.getUsername(),180 * 60 * 1000);
            TokenDTO tokenDTO = TokenDTO.builder()
                    .accessToken(access)
                    .refreshToken(refresh)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed authentication");
        }
    }


    @PostMapping("/createAccount")
    public ResponseEntity<Object> CreateAccount(@RequestBody createDTO create){
        userService.createUser(create);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "UserName",create.getUsername()
        ));
    }

    @GetMapping("/getDetails")
    public ResponseEntity<AccountDTO> getDetails(@RequestBody Map<String,String> body){
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.getDetails(body.get("username"))
        );
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestBody Map<String,String> body){
        userService.resetPassword(body);
        return ResponseEntity.status(HttpStatus.OK).body("Password Reset Successfully");
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok("Token is valid");
    }

    @PutMapping("/addPlacedOrders")
    public ResponseEntity<String> addnewOrders(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        try {
            String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
            String username = jwtService.extractUsername(jwt);

            userService.updateOrders(body, username);

            return ResponseEntity.status(HttpStatus.OK).body("updated new orders Successfully");
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to update orders: " + e.getMessage());
        }
    }


}
