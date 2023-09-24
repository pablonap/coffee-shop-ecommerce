package com.trafilea.coffeeshop.service;

import com.trafilea.coffeeshop.model.CartProduct;
import com.trafilea.coffeeshop.model.Order;
import com.trafilea.coffeeshop.model.ProductCategory;

import java.util.Optional;

public enum PromotionType {
    EXTRA_FOR_FREE {
        @Override
        public void applyPromotion(Order order) {
            Optional<CartProduct> coffeeProduct = order.getCart().getCartProducts()
                    .stream()
                    .filter(cp -> ProductCategory.COFFEE.getDescription().equals(cp.getProduct().getCategory()))
                    .findFirst();

            CartProduct cartProduct = coffeeProduct.orElseThrow(() -> new RuntimeException("Promotion cannot be applied"));
            cartProduct.addToQuantity(1);
        }
    },
    FREE_SHIPPING {
        @Override
        public void applyPromotion(Order order) {
            order.setFreeShipping(true);
            order.setShippingAmount(0.0);
        }
    },
    DISCOUNT {
        @Override
        public void applyPromotion(Order order) {
            order.setDiscount(0.1);
        }
    };

    public abstract void applyPromotion(Order order);
}
