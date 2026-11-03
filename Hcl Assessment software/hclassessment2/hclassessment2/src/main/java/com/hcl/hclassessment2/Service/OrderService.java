package com.hcl.hclassessment2.Service;

import com.hcl.hclassessment2.Entity.*;
import com.hcl.hclassessment2.Exception.InsufficientStockException;
import com.hcl.hclassessment2.Exception.ResourceNotFoundException;
import com.hcl.hclassessment2.Repository.CartRepository;
import com.hcl.hclassessment2.Repository.CustomerOrderRepository;
import com.hcl.hclassessment2.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final CustomerOrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderService(CustomerOrderRepository orderRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CustomerOrder placeOrder(Long customerId) {
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart is empty or not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cannot place order with an empty cart");
        }

        CustomerOrder order = new CustomerOrder();
        order.setCustomer(cart.getCustomer());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PLACED");

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();

            // Manage and deduct product stock
            if (product.getQuantity() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getProductName());
            }
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            order.getOrderItems().add(orderItem);

            // Calculate Order Totals
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        order.setTotalAmount(total);
        cart.getCartItems().clear(); // Flush cart
        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    public List<CustomerOrder> getOrderHistory(Long customerId) {
        return orderRepository.findByCustomerCustomerId(customerId);
    }

    public CustomerOrder cancelOrder(Long orderId) {
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if ("CANCELLED".equals(order.getOrderStatus())) {
            throw new IllegalStateException("Order is already cancelled");
        }

        // Return stock back to inventory
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productRepository.save(product);
        }

        order.setOrderStatus("CANCELLED");
        return orderRepository.save(order);
    }
}
