package com.hcl.hclassessment2.Repository;

import com.hcl.hclassessment2.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Long> {}


