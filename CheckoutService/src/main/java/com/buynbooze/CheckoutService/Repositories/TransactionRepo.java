package com.buynbooze.CheckoutService.Repositories;

import com.buynbooze.CheckoutService.Entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {
}
