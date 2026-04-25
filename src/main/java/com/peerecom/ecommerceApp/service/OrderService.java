package com.peerecom.ecommerceApp.service;

import com.peerecom.ecommerceApp.dto.OrderItemDTO;
import com.peerecom.ecommerceApp.dto.OrderResponse;
import com.peerecom.ecommerceApp.model.*;
import com.peerecom.ecommerceApp.repository.OrderRepository;
import com.peerecom.ecommerceApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {

        // validate for cart items eg if the items are added or not

        List<Cartitem> cartitems = cartService.getCart(userId);
        if (cartitems.isEmpty()){
            return Optional.empty();
        }

        // validate for user

        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()){

            return Optional.empty();
        }
        // if user is found so it will store in userOptional variable
        User user = userOptional.get();

        // calculate total price
        BigDecimal totalPrice = cartitems.stream()
                .map(Cartitem::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        // create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartitems.stream()
                .map(items -> new OrderItem(
                        null,
                        items.getProduct(),
                        items.getQuantity(),
                        items.getPrice(),
                        order
                ))
                .toList();
        order.setItems(orderItems);

        // now save the order in order repository
        Order savedOrder = orderRepository.save(order);

        // clear the cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    // now we are packing the data to ship to client through order response DTO
    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getItems()
                        .stream().map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProduct().getId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                        orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        ))
                        .toList(),
                savedOrder.getCreatedAt()
        );
    }
}
