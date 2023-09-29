package com.trafilea.coffeeshop.service;

import com.trafilea.coffeeshop.model.CartProduct;
import com.trafilea.coffeeshop.model.Order;
import com.trafilea.coffeeshop.model.Product;
import com.trafilea.coffeeshop.model.ProductCategory;

import java.util.HashSet;
import java.util.Set;

public enum PromotionType {
    EXTRA_FOR_FREE {
        @Override
        public void applyPromotion(Order order) {
            // this will fail as product needs the persisted id to be located in the Set
            Product product = new Product("extra coffee for free", 0F, ProductCategory.COFFEE.getDescription());
            order.getCart().addProduct(product, 1);
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
