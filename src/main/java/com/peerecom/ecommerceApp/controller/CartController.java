package com.peerecom.ecommerceApp.controller;

import com.peerecom.ecommerceApp.dto.CartItemRequest;
import com.peerecom.ecommerceApp.model.Cartitem;
import com.peerecom.ecommerceApp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-Id") String userId, // these things we need for the user to enter
            @RequestBody CartItemRequest request){
        if (!cartService.addToCart(userId,request)){
            return ResponseEntity.badRequest().body("Product out of stock or user not found or product is not present");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long productId){

       boolean deleted = cartService.deleteItemFromCart(userId,productId);
              // if deleted                                      if not deleted
       return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }

    // fetch cart of a particular user
    @GetMapping
    public ResponseEntity<List<Cartitem>> getCart(@RequestHeader("X-User-Id") String userId){
       return ResponseEntity.ok(cartService.getCart(userId));


    }

}
