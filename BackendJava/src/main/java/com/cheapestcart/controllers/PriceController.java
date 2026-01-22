package com.cheapestcart.controllers;

import com.cheapestcart.dto.CheapestItemResponse;
import com.cheapestcart.services.PriceComparisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceComparisonService priceService;

    /**
     * Get the cheapest item for a product by name
     * GET /api/prices/cheapest?productName=milk
     */
    @GetMapping("/cheapest")
    public ResponseEntity<?> getCheapestItem(@RequestParam String productName) {
        Optional<CheapestItemResponse> result = priceService.getCheapestItem(productName);
        
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(result.get());
    }

    /**
     * Get cheapest items for multiple products
     * POST /api/prices/cheapest-items
     * Body: ["milk", "bread", "eggs"]
     */
    @PostMapping("/cheapest-items")
    public ResponseEntity<List<CheapestItemResponse>> getCheapestItems(
            @RequestBody List<String> productNames) {
        List<CheapestItemResponse> result = priceService.getCheapestItems(productNames);
        return ResponseEntity.ok(result);
    }

    /**
     * Compare prices across all stores for a product
     * GET /api/prices/compare?productId=1
     */
    @GetMapping("/compare")
    public ResponseEntity<?> compareStores(@RequestParam Long productId) {
        var result = priceService.compareAllStores(productId);
        
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(result);
    }

    /**
     * Calculate total price for a shopping cart
     * POST /api/prices/cart-total
     * Body: ["milk", "bread", "eggs"]
     */
    @PostMapping("/cart-total")
    public ResponseEntity<Map<String, Object>> calculateCartTotal(
            @RequestBody List<String> productNames) {
        Map<String, Object> result = priceService.calculateCartTotal(productNames);
        return ResponseEntity.ok(result);
    }
}
