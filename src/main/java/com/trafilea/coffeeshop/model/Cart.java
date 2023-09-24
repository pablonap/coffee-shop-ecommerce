package com.trafilea.coffeeshop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart implements PromotionStrategies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="user_id")
    private long userId;

    @Column(name="create_at")
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CartProduct> cartProducts;

    public Cart() {
        cartProducts = new HashSet<>();
        this.createAt = LocalDateTime.now();
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

        if (amountOfEquipmentProducts > 3) {
            return true;
        }
        return false;
    }

    @Override
    public boolean applyDiscount() {
        final Double LIMIT_AMOUNT = 70.0;
        Double totalPriceOfAccessoriesProducts = this.getCartProducts()
                .stream()
                .filter(cp -> ProductCategory.ACCESSORIES.getDescription().equals(cp.getProduct().getCategory()))
                .mapToDouble(cp -> cp.getProduct().getPrice() * cp.getQuantity())
                .sum();

        if (totalPriceOfAccessoriesProducts > LIMIT_AMOUNT) {
            return true;
        }
        return false;
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
        return cartProducts;
    }

    public void setCartProducts(Set<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (id != cart.id) return false;
        if (userId != cart.userId) return false;
        return Objects.equals(createAt, cart.createAt);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", createAt=" + createAt +
                '}';
    }
}
