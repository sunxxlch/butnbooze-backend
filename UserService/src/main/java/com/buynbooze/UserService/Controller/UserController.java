package com.buynbooze.UserService.Controller;

import com.buynbooze.UserService.DTO.*;
import com.buynbooze.UserService.Entities.CheckoutEntity;
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

import java.util.List;
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
            String access = jwtService.generateToken(userDTO.getUsername(),10 * 60 * 1000);
            String refresh = jwtServiceRefresh.generateRefreshToken(userDTO.getUsername(),300 * 60 * 1000);
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
    public ResponseEntity<AccountDTO> getDetails(@RequestParam String username){
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.getDetails(username)
        );
    }

    @GetMapping("/getEmail")
    public String getEmail(@RequestParam String username){
        System.out.println("getting email");
        return userService.getEmail(username);
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
    public ResponseEntity<String> addnewOrders(@RequestBody UserOrderDTO userOrderDTO) {
        System.out.println("entered new orders");
        String token = userOrderDTO.getToken();
        try {
            String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
            String username = jwtService.extractUsername(jwt);
            System.out.println(userOrderDTO.getOrder());
            userService.updateOrders(userOrderDTO.getOrder(), username);

            return ResponseEntity.status(HttpStatus.OK).body("updated new orders Successfully");
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to update orders: " + e.getMessage());
        }
    }

    @GetMapping("/getOrders")
    public List<Long> getOrders(@RequestParam String username){
        return userService.getOrders(username);
    }

    @GetMapping("/getOrdersDetails")
    public List<CheckoutEntity> getOrdersDetails(@RequestParam String username){
        return userService.getOrdersDetails(username);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshExpiredToken(@RequestBody Map<String,String> body){
        String token = body.get("refreshToken");
        if(token!=null){
            if (jwtServiceRefresh.validateRefreshToken(token)) {
                String newAccessToken = jwtService.generateToken(jwtServiceRefresh.RefreshextractUsername(token),10 * 60 * 1000);
                return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("token is null");
        }
    }


}
