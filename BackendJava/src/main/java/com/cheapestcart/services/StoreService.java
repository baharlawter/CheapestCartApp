package com.cheapestcart.services;

import com.cheapestcart.models.Store;
import com.cheapestcart.repositories.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> getStoreById(Long id) {
        return storeRepository.findById(id);
    }

    public Optional<Store> getStoreByChainName(String chainName) {
        return storeRepository.findByChainNameIgnoreCase(chainName);
    }

    public Store addStore(Store store) {
        return storeRepository.save(store);
    }

    public Store updateStore(Long id, Store storeDetails) {
        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Store not found"));
        
        store.setChainName(storeDetails.getChainName());
        store.setStoreName(storeDetails.getStoreName());
        store.setBaseUrl(storeDetails.getBaseUrl());
        store.setCountry(storeDetails.getCountry());
        
        return storeRepository.save(store);
    }

    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}
