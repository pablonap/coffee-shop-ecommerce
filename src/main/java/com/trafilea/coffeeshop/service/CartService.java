package com.trafilea.coffeeshop.service;

import com.trafilea.coffeeshop.dto.CartProductQuantityRequestDto;
import com.trafilea.coffeeshop.dto.CartRequestDto;
import com.trafilea.coffeeshop.dto.CartResponseDto;
import com.trafilea.coffeeshop.dto.CartResponseDtoMapper;
import com.trafilea.coffeeshop.exception.ExceptionMessages;
import com.trafilea.coffeeshop.exception.RequestAuthenticationException;
import com.trafilea.coffeeshop.exception.ResourceNotFoundException;
import com.trafilea.coffeeshop.model.Cart;
import com.trafilea.coffeeshop.model.CartProduct;
import com.trafilea.coffeeshop.model.Product;
import com.trafilea.coffeeshop.model.UserEntity;
import com.trafilea.coffeeshop.repository.CartRepository;
import com.trafilea.coffeeshop.repository.ProductRepository;
import com.trafilea.coffeeshop.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        String username = getLoggedInUsername()
                .orElseThrow(() -> new RequestAuthenticationException(ExceptionMessages.AUTHENTICATION_ERROR.getMessage()));


        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.getMessage()));

        Cart cart = new Cart();
        cart.setUserId(userEntity.getId());
        CartProduct cartProduct = CartProduct.cartProductOf(cart, product, cartRequestDto.quantity());
        cart.getCartProducts().add(cartProduct);
        Cart savedCart = cartRepository.save(cart);

        return cartResponseDtoMapper.apply(savedCart);
    }

    public CartResponseDto addProduct(long cartId, CartRequestDto cartRequestDto) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(IllegalArgumentException::new);
        Product product = productRepository.findById(cartRequestDto.productId())
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.getMessage()));

        CartProduct cartProduct = CartProduct.cartProductOf(cart, product, cartRequestDto.quantity());
        cart.getCartProducts().add(cartProduct);
        Cart savedCart = cartRepository.save(cart);

        return cartResponseDtoMapper.apply(savedCart);

    }

    public CartResponseDto modifyQuantity(long cartId, long productId, CartProductQuantityRequestDto dto) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.getMessage()));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.getMessage()));

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
