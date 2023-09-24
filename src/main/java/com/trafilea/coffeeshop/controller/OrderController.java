package com.trafilea.coffeeshop.controller;

import com.trafilea.coffeeshop.dto.OrderResponseDto;
import com.trafilea.coffeeshop.service.PlaceOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final PlaceOrderService placeOrderService;

    public OrderController(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }

    @PostMapping("/create/{cartId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable Long cartId) {
        return new ResponseEntity<>(placeOrderService.createOrder(cartId), HttpStatus.OK);
    }
}
