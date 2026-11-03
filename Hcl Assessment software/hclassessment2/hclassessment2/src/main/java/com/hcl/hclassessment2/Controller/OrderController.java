package com.hcl.hclassessment2.Controller;

import com.hcl.hclassessment2.Entity.CustomerOrder;
import com.hcl.hclassessment2.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) { this.orderService = orderService; }

    @PostMapping("/place/{customerId}")
    public ResponseEntity<CustomerOrder> placeOrder(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.placeOrder(customerId));
    }

    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<CustomerOrder>> viewOrderHistory(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrderHistory(customerId));
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<CustomerOrder> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}

