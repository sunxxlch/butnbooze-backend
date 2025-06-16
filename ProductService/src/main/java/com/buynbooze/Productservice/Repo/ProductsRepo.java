package com.buynbooze.Productservice.Repo;

import com.buynbooze.Productservice.DTO.ProductDTO;
import com.buynbooze.Productservice.Entity.ProductsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepo extends JpaRepository<ProductsEntity, Integer> {

    List<ProductDTO> findByCategory(String category);

    Optional<ProductDTO> findByCategoryAndId(String productCategory, int productId);


}
