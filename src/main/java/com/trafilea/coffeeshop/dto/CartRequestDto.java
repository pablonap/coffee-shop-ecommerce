package com.trafilea.coffeeshop.dto;

import com.google.common.base.Preconditions;

public record CartRequestDto(
    long productId,
    int quantity
) {
  public CartRequestDto {
    Preconditions.checkArgument(quantity > 0);
  }
}
