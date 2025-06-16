package com.buynbooze.CheckoutService.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "userservice", path = "/user")
public interface UserServiceClient {

    @PutMapping("/addPlacedOrders")
    ResponseEntity<String> addNewOrders(@RequestBody Map<String, String> body);
}
