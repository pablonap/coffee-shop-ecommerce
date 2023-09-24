package com.trafilea.coffeeshop.dto;

public record OrderRequestDto(
    long cartId,
    String shippingAddress
) {}
