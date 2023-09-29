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
import java.util.Objects;
import java.util.function.Supplier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import static java.util.Objects.requireNonNull;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartResponseDtoMapper cartResponseDtoMapper;

    private final Supplier<Optional<Authentication>> userProvider;

    public CartService(
        CartRepository cartRepository,
        ProductRepository productRepository,
        UserRepository userRepository,
        CartResponseDtoMapper cartResponseDtoMapper,
        Supplier<Optional<Authentication>> userProvider
    ) {
        this.cartRepository = requireNonNull(cartRepository);
        this.productRepository = requireNonNull(productRepository);
        this.userRepository = requireNonNull(userRepository);
        this.cartResponseDtoMapper = requireNonNull(cartResponseDtoMapper);
        this.userProvider = requireNonNull(userProvider);
    }

    @Transactional
    public CartResponseDto create(CartRequestDto cartRequestDto) {
        Product product = productRepository.findById(cartRequestDto.productId()).orElseThrow(IllegalArgumentException::new);
        String username = getLoggedInUsername()
                .orElseThrow(() -> new RequestAuthenticationException(ExceptionMessages.AUTHENTICATION_ERROR.getMessage()));


        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.getMessage()));

        Cart cart = Cart.createCart(userEntity);
        cart.addProduct(product, cartRequestDto.quantity());
        Cart savedCart = cartRepository.saveAndFlush(cart);

        return cartResponseDtoMapper.apply(savedCart);
    }

    @Transactional
    public CartResponseDto addProduct(long cartId, @NonNull CartRequestDto cartRequestDto) {
        Objects.requireNonNull(cartRequestDto);
        Cart cart = cartRepository.findById(cartId).orElseThrow(IllegalArgumentException::new);
        Product product = productRepository.findById(cartRequestDto.productId())
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.getMessage()));

        cart.addProduct(product, cartRequestDto.quantity());
        Cart savedCart = cartRepository.save(cart);

        return cartResponseDtoMapper.apply(savedCart);

    }

    @Transactional
    public CartResponseDto modifyQuantity(long cartId, long productId, @NonNull CartProductQuantityRequestDto dto) {
        requireNonNull(dto);
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
        return userProvider.get().map(Authentication::getName);
    }
}
