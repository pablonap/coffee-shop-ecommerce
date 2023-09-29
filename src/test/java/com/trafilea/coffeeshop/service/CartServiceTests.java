package com.trafilea.coffeeshop.service;

import com.trafilea.coffeeshop.dto.CartResponseDtoMapper;
import com.trafilea.coffeeshop.repository.CartRepository;
import com.trafilea.coffeeshop.repository.ProductRepository;
import com.trafilea.coffeeshop.repository.UserRepository;
import java.util.Optional;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

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

    @Mock
    private Supplier<Optional<Authentication>> authSupplier;

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
    void itShouldModifyQuantity() {
        // TODO
    }

    @Test
    void itShouldNotModifyQuantityIfCartNotFound() {
        // TODO
    }

    @Test
    void itShouldNotModifyQuantityIfProductNotFound() {
        // TODO
    }
}
