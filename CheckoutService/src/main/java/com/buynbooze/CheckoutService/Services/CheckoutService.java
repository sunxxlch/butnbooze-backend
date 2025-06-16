package com.buynbooze.CheckoutService.Services;

import com.buynbooze.CheckoutService.Clients.UserServiceClient;
import com.buynbooze.CheckoutService.DTO.OrderDTO;
import com.buynbooze.CheckoutService.Entities.CheckoutEntity;
import com.buynbooze.CheckoutService.Exceptions.OrderNotFoundException;
import com.buynbooze.CheckoutService.Repositories.CheckoutRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class CheckoutService implements CheckoutIMPL {

    @Autowired
    private CheckoutRepo crepo;

    @Autowired
    private UserServiceClient userServiceClient;

    @Transactional
    @Override
    public int placeOrder(CheckoutEntity checkoutEntity, HttpServletRequest request) {
        checkoutEntity.setCreated_at(LocalDateTime.now());
        CheckoutEntity ce = crepo.save(checkoutEntity);

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }

        try {
            userServiceClient.addNewOrders(
                    Map.of("order_id", ce.getOrder_id()+"",
                            "token", request.getHeader("Authorization"))
            );
        } catch (Exception e) {
            System.out.println("Failed to update user's orders: " + e.getMessage());
            throw new RuntimeException("Failed to assign order to User");
        }

        return ce.getOrder_id();
    }


    @Override
    public int updateAddress(OrderDTO checkoutEntity) {
        CheckoutEntity ce = crepo.findById(checkoutEntity.getOrder_id())
                .orElseThrow(()->new OrderNotFoundException("order not exists with Id: "+checkoutEntity.getOrder_id()));
        ce.setAddress(checkoutEntity.getAddress());
        crepo.save(ce);
        return ce.getOrder_id();
    }

    @Override
    public void deleteOrder(int orderId) {
        CheckoutEntity ce = crepo.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("order not exists with Id: "+orderId));
        crepo.deleteById(orderId);
    }

    @Override
    public void updateStatus(int orderId) {
        System.out.println("Implemnt kafka real time status update");
    }


}
