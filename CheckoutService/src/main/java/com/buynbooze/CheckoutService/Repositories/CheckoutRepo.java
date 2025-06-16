package com.buynbooze.CheckoutService.Repositories;

import com.buynbooze.CheckoutService.Entities.CheckoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepo  extends JpaRepository<CheckoutEntity , Integer> {
}
