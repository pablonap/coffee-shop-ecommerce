package com.trafilea.coffeeshop.service;

import com.trafilea.coffeeshop.dto.CartRequestDto;
import com.trafilea.coffeeshop.dto.CartResponseDto;
import com.trafilea.coffeeshop.dto.CartResponseDtoMapper;
import com.trafilea.coffeeshop.dto.OrderResponseDtoMapper;
import com.trafilea.coffeeshop.model.Cart;
import com.trafilea.coffeeshop.model.CartProduct;
import com.trafilea.coffeeshop.model.Product;
import com.trafilea.coffeeshop.model.UserEntity;
import com.trafilea.coffeeshop.repository.CartRepository;
import com.trafilea.coffeeshop.repository.ProductRepository;
import com.trafilea.coffeeshop.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartResponseDtoMapper cartResponseDtoMapper;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository,
                       CartResponseDtoMapper cartResponseDtoMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartResponseDtoMapper = cartResponseDtoMapper;
    }

    public CartResponseDto create(CartRequestDto cartRequestDto) {
        Product product = productRepository.findById(cartRequestDto.productId()).orElseThrow(IllegalArgumentException::new);
        String username = getLoggedInUsername().orElseThrow(() -> new BadCredentialsException("Error with credentials"));


        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));

        Cart cart = new Cart();
        cart.setUserId(userEntity.getId());

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(cartRequestDto.quantity());

        cart.getCartProducts().add(cartProduct);

        Cart savedCart = cartRepository.save(cart);

        return cartResponseDtoMapper.apply(savedCart);
    }

    private Optional<String> getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return Optional.of(authentication.getName());
        }

        return Optional.empty();
    }
}
