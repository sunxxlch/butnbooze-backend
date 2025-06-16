package com.buynbooze.CheckoutService.Services;

import com.buynbooze.CheckoutService.DTO.OrderDTO;
import com.buynbooze.CheckoutService.Entities.CheckoutEntity;
import jakarta.servlet.http.HttpServletRequest;

public interface CheckoutIMPL {
    int placeOrder(CheckoutEntity checkoutEntity, HttpServletRequest request);

    int updateAddress(OrderDTO checkoutEntity);

    void deleteOrder(int orderId);

    void updateStatus(int orderId);
}
