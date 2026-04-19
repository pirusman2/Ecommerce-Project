package com.peerecom.ecommerceApp.controller;

import com.peerecom.ecommerceApp.dto.ProductRequest;
import com.peerecom.ecommerceApp.dto.ProductResponse;
import com.peerecom.ecommerceApp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor // this constructor will be only used  for final such as Product Service
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest),
                                                     HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.updateProduct(id, productRequest),
                HttpStatus.OK);

    }

    @GetMapping
    public  ResponseEntity <List <ProductResponse>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();


    }


    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword){
       return ResponseEntity.ok(productService.searchProducts(keyword));


    }




}
