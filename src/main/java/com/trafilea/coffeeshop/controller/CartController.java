package com.trafilea.coffeeshop.controller;

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
    public ResponseEntity<CartResponseDto> create(@RequestBody CartRequestDto cartRequestDto) {
        return new ResponseEntity<>(cartService.create(cartRequestDto), HttpStatus.OK);
    }

    @PutMapping("/{cartId}/add-product")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartResponseDto> addProduct(@PathVariable("cartId") long cartId , @RequestBody CartRequestDto cartRequestDto) {
        return new ResponseEntity<>(cartService.addProduct(cartId, cartRequestDto), HttpStatus.OK);
    }
}
