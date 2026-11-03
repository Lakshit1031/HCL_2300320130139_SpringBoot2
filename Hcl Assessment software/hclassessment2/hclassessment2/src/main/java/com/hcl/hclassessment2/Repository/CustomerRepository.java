package com.hcl.hclassessment2.Repository;

import com.hcl.hclassessment2.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, Long> {}


