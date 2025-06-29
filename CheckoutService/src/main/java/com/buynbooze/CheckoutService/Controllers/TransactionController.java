package com.buynbooze.CheckoutService.Controllers;

import com.buynbooze.CheckoutService.DTO.ResponseDTO;
import com.buynbooze.CheckoutService.Entities.CheckoutEntity;
import com.buynbooze.CheckoutService.Entities.TransactionEntity;
import com.buynbooze.CheckoutService.Services.CheckoutIMPL;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final CheckoutIMPL checkoutIMPL;

    public TransactionController(CheckoutIMPL checkoutIMPL) {
        this.checkoutIMPL = checkoutIMPL;
    }

    @PostMapping("/newTransaction")
    public Long addTransaction(@RequestBody TransactionEntity transactionEntity){
        return checkoutIMPL.newTransaction(transactionEntity);
    }


}
