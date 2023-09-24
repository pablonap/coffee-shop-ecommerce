package com.trafilea.coffeeshop.service;

import com.trafilea.coffeeshop.dto.CartRequestDto;
import com.trafilea.coffeeshop.dto.CartResponseDtoMapper;
import com.trafilea.coffeeshop.model.Cart;
import com.trafilea.coffeeshop.model.Product;
import com.trafilea.coffeeshop.model.ProductCategory;
import com.trafilea.coffeeshop.model.UserEntity;
import com.trafilea.coffeeshop.repository.CartRepository;
import com.trafilea.coffeeshop.repository.ProductRepository;
import com.trafilea.coffeeshop.repository.UserRepository;
import com.trafilea.coffeeshop.service.utils.TestsUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartResponseDtoMapper cartResponseDtoMapper;

    @InjectMocks
    private CartService underTest;

    @Test
    void itShouldCreateCart() {
      // TODO
    }

    @Test
    void itShouldNotCreateCartIfProductNotFound() {
        // TODO
    }

    @Test
    void itShouldNotCreateCartIfUsernameNotFound() {
        // TODO
    }

    @Test
    void itShouldAddProductToCart() {
        // TODO
    }

    @Test
    void itShouldNotAddProductIfCartNotFound() {
        // TODO
    }

    @Test
    void itShouldNotAddProductIfProductNotFound() {
        // TODO
    }

    @Test
    void itShouldModifyQuanity() {
        // TODO
    }

    @Test
    void itShouldNotModifyQuanityIfCartNotFound() {
        // TODO
    }

    @Test
    void itShouldNotModifyQuanityIfProductNotFound() {
        // TODO
    }
}
