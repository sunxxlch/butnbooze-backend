package com.buynbooze.UserService.Clients;

import com.buynbooze.UserService.Entities.CheckoutEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "checkoutservice",path = "/checkout")
public interface CheckoutClient {

    @PostMapping("getOrders")
    List<CheckoutEntity> getAllOrder(@RequestBody List<Long> orderIds);

}
