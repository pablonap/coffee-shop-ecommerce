package com.trafilea.coffeeshop.dto;

import com.trafilea.coffeeshop.model.Cart;

import java.time.LocalDateTime;

public record OrderResponseDto (
    long cartId,
    String createAt,
    int productsAmount,
    double discount,
    double shippingAmount,
    String shippingAddress,
    double totalAmount
) {}
