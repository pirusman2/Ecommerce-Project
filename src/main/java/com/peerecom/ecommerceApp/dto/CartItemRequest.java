package com.peerecom.ecommerceApp.dto;

import lombok.Data;

@Data
public class CartItemRequest {

    // so this class is specifically for client request handling when he put request
    // so which product he wants he will specify by product id and how much in quantity he want
    // will also specify it through quantity

    private Long productId;
    private Integer quantity;
}
