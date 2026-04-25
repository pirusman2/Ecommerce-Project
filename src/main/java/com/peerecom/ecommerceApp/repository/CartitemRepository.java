package com.peerecom.ecommerceApp.repository;

import com.peerecom.ecommerceApp.model.Cartitem;
import com.peerecom.ecommerceApp.model.Product;
import com.peerecom.ecommerceApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Consumer;

@Repository
public interface CartitemRepository extends JpaRepository<Cartitem,Long> {
    Cartitem findByUserAndProduct(User user, Product product);

// so the cart item we want to delete should belong to this user and product
    void deleteByUserAndProduct(User user, Product product);

    List<Cartitem> findByUser(User user);

    void deleteByUser(User user);
}
