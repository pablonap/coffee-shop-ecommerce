package com.trafilea.coffeeshop.dto;

import com.google.common.base.Preconditions;

public record CartProductQuantityRequestDto(int quantity) {
  public CartProductQuantityRequestDto {
    Preconditions.checkArgument(quantity > 0);
  }
}
