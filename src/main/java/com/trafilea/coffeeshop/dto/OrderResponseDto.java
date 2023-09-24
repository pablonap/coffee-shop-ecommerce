package com.trafilea.coffeeshop.dto;

import com.trafilea.coffeeshop.model.Cart;
import com.trafilea.coffeeshop.model.State;

import java.time.LocalDateTime;

public record OrderResponseDto (
    long orderId,
    long cartId,
    String createAt,
    int productsAmount,
    double discount,
    double shippingAmount,
    String shippingAddress,
    double totalAmount,
    State state
) {}
