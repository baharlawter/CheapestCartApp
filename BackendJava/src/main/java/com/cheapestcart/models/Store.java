package com.cheapestcart.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String chainName;

    @Column(nullable = false)
    private String storeName;

    private String baseUrl;

    private String country;

    public Store(String chainName, String storeName, String baseUrl, String country) {
        this.chainName = chainName;
        this.storeName = storeName;
        this.baseUrl = baseUrl;
        this.country = country;
    }
}
