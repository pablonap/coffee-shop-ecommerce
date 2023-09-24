package com.trafilea.coffeeshop.service;

import com.trafilea.coffeeshop.dto.OrderRequestDto;
import com.trafilea.coffeeshop.dto.OrderResponseDto;
import com.trafilea.coffeeshop.dto.OrderResponseDtoMapper;
import com.trafilea.coffeeshop.model.*;
import com.trafilea.coffeeshop.repository.CartRepository;
import com.trafilea.coffeeshop.repository.OrderRepository;
import com.trafilea.coffeeshop.service.utils.TestsUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlaceOrderServiceTests {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderResponseDtoMapper orderResponseDtoMapper;

    @InjectMocks
    private PlaceOrderService underTest;

    @Test
    void itShouldCreateOrderWithoutAnyPromotion() {
        // given
        final int QUANTITY_PRODUCT_1 = 1;
        final int QUANTITY_PRODUCT_2 = 3;
        final int QUANTITY_PRODUCT_3 = 1;

        OrderRequestDto orderRequestDto = TestsUtils.orderRequestDtoOf(1L, "velazquez 1234");

        Product product1 = TestsUtils.productOf(
                1L,
                "american coffee",
                4.0,
                ProductCategory.COFFEE.getDescription());

        Product product2 = TestsUtils.productOf(
                2L,
                "small bag",
                5.0,
                ProductCategory.EQUIPMENT.getDescription());

        Product product3 = TestsUtils.productOf(
                3L,
                "coffee machine",
                50.0,
                ProductCategory.ACCESSORIES.getDescription());

        Cart cart = TestsUtils.cartOf(1L, 2);

        CartProduct cartProduct1 = TestsUtils.cartProductOf(1L, cart, product1, QUANTITY_PRODUCT_1);
        CartProduct cartProduct2 = TestsUtils.cartProductOf(2L, cart, product2, QUANTITY_PRODUCT_2);
        CartProduct cartProduct3 = TestsUtils.cartProductOf(3L, cart, product3, QUANTITY_PRODUCT_3);

        Set<CartProduct> cartProducts = Set.of(cartProduct1, cartProduct2, cartProduct3);

        cart.setCartProducts(cartProducts);
        product1.setCartProducts(cartProducts);
        product2.setCartProducts(cartProducts);
        product3.setCartProducts(cartProducts);

        Order order = new Order(cart);
        order.setShippingAddress(orderRequestDto.shippingAddress());

        when(cartRepository.findById(orderRequestDto.cartId())).thenReturn(Optional.of(cart));
        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
        when(orderResponseDtoMapper.apply(order)).thenReturn(Mockito.any(OrderResponseDto.class));

        // when
        underTest.createOrder(orderRequestDto);

        // then
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order capturedOrder = orderArgumentCaptor.getValue();

        assertThat(capturedOrder.getProductsAmount()).isEqualTo(5);
        assertThat(capturedOrder.getTotalAmount()).isEqualTo(74.0); // 69 + 5 (from shipping)
        assertThat(capturedOrder.isFreeShipping()).isFalse();
        assertThat(capturedOrder.getDiscount()).isEqualTo(0.0);

        verify(cartRepository, times(1)).findById(orderRequestDto.cartId());
        verify(orderRepository, times(1)).save(Mockito.any(Order.class));
        verify(orderResponseDtoMapper, times(1)).apply(order);
    }

    @Test
    void itShouldCreateOrderWithExtraCoffeePromotion() {
        // given
        final int QUANTITY = 2;
        OrderRequestDto orderRequestDto = TestsUtils.orderRequestDtoOf(1L, "velazquez 1234");

        Product product1 = TestsUtils.productOf(
                1L,
                "american coffee",
                4.0,
                ProductCategory.COFFEE.getDescription());

        Cart cart = TestsUtils.cartOf(1L, 2);

        CartProduct cartProduct1 = TestsUtils.cartProductOf(1L, cart, product1, QUANTITY);

        Set<CartProduct> cartProducts = Set.of(cartProduct1);

        cart.setCartProducts(cartProducts);
        product1.setCartProducts(cartProducts);

        Order order = new Order(cart);
        order.setShippingAddress(orderRequestDto.shippingAddress());

        when(cartRepository.findById(orderRequestDto.cartId())).thenReturn(Optional.of(cart));
        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
        when(orderResponseDtoMapper.apply(order)).thenReturn(Mockito.any(OrderResponseDto.class));

        // when
        underTest.createOrder(orderRequestDto);

        // then
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order capturedOrder = orderArgumentCaptor.getValue();

        assertThat(capturedOrder.getProductsAmount()).isEqualTo(3);
        assertThat(capturedOrder.getTotalAmount()).isEqualTo(13); // 4 (from coffee) + 5 (from shipping)
        assertThat(capturedOrder.isFreeShipping()).isFalse();
        assertThat(capturedOrder.getDiscount()).isEqualTo(0.0);

        verify(cartRepository, times(1)).findById(orderRequestDto.cartId());
        verify(orderRepository, times(1)).save(Mockito.any(Order.class));
        verify(orderResponseDtoMapper, times(1)).apply(order);
    }

}














