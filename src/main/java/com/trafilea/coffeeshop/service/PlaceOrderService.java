package com.trafilea.coffeeshop.service;

import com.trafilea.coffeeshop.dto.OrderRequestDto;
import com.trafilea.coffeeshop.dto.OrderResponseDto;
import com.trafilea.coffeeshop.dto.OrderResponseDtoMapper;
import com.trafilea.coffeeshop.model.Cart;
import com.trafilea.coffeeshop.model.Order;
import com.trafilea.coffeeshop.model.State;
import com.trafilea.coffeeshop.repository.CartRepository;
import com.trafilea.coffeeshop.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class PlaceOrderService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderResponseDtoMapper orderResponseDtoMapper;

    public PlaceOrderService(CartRepository cartRepository,
                             OrderRepository orderRepository,
                             OrderResponseDtoMapper orderResponseDtoMapper) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
    }

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Cart cart = cartRepository.findById(orderRequestDto.cartId()).orElseThrow(IllegalArgumentException::new);

        if (cart.getState().equals(State.FINISHED)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Cart with ID " + cart.getId() + " is already finished");
        }

        Order order = new Order(cart);

        if (cart.applyExtraCoffee()) {
            PromotionType.EXTRA_FOR_FREE.applyPromotion(order);
        }

        if (cart.applyFreeShipping()) {
            PromotionType.FREE_SHIPPING.applyPromotion(order);
        }

        if (cart.applyDiscount()) {
            PromotionType.DISCOUNT.applyPromotion(order);
        }

        order.setShippingAddress(orderRequestDto.shippingAddress());
        order.setProductsAmount(order.calculateProductsAmount());
        order.setTotalAmount(order.calculateTotalAmount());

        order.getCart().setState(State.FINISHED);
        Order savedOrder = orderRepository.save(order);

        return orderResponseDtoMapper.apply(savedOrder);
    }
}
