package com.peerecom.ecommerceApp.repository;

import com.peerecom.ecommerceApp.model.Cartitem;
import com.peerecom.ecommerceApp.model.Product;
import com.peerecom.ecommerceApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartitemRepository extends JpaRepository<Cartitem,Long> {
    Cartitem findByUserAndProduct(User user, Product product);
}
