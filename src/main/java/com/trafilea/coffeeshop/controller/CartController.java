package com.trafilea.coffeeshop.controller;

import com.trafilea.coffeeshop.dto.CartProductQuantityRequestDto;
import com.trafilea.coffeeshop.dto.CartRequestDto;
import com.trafilea.coffeeshop.dto.CartResponseDto;
import com.trafilea.coffeeshop.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public CartResponseDto create(@RequestBody CartRequestDto cartRequestDto) {
        return cartService.create(cartRequestDto);
    }

    @PutMapping("/{cartId}/add-product")
    @PreAuthorize("hasRole('USER')")
    public CartResponseDto addProduct(@PathVariable("cartId") long cartId , @RequestBody CartRequestDto cartRequestDto) {
        return cartService.addProduct(cartId, cartRequestDto);
    }

    @PutMapping("/{cartId}/product/{productId}/quantity")
    @PreAuthorize("hasRole('USER')")
    public CartResponseDto modifyQuantity(@PathVariable("cartId") long cartId, @PathVariable("productId") long productId, @RequestBody CartProductQuantityRequestDto cartProductQuantityRequestDto) {
        return cartService.modifyQuantity(cartId, productId, cartProductQuantityRequestDto);
    }
}
