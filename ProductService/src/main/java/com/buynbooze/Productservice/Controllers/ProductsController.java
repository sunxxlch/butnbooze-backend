package com.buynbooze.Productservice.Controllers;

import com.buynbooze.Productservice.DTO.ErrorDto;
import com.buynbooze.Productservice.DTO.ResponseDTO;
import com.buynbooze.Productservice.Entity.ProductsEntity;
import com.buynbooze.Productservice.Service.ProductImpl;
import static com.buynbooze.Productservice.Commons.Constants.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductImpl productimpl;

    public ProductsController(ProductImpl pdl) {
        this.productimpl = pdl;
    }


    @GetMapping("/{productCategory}")
    public ResponseEntity<Object> getProductsByCategory(@PathVariable String productCategory, HttpServletRequest request){
        try {
            return new ResponseEntity<>(productimpl.findProducts(productCategory), HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(
                    HttpStatus.BAD_REQUEST,
                    "Invalid product category: " + productCategory,
                    request.getRequestURI(),
                    LocalDateTime.now()
            ));
        }
    }

    @GetMapping("/{productCategory}/{productId}")
    public ResponseEntity<Object> getProductById(@PathVariable String productCategory , @PathVariable int productId, HttpServletRequest request){
        try {
            return new ResponseEntity<>(productimpl.findProducts(productCategory, productId), HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(
                    HttpStatus.BAD_REQUEST,
                    "Invalid product category: " + productCategory,
                    request.getRequestURI(),
                    LocalDateTime.now()
            ));
        }
    }

    @PostMapping("/{productCategory}")
    public ResponseEntity<Object> addProducts(@PathVariable String productCategory , @RequestBody List<ProductsEntity> productDTOList, HttpServletRequest request){
        try{
            productimpl.addProductsToCategory(productCategory,productDTOList);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(STATUS_201,DATA_ADDED_MSG+productCategory));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(
                    HttpStatus.BAD_REQUEST,
                    INVALID_CATEGORY + productCategory,
                    request.getRequestURI(),
                    LocalDateTime.now()
            ));
        }
    }

    @PutMapping("/{productCategory}/{productId}")
    public ResponseEntity<Object> updatePrice(@PathVariable String productCategory, @PathVariable int productId, @RequestBody Map<String, Object> obj, HttpServletRequest request){
        try{
            productimpl.updatePrice(productCategory,productId,obj);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(STATUS_201,UPDATED_DETAILS));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(
                    HttpStatus.BAD_REQUEST,
                    INVALID_CATEGORY + productCategory,
                    request.getRequestURI(),
                    LocalDateTime.now()
            ));
        }
    }

    @PutMapping("/{productCategory}")
    public ResponseEntity<Object> updatePrice(@PathVariable String productCategory, @RequestBody ProductsEntity productsEntity, HttpServletRequest request){
        try{
            productimpl.updateProductDetails(productCategory,productsEntity);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(STATUS_201,UPDATED_DETAILS));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(
                    HttpStatus.BAD_REQUEST,
                    INVALID_CATEGORY + productCategory,
                    request.getRequestURI(),
                    LocalDateTime.now()
            ));
        }
    }

    @PutMapping(value = "/{productCategory}/{productId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@PathVariable String productCategory, @PathVariable int productId, @RequestPart("image") MultipartFile imageFile) throws IOException {
        productimpl.updateImage(productCategory,productId,imageFile);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(STATUS_201,UPDATED_DETAILS));
    }

    @DeleteMapping("/{productCategory}/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String productCategory, @PathVariable int productId){
        productimpl.deleteProduct(productCategory,productId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(STATUS_201,DELETE_PRODUCT));
    }

    @GetMapping(value = "/{productCategory}/{productId}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String productCategory, @PathVariable int productId){
        byte[] img = productimpl.findImage(productCategory,productId);

        return ResponseEntity.status(HttpStatus.OK).body(img);
    }

    @GetMapping("/{productCategory}/page/{pageNum}")
    public ResponseEntity<Object> getProductsByCategoryPages(@PathVariable String productCategory,@PathVariable int pageNum, HttpServletRequest request){
        try {
            return new ResponseEntity<>(productimpl.findProductsByPage(productCategory,pageNum), HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(
                    HttpStatus.BAD_REQUEST,
                    "Invalid product category: " + productCategory,
                    request.getRequestURI(),
                    LocalDateTime.now()
            ));
        }
    }

    @GetMapping("/{productCategory}/pages")
    public ResponseEntity<Object> getProductsByCategoryPages(@PathVariable String productCategory, HttpServletRequest request){
        try {
            return new ResponseEntity<>(productimpl.findPages(productCategory), HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(
                    HttpStatus.BAD_REQUEST,
                    "Invalid product category: " + productCategory,
                    request.getRequestURI(),
                    LocalDateTime.now()
            ));
        }
    }

}
