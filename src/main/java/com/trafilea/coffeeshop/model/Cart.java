package com.trafilea.coffeeshop.model;

import com.google.common.base.Preconditions;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.lang.NonNull;
import static java.util.Objects.requireNonNull;

// inconsistent naming convention, some entities doesn't include suffix "Entity" while others do
@Entity
@Table(name = "carts")
public class Cart implements PromotionStrategies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // FIXME: map User type instead
    @Column(name="user_id")
    private long userId;

    @Column(name="create_at")
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartProduct> cartProducts;

    Cart() {
    }

    /**
     * creates an empty Cart owned by the given {@link UserEntity}
     * @param user the owner
     * @return the empty Cart for such user
     */
    public static Cart createCart(@NonNull UserEntity user) {
        requireNonNull(user);
        final var result = new Cart();
        result.setUserId(user.getId());
        result.cartProducts = new HashSet<>();
        result.state = State.ON_PROCESS;
        result.createAt = LocalDateTime.now();
        return result;
    }

    public void addProduct(@NonNull Product product, int quantity) {
        requireNonNull(product);
        cartProducts.stream().filter(cp -> product.equals(cp.getProduct()))
            .findAny() // no need to use findFirst since Set doesn't allow dupes
            .ifPresentOrElse(
                cp -> cp.addQuantity(quantity),
                () -> this.cartProducts.add(CartProduct.cartProductOf(this, product, quantity))
            );
    }

    @Override
    public boolean applyExtraCoffee() {
        int amountOfCoffeeProducts = this.getCartProducts()
                .stream()
                .filter(cp -> ProductCategory.COFFEE.getDescription().equals(cp.getProduct().getCategory()))
                .mapToInt(CartProduct::getQuantity)
                .sum();

        if (amountOfCoffeeProducts >= 2) {
            return true;
        }
        return false;
    }

    @Override
    public boolean applyFreeShipping() {
        long amountOfEquipmentProducts = this.getCartProducts()
                .stream()
                .filter(cp -> ProductCategory.EQUIPMENT.getDescription().equals(cp.getProduct().getCategory()))
                .mapToInt(CartProduct::getQuantity)
                .sum();

      return amountOfEquipmentProducts > 3;
    }

    @Override
    public boolean applyDiscount() {
        final double LIMIT_AMOUNT = 70.0;
        double totalPriceOfAccessoriesProducts = this.getCartProducts()
                .stream()
                .filter(cp -> ProductCategory.ACCESSORIES.getDescription().equals(cp.getProduct().getCategory()))
                .mapToDouble(cp -> cp.getProduct().getPrice() * cp.getQuantity())
                .sum();

      return totalPriceOfAccessoriesProducts > LIMIT_AMOUNT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Set<CartProduct> getCartProducts() {
        return Set.copyOf(cartProducts);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (id != cart.id) return false;
        if (userId != cart.userId) return false;
        if (!Objects.equals(createAt, cart.createAt)) return false;
        return state == cart.state;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", createAt=" + createAt +
                ", state=" + state +
                '}';
    }
}
