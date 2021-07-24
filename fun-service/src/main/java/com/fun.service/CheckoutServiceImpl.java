package com.fun.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final Map<String, Basket> baskets = new ConcurrentHashMap<>();

    public void addProduct(String customerId, String productId) {
        /* Create Bucket if missing */
        baskets.computeIfAbsent(customerId, Basket::new);

        /* Check and add Product */
        //TODO: Add Product Check and price
        if (productId.startsWith("P")) {
            baskets.get(customerId).addProduct(productId, 100f);
        } else {
            var msg = "Invalid Product Id: " + productId;
            throw new FunException(FunException.ErrorCode.PRODUCT_NOT_FOUND, msg, msg);
        }
    }

    public Optional<Basket> getBasket(String customerId) {
        return Optional.ofNullable(baskets.get(customerId));
    }

}
