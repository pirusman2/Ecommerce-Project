package com.peerecom.ecommerceApp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
    // one user can have multiple orders created   many is representing the orders
    // why many for order come first in ManyToOne bcx we are in order class
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private BigDecimal totalAmount;

    // bcx it is taking values from Enum
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    // also order will have the list of items that were ordered

    // one order can have multiple order items
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
