package com.hcl.hclassessment2.Repository;

import com.hcl.hclassessment2.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}

