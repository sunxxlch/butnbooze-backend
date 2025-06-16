package com.buynbooze.Productservice.Service;

import com.buynbooze.Productservice.DTO.ProductDTO;
import com.buynbooze.Productservice.Entity.ProductsEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductImpl {

    public List<ProductDTO> findProducts(String category);

    ProductDTO findProducts(String productCategory, int productId);

    void addProductsToCategory(String productCategory, List<ProductsEntity> productDTOList);

    void updatePrice(String productCategory, int productId, Map<String, Object> obj) ;

    void updateProductDetails(String productCategory, ProductsEntity productsEntity);

    void updateImage(String productCategory, int productId, MultipartFile imageFile) throws IOException;

    void deleteProduct(String productCategory, int productId);

    byte[] findImage(String productCategory, int productId);

    List<ProductDTO> findProductsByPage(String productCategory, int pageNum);

    int findPages(String productCategory);
}
