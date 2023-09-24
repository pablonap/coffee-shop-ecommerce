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
            Product product = new Product();
            product.setId(1L);
            product.setName("extra coffee for free");
            product.setPrice(0.0);
            product.setCategory(ProductCategory.COFFEE.getDescription());
            CartProduct cartProduct = CartProduct.cartProductOf(order.getCart(), product, 1) ;

            Set<CartProduct> cartProducts = new HashSet<>();
            order.getCart().getCartProducts().forEach(e-> cartProducts.add(e));
            cartProducts.add(cartProduct);
            order.getCart().setCartProducts(cartProducts);
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
