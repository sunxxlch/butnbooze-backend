package com.buynbooze.CheckoutService.Services;

import com.buynbooze.CheckoutService.Clients.UserServiceClient;
import com.buynbooze.CheckoutService.DTO.OrderDTO;
import com.buynbooze.CheckoutService.DTO.UserOrderDTO;
import com.buynbooze.CheckoutService.Entities.CheckoutEntity;
import com.buynbooze.CheckoutService.Entities.TransactionEntity;
import com.buynbooze.CheckoutService.Exceptions.OrderNotFoundException;
import com.buynbooze.CheckoutService.Repositories.CheckoutRepo;
import com.buynbooze.CheckoutService.Repositories.TransactionRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutService implements CheckoutIMPL {

    @Autowired
    private CheckoutRepo crepo;

    @Autowired
    private TransactionRepo trepo;

    @Autowired
    private UserServiceClient userServiceClient;

    @Transactional
    @Override
    public Long placeOrder(Long transactionId, HttpServletRequest request) {

        TransactionEntity te = trepo.findById(transactionId).orElseThrow();

        CheckoutEntity checkoutEntity = new CheckoutEntity();
        checkoutEntity.setAddress(te.getAddress());
        checkoutEntity.setStatus("Delivered");
        checkoutEntity.setProducts(te.getProducts());
        checkoutEntity.setSubtotal(te.getSubtotal());
        checkoutEntity.setPayment_type(te.getPayment_type());
        checkoutEntity.setCreated_at(LocalDateTime.now());

        CheckoutEntity ce = crepo.save(checkoutEntity);


        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        UserOrderDTO userOrderDTO = UserOrderDTO.builder()
                .token(request.getHeader("Authorization"))
                .order(ce.getOrder_id())
                .build();

        try {
            userServiceClient.addNewOrders(userOrderDTO);
            updateTransaction(te);
        } catch (Exception e) {
            System.out.println("Failed to update user's orders: " + e.getMessage());
            throw new RuntimeException("Failed to assign order to User");
        }

        return ce.getOrder_id();

    }


    @Override
    public Long updateAddress(OrderDTO checkoutEntity) {
        CheckoutEntity ce = crepo.findById(checkoutEntity.getOrder_id())
                .orElseThrow(() -> new OrderNotFoundException("order not exists with Id: " + checkoutEntity.getOrder_id()));
        ce.setAddress(checkoutEntity.getAddress());
        crepo.save(ce);
        return ce.getOrder_id();
    }

    @Override
    public void deleteOrder(Long orderId) {
        CheckoutEntity ce = crepo.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("order not exists with Id: " + orderId));
        crepo.deleteById(orderId);
    }

    @Override
    public void updateStatus(Long orderId) {
        System.out.println("Implemnt kafka real time status update");
    }

    @Override
    public Long newTransaction(TransactionEntity transactionEntity) {
        transactionEntity.setCreated_at(LocalDateTime.now());
        transactionEntity.setStatus("InProgress");
        transactionEntity.setUser_email(userServiceClient.getEmail(transactionEntity.getUser_id()));
        TransactionEntity te = trepo.save(transactionEntity);
        return te.getTransaction_id();
    }

    @Override
    public List<CheckoutEntity> getAllOrderDetails(List<Long> orderIds) {
        return crepo.findAllById(orderIds);
    }

    public void updateTransaction(TransactionEntity transactionEntity) {
        transactionEntity.setStatus("Completed");
        trepo.save(transactionEntity);
    }


}
