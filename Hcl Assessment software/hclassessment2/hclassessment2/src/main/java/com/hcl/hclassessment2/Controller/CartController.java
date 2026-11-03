package com.hcl.hclassessment2.Controller;

import com.hcl.hclassessment2.Entity.Cart;
import com.hcl.hclassessment2.Service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) { this.cartService = cartService; }

    @GetMapping("/{customerId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getOrCreateCart(customerId));
    }

    @PostMapping("/{customerId}/add")
    public ResponseEntity<Cart> addProductToCart(
            @PathVariable Long customerId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.addProductToCart(customerId, productId, quantity));
    }
}

