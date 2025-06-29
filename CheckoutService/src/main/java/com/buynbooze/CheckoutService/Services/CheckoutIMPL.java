package com.buynbooze.CheckoutService.Services;

import com.buynbooze.CheckoutService.DTO.OrderDTO;
import com.buynbooze.CheckoutService.Entities.TransactionEntity;
import jakarta.servlet.http.HttpServletRequest;

public interface CheckoutIMPL {
    Long placeOrder(Long transactionId, HttpServletRequest request);

    Long updateAddress(OrderDTO checkoutEntity);

    void deleteOrder(Long orderId);

    void updateStatus(Long orderId);

    Long newTransaction(TransactionEntity checkoutEntity);
}
