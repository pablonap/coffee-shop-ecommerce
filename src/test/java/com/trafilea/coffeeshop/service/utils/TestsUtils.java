package com.trafilea.coffeeshop.service.utils;

import com.trafilea.coffeeshop.dto.CartRequestDto;
import com.trafilea.coffeeshop.dto.OrderRequestDto;
import com.trafilea.coffeeshop.dto.OrderResponseDto;
import com.trafilea.coffeeshop.model.Cart;
import com.trafilea.coffeeshop.model.CartProduct;
import com.trafilea.coffeeshop.model.Product;
import com.trafilea.coffeeshop.model.State;

public final class TestsUtils {
    private TestsUtils() {}

    public static Product productOf(long id, String productName, double price , String category) {
        Product product = new Product();
        product.setId(id);
        product.setName(productName);
        product.setPrice(price);
        product.setCategory(category);

        return product;
    }

    public static Cart cartOf(long id, int userId) {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUserId(2);

        return cart;
    }

    public static CartProduct cartProductOf(long id, Cart cart, Product product, int quantity) {
        CartProduct cp = CartProduct.cartProductOf(cart, product, quantity);
        cp.setId(1L);

        return cp;
    }

    public static OrderRequestDto orderRequestDtoOf(long cartId, String shippingAddress) {
        return new OrderRequestDto(cartId, shippingAddress);
    }

    public static OrderResponseDto orderResponseDtoOf(long cartId,
                                                      String createAt,
                                                      int productsAmount,
                                                      double discount,
                                                      double shippingAmount,
                                                      String shippingAddress,
                                                      double totalAmount,
                                                      State state) {
        long orderId = 1L;
        return new OrderResponseDto(
                orderId,
                cartId,
                createAt,
                productsAmount,
                discount,
                shippingAmount,
                shippingAddress,
                totalAmount,
                state);
    }


    public static CartRequestDto cartRequestDtoOf(long productId, int quantity) {
        return new CartRequestDto(productId, quantity);
    }
}
