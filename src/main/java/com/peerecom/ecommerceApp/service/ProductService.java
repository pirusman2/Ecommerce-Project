package com.peerecom.ecommerceApp.service;

import com.peerecom.ecommerceApp.dto.ProductRequest;
import com.peerecom.ecommerceApp.dto.ProductResponse;
import com.peerecom.ecommerceApp.model.Product;
import com.peerecom.ecommerceApp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

        private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = new Product();
        updateProductFromRequest(product,productRequest); // we r updating product from productRequest

        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct); // this is mapping of product which the user had
                                                   // saved to productResponse
        // mapping to productResponse becuase for the user to access it through productResponse


    }



    // for sending data to the client
    private ProductResponse mapToProductResponse(Product savedProduct){
        ProductResponse response = new ProductResponse();
        response.setId(String.valueOf(savedProduct.getId()));
        response.setName(savedProduct.getName());
        response.setActive(savedProduct.getActive());
        response.setCategory(savedProduct.getCategory());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());
        return response;
    }

    // for storing the data in DB which will come from the client
    private void updateProductFromRequest(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());

    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {

        // first we need to get the product from the database and then update it
        return productRepository.findById(id)
                .map(existingProduct -> {
                    updateProductFromRequest(existingProduct, productRequest);
                    Product savedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(savedProduct);
                }).orElseThrow(() -> new RuntimeException("Product Not Found : " + id));

    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
       // first find the product
        return productRepository.findById(id)
                .map(product -> {  // If the product is found, do the following things inside the
                    product.setActive(false);  // Mark this product as inactive (soft delete)
                    productRepository.save(product); // Save the updated product back to database
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {

        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
