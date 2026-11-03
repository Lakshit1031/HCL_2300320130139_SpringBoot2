package com.hcl.hclassessment2.Repository;

import com.hcl.hclassessment2.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerCustomerId(Long customerId);
}


