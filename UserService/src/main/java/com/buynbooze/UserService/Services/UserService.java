package com.buynbooze.UserService.Services;

import com.buynbooze.UserService.Clients.CheckoutClient;
import com.buynbooze.UserService.DTO.AccountDTO;
import com.buynbooze.UserService.DTO.createDTO;
import com.buynbooze.UserService.Entities.CheckoutEntity;
import com.buynbooze.UserService.Entities.UserEntity;
import com.buynbooze.UserService.Exceptions.OldPasswordNotMatchExcepion;
import com.buynbooze.UserService.Exceptions.UserAlreadyExistsException;
import com.buynbooze.UserService.Repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserServiceImpl {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CheckoutClient checkoutClient;

    @Override
    public void createUser(createDTO create) {
        Optional<UserEntity> use = userRepo.findById(create.getUsername());
        if(use.isEmpty()){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(create.getUsername());
            userEntity.setPassword(passwordEncoder.encode(create.getPassword()));
            userEntity.setEmail(create.getEmail());
            userEntity.setMobile(create.getMobile());
            userEntity.setDob(create.getDob());
            userEntity.setCreated_at(LocalDateTime.now());
            userEntity.setOrders(null);
            userRepo.save(userEntity);
        }else{
            throw new UserAlreadyExistsException("user already exists with username: "+create.getUsername());
        }
    }

    @Override
    public AccountDTO getDetails(String uname) {
        UserEntity userEntity = userRepo.findById(uname).
                orElseThrow(()-> new UsernameNotFoundException("User not exists for user :"+uname));
        return AccountDTO.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .mobile(userEntity.getMobile())
                .dob(userEntity.getDob())
                .created_at(userEntity.getCreated_at())
                .build();
    }

    @Override
    public void resetPassword(Map<String, String> body) {
        UserEntity userEntity = userRepo.findById(body.get("username")).
                orElseThrow(()-> new UsernameNotFoundException("User not exists for user :"+body.get("username")));
        String currpass = passwordEncoder.encode(body.get("currentPassword"));
        if(currpass.equals(userEntity.getPassword())){
            userEntity.setPassword(currpass);
            userRepo.save(userEntity);
        }else{
            throw new OldPasswordNotMatchExcepion("Old Password not matches");
        }
    }

    @Override
    public void updateOrders(Long checkoutObj, String username) {
        UserEntity userEntity = userRepo.findById(username).
                orElseThrow(()-> new UsernameNotFoundException("User not exists for user :"+username));
        System.out.println(checkoutObj);
        List<Long> currentOrders = userEntity.getOrders();
        if (currentOrders == null) {
            currentOrders = new ArrayList<>();
        }
        currentOrders.add(checkoutObj);
        userEntity.setOrders(currentOrders);
        userRepo.save(userEntity);
    }

    @Override
    public List<Long> getOrders(String username) {
        UserEntity userEntity = userRepo.findById(username).
                orElseThrow(()-> new UsernameNotFoundException("User not exists for user :"+username));
        return userEntity.getOrders();
    }

    @Override
    public String getEmail(String username) {
        UserEntity userEntity = userRepo.findById(username).
                orElseThrow(()-> new UsernameNotFoundException("User not exists for user :"+username));
        System.out.println("email received");
        return  userEntity.getEmail();
    }

    @Override
    public List<CheckoutEntity> getOrdersDetails(String username) {
        UserEntity userEntity = userRepo.findById(username).
                orElseThrow(()-> new UsernameNotFoundException("User not exists for user :"+username));
        List<Long> orderIds = userEntity.getOrders();
        return checkoutClient.getAllOrder(orderIds);
    }
}
