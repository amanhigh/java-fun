package com.fun.service;

import java.util.Optional;

public interface CheckoutService {
    void addProduct(String customerId, String productId);
    Optional<Basket> getBasket(String customerId);
}
