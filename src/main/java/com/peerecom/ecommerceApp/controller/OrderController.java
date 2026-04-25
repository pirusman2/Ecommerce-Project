package com.peerecom.ecommerceApp.controller;

import com.peerecom.ecommerceApp.dto.OrderResponse;
import com.peerecom.ecommerceApp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader ("X-User-Id") String userId){

            return orderService.createOrder(userId)
                    .map(orderResponse -> new ResponseEntity<>(orderResponse,HttpStatus.CREATED))
                    .orElseGet(() -> ResponseEntity.badRequest().body());

    }
}
