package com.buynbooze.UserService.Services;

import com.buynbooze.UserService.DTO.AccountDTO;
import com.buynbooze.UserService.DTO.createDTO;
import com.buynbooze.UserService.Entities.UserEntity;
import com.buynbooze.UserService.Exceptions.OldPasswordNotMatchExcepion;
import com.buynbooze.UserService.Exceptions.UserAlreadyExistsException;
import com.buynbooze.UserService.Repositories.UserRepo;
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
                .orders(userEntity.getOrders())
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
    public void updateOrders(Map<String, String> body, String username) {
        UserEntity userEntity = userRepo.findById(username).
                orElseThrow(()-> new UsernameNotFoundException("User not exists for user :"+body.get("username")));
        List<Integer> lst = new ArrayList<>();
        if(userEntity.getOrders()!=null){
            lst.addAll(userEntity.getOrders());
        }
        lst.add(Integer.parseInt(body.get("order_id")));
        userEntity.setOrders(lst);
        userRepo.save(userEntity);
    }
}
