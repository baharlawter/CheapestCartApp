package com.cheapestcart.repositories;

import com.cheapestcart.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByChainNameIgnoreCase(String chainName);
}
