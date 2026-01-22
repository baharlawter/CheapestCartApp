package com.cheapestcart.services;

import com.cheapestcart.dto.CheapestItemResponse;
import com.cheapestcart.models.Product;
import com.cheapestcart.models.ProductPrice;
import com.cheapestcart.repositories.ProductRepository;
import com.cheapestcart.repositories.ProductPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceComparisonService {

    private final ProductRepository productRepository;
    private final ProductPriceRepository productPriceRepository;

    /**
     * Get the cheapest item for a given product name
     */
    public Optional<CheapestItemResponse> getCheapestItem(String productName) {
        Optional<Product> product = productRepository.findByNameIgnoreCase(productName);
        
        if (product.isEmpty()) {
            return Optional.empty();
        }

        Optional<ProductPrice> cheapestPrice = productPriceRepository
            .findCheapestByProductId(product.get().getId());

        if (cheapestPrice.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(mapToResponse(product.get(), cheapestPrice.get()));
    }

    /**
     * Get the cheapest items for multiple products
     */
    public List<CheapestItemResponse> getCheapestItems(List<String> productNames) {
        return productNames.stream()
            .flatMap(name -> getCheapestItem(name).stream())
            .collect(Collectors.toList());
    }

    /**
     * Compare prices across all stores for a product
     */
    public List<ProductPrice> compareAllStores(Long productId) {
        List<ProductPrice> prices = productPriceRepository.findByProductId(productId);
        return prices.stream()
            .sorted(Comparator.comparing(ProductPrice::getPrice))
            .collect(Collectors.toList());
    }

    /**
     * Get total price for a shopping cart (list of products)
     */
    public Map<String, Object> calculateCartTotal(List<String> productNames) {
        List<CheapestItemResponse> items = getCheapestItems(productNames);
        
        BigDecimal totalPrice = items.stream()
            .map(CheapestItemResponse::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("items", items);
        result.put("totalPrice", totalPrice);
        result.put("itemCount", items.size());

        return result;
    }

    /**
     * Helper method to map ProductPrice to CheapestItemResponse
     */
    private CheapestItemResponse mapToResponse(Product product, ProductPrice price) {
        BigDecimal pricePerUnit = price.getPrice()
            .divide(BigDecimal.valueOf(price.getQuantity()), 2, RoundingMode.HALF_UP);

        CheapestItemResponse response = new CheapestItemResponse();
        response.setProductId(product.getId());
        response.setProductName(product.getName());
        response.setCategory(product.getCategory());
        response.setStoreId(price.getStore().getId());
        response.setChainName(price.getStore().getChainName());
        response.setStoreName(price.getStore().getStoreName());
        response.setPrice(price.getPrice());
        response.setQuantity(price.getQuantity());
        response.setUnit(price.getUnit());
        response.setPricePerUnit(pricePerUnit);

        return response;
    }
}
