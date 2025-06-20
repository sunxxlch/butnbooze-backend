package com.buynbooze.UserService.Services;

import com.buynbooze.UserService.DTO.AccountDTO;
import com.buynbooze.UserService.DTO.createDTO;

import java.util.List;
import java.util.Map;

public interface UserServiceImpl {

    void createUser(createDTO userEntity);

    AccountDTO getDetails(String uname);

    void resetPassword(Map<String, String> body);

    void updateOrders(Object userOrderDTO, String username);

    List<Object> getOrders(String username);
}
