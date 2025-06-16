package com.buynbooze.Productservice.Service;

import com.buynbooze.Productservice.DTO.ProductDTO;
import com.buynbooze.Productservice.Entity.ProductsEntity;
import com.buynbooze.Productservice.Exception.ProductNotFoundException;
import com.buynbooze.Productservice.Repo.ProductsRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class Productservice implements ProductImpl {

    @Autowired
    private ProductsRepo prepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductDTO> findProducts(String category) {
        return prepo.findByCategory(category);
    }

    @Override
    public ProductDTO findProducts(String productCategory, int productId) {
        return prepo.findByCategoryAndId(productCategory,productId).orElseThrow(()->
        new ProductNotFoundException("Product is not available in Database"+productId)
        );
    }

    @Transactional
    @Override
    public void addProductsToCategory(String productCategory, List<ProductsEntity> productDTOList) {
        try{
            for (ProductsEntity product : productDTOList) {
                StoredProcedureQuery query = entityManager
                        .createStoredProcedureQuery("save_none_existing_products");

                query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);

                query.setParameter(1, product.getCategory());
                query.setParameter(2, product.getBrand());
                query.setParameter(3, product.getType());

                if(query.execute()){
                    System.out.println("Product Added :"+product.getCategory()+" "+product.getBrand()+" "+product.getType());
                }else{
                    System.out.println("Product already exists for :"+product.getCategory()+" "+product.getBrand()+" "+product.getType());
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePrice(String productCategory, int productId, Map<String, Object> obj) {
        ProductsEntity productsEntity =prepo.findById(productId).orElseThrow(()->
                new ProductNotFoundException("Product is not available in Database"+productId)
        );
        double newPrice = Double.parseDouble(obj.get("price").toString());
        if(Double.isNaN(newPrice)){
            throw new RuntimeException();
        }else{
            productsEntity.setPrice(newPrice);
            prepo.save(productsEntity);
        }
    }

    @Override
    public void updateProductDetails(String productCategory, ProductsEntity productsEntity) {
        ProductsEntity pde =prepo.findById(productsEntity.getId()).orElseThrow(()->
                new ProductNotFoundException("Product is not available in Database"+productsEntity.getId())
        );
        pde.setPrice(productsEntity.getPrice());
        pde.setType(productsEntity.getType());
        pde.setCategory(productsEntity.getCategory());
        pde.setBrand(productsEntity.getBrand());

        prepo.save(pde);
    }

    @Transactional
    @Override
    public void updateImage(String productCategory, int productId, MultipartFile imageFile) throws IOException {
        ProductsEntity productsEntity = prepo.findById(productId).orElseThrow(()->
                new ProductNotFoundException("Product is not available in Database"+productId)
        );

        productsEntity.setImage(imageFile.getBytes());
        prepo.save(productsEntity);
    }

    @Override
    public void deleteProduct(String productCategory, int productId) {
        ProductsEntity productsEntity = prepo.findById(productId).orElseThrow(()->
                new ProductNotFoundException("Product is not available in Database"+productId)
        );
        prepo.deleteById(productId);
    }

    @Override
    public byte[] findImage(String productCategory, int productId) {
        ProductsEntity productsEntity = prepo.findById(productId).orElseThrow(()->
                new ProductNotFoundException("Product is not available in Database"+productId)
        );
        return productsEntity.getImage();
    }

    @Override
    public List<ProductDTO> findProductsByPage(String productCategory, int pageNum) {
        List<ProductDTO> list = prepo.findByCategory(productCategory);
        int size = list.size();
        int end = pageNum*12 < size ? pageNum*12 : size-1;
        int start = (pageNum-1)*12;
        return list.subList(start,end);
    }

    @Override
    public int findPages(String productCategory) {
        List<ProductDTO> list = prepo.findByCategory(productCategory);
        return (int) Math.ceil((double) list.size() /12);
    }
}
