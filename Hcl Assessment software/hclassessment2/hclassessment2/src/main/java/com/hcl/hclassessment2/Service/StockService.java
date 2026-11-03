package com.hcl.hclassessment2.Service;

import com.hcl.hclassessment2.Entity.Product;
import com.hcl.hclassessment2.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {

    private final ProductRepository productRepository;

    public StockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Reduce stock when an order is placed.
     */
    @Transactional
    public boolean reduceStock(Long productId, int quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            return false;
        }
        Product product = productOpt.get();
        if (product.getQuantity() < quantity) {
            return false; // Not enough stock
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        return true;
    }

    /**
     * Increase stock when an order is cancelled.
     */
    @Transactional
    public boolean restoreStock(Long productId, int quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            return false;
        }
        Product product = productOpt.get();
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);
        return true;
    }

    /**
     * Check if stock is available for a product.
     */
    public boolean isStockAvailable(Long productId, int quantity) {
        return productRepository.findById(productId)
                .map(p -> p.getQuantity() >= quantity)
                .orElse(false);
    }
}

