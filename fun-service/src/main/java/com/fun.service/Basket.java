package com.fun.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class Basket {
    private String customerId;
    private final Map<String, Float> productMap = new ConcurrentHashMap<>();

    Basket(String customerId) {
        this.customerId = customerId;
    }

    void addProduct(String productId, Float price) {
        this.productMap.put(productId, price);
    }

    public Float getTotal() {
        Float total = 0f;
        for (String productId : this.productMap.keySet()) {
            total += this.productMap.get(productId);
        }
        return total;
    }
}
