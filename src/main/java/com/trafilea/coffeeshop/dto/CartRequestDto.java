package com.trafilea.coffeeshop.dto;

public record CartRequestDto(
    long productId,
    int quantity
) {}
