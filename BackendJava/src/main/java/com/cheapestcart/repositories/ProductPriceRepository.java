package com.cheapestcart.repositories;

import com.cheapestcart.models.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    
    List<ProductPrice> findByProductId(Long productId);
    
    List<ProductPrice> findByStoreId(Long storeId);
    
    Optional<ProductPrice> findByProductIdAndStoreId(Long productId, Long storeId);
    
    @Query("SELECT pp FROM ProductPrice pp WHERE pp.product.id = :productId ORDER BY pp.price ASC LIMIT 1")
    Optional<ProductPrice> findCheapestByProductId(@Param("productId") Long productId);
}
