package com.buynbooze.UserService.Services;

import com.buynbooze.UserService.DTO.AccountDTO;
import com.buynbooze.UserService.DTO.createDTO;

import java.util.Map;

public interface UserServiceImpl {

    void createUser(createDTO userEntity);

    AccountDTO getDetails(String uname);

    void resetPassword(Map<String, String> body);

    void updateOrders(Map<String, String> body, String username);
}
