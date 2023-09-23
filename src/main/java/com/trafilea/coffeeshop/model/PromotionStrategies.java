package com.trafilea.coffeeshop.model;

public interface PromotionStrategies {
    boolean applyExtraCoffee();

    boolean applyFreeShipping();

    boolean applyDiscount();
}
