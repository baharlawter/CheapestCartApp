package com.cheapestcart.controllers;

import com.cheapestcart.models.Store;
import com.cheapestcart.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * Get all stores
     */
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    /**
     * Get store by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable Long id) {
        Optional<Store> store = storeService.getStoreById(id);
        return store.isPresent()
            ? ResponseEntity.ok(store.get())
            : ResponseEntity.notFound().build();
    }

    /**
     * Get store by chain name
     */
    @GetMapping("/search")
    public ResponseEntity<?> getStoreByChainName(@RequestParam String chainName) {
        Optional<Store> store = storeService.getStoreByChainName(chainName);
        return store.isPresent()
            ? ResponseEntity.ok(store.get())
            : ResponseEntity.notFound().build();
    }

    /**
     * Create a new store
     */
    @PostMapping
    public ResponseEntity<Store> addStore(@RequestBody Store store) {
        Store createdStore = storeService.addStore(store);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStore);
    }

    /**
     * Update a store
     */
    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(
            @PathVariable Long id,
            @RequestBody Store storeDetails) {
        Store updatedStore = storeService.updateStore(id, storeDetails);
        return ResponseEntity.ok(updatedStore);
    }

    /**
     * Delete a store
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }
}
