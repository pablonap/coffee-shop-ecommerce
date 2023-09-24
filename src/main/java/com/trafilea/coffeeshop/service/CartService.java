package com.trafilea.coffeeshop.service;

import com.trafilea.coffeeshop.dto.*;
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
        CartProduct cartProduct = CartProduct.cartProductOf(cart, product, cartRequestDto.quantity());
        cart.getCartProducts().add(cartProduct);
        Cart savedCart = cartRepository.save(cart);

        return cartResponseDtoMapper.apply(savedCart);
    }

    public CartResponseDto addProduct(long cartId, CartRequestDto cartRequestDto) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(IllegalArgumentException::new);
        Product product = productRepository.findById(cartRequestDto.productId()).orElseThrow(IllegalArgumentException::new);

        CartProduct cartProduct = CartProduct.cartProductOf(cart, product, cartRequestDto.quantity());
        cart.getCartProducts().add(cartProduct);
        Cart savedCart = cartRepository.save(cart);

        return cartResponseDtoMapper.apply(savedCart);

    }

    public CartResponseDto modifyQuantity(long cartId, long productId, CartProductQuantityRequestDto dto) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(IllegalArgumentException::new);
        Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);

        CartProduct cartProduct = cart.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getId() == product.getId())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Error searching for product ID = " + product.getId()));

        cartProduct.setQuantity(dto.quantity());

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
