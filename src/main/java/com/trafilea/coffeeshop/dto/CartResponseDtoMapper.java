package com.trafilea.coffeeshop.dto;

import com.trafilea.coffeeshop.model.Cart;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CartResponseDtoMapper implements Function<Cart, CartResponseDto> {
    @Override
    public CartResponseDto apply(Cart cart) {
        return new CartResponseDto(cart.getId());
    }
}
