package com.hcl.hclassessment2.Repository;

import com.hcl.hclassessment2.Entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByCustomerCustomerId(Long customerId);
}

