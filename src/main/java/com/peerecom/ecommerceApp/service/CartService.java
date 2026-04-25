package com.peerecom.ecommerceApp.service;

import com.peerecom.ecommerceApp.dto.CartItemRequest;
import com.peerecom.ecommerceApp.model.Cartitem;
import com.peerecom.ecommerceApp.model.Product;
import com.peerecom.ecommerceApp.model.User;
import com.peerecom.ecommerceApp.repository.CartitemRepository;
import com.peerecom.ecommerceApp.repository.ProductRepository;
import com.peerecom.ecommerceApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final CartitemRepository cartitemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {

        // so first we will look for the product whether the product exists or not
       Optional<Product> productOpt = productRepository.findById(request.getProductId());
       if (productOpt.isEmpty())
           return false;

       // if product was found
        Product product = productOpt.get();
        // or if the client request for 4 products and in stock we have 3
        if (product.getStockQuantity() < request.getQuantity())
            return false;

        // checking if the user exists by checking the userId entered by the user request
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty())
            return false;

        // if the program has reached here it means the user and stock and product exists
        // so the request has passed these 3 checks
        User user = userOpt.get();

        // well if the item already exists in cart and user request for it increase the quantity
        // of the product and if not present in cart add the product to the cart as new product
        Cartitem existingCartItem = cartitemRepository.findByUserAndProduct(user,product);

        if (existingCartItem != null){
            // u perform the update the quantity bcx product already exists in cart
                                         // item already present     +     items requested by user
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartitemRepository.save(existingCartItem);

        }
        else {
            //otherwise create a new cart item here

            Cartitem cartitem = new Cartitem();
            cartitem.setUser(user);
            cartitem.setProduct(product);
            cartitem.setQuantity(request.getQuantity());
            cartitem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartitemRepository.save(cartitem);
        }
        return true;
    }

    // so to delete an item from the cart pass the product id which is added to the cart and by which user it is added also pass that user id
    public boolean deleteItemFromCart(String userId, Long productId) {

        // validation for product
        Optional<Product> productOpt = productRepository.findById(productId);
        // validation for user
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (productOpt.isPresent() && userOpt.isPresent()){
            cartitemRepository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
            return true;
        }

        return false;
    }


    public List<Cartitem> getCart(String userId) {
        // check for the user
        return userRepository.findById(Long.valueOf(userId))
        .map(cartitemRepository::findByUser) // if user exists return it
                .orElseGet(List::of); // or if not exists return empty list
    }


    // this will remove all the cart items for a particular user
    public void clearCart(String userId) {
        // first we will check the given user in user repository
        userRepository.findById(Long.valueOf(userId)).ifPresent(cartitemRepository::deleteByUser);

    }
}
