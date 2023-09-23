package com.trafilea.coffeeshop.dto;

import com.trafilea.coffeeshop.model.Order;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Component
public class OrderResponseDtoMapper implements Function<Order, OrderResponseDto> {
    @Override
    public OrderResponseDto apply(Order order) {
        final DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return new OrderResponseDto(
                order.getId(),
                order.getCreateAt().format(newFormatter),
                order.getProductsAmount(),
                order.getDiscount(),
                order.getShippingAmount(),
                order.getShippingAddress(),
                order.getTotalAmount()
        );
    }
}
