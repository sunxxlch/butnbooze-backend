package com.buynbooze.CheckoutService.Clients;

import com.buynbooze.CheckoutService.DTO.UserOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "userservice", path = "/user")
public interface UserServiceClient {

    @PutMapping("/addPlacedOrders")
    ResponseEntity<String> addNewOrders(@RequestBody UserOrderDTO userOrderDTO);

    @GetMapping("/getEmail")
    String getEmail(@RequestParam String username);
}