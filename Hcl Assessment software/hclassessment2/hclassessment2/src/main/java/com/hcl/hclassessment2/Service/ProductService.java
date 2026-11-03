package com.hcl.hclassessment2.Service;

import com.hcl.hclassessment2.Entity.Product;
import com.hcl.hclassessment2.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() { return productRepository.findAll(); }
    public Product saveProduct(Product product) { return productRepository.save(product); }
}
