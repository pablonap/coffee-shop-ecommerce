package com.trafilea.coffeeshop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    @Column(name="create_at")
    private LocalDateTime createAt;

    private Integer productsAmount;

    private Double discount;

    private boolean isFreeShipping = false;

    private static final Double SHIPPING_DEFAULT_AMOUNT = 5.0;

    private Double shippingAmount;

    private String shippingAddress;

    private Double totalAmount;

    public Order() {
    }

    public Order(Cart cart) {
        this.cart = cart;
        this.createAt = LocalDateTime.now();
        this.shippingAmount = SHIPPING_DEFAULT_AMOUNT;
    }

    public double calculateTotalAmount() {
        double partialTotalAmount = this.cart.getCartProducts()
                .stream()
                .mapToDouble(cp -> cp.getProduct().getPrice() * cp.getQuantity())
                .sum();

        partialTotalAmount = partialTotalAmount + this.shippingAmount;

        return partialTotalAmount - (partialTotalAmount * this.discount);
    }

    public int calculateProductsAmount() {
        return this.cart.getCartProducts()
                .stream()
                .mapToInt(cp -> cp.getQuantity())
                .sum();
    }

    public Long getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Integer getProductsAmount() {
        return productsAmount;
    }

    public void setProductsAmount(Integer productsAmount) {
        this.productsAmount = productsAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(Double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isFreeShipping() {
        return isFreeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        isFreeShipping = freeShipping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (isFreeShipping != order.isFreeShipping) return false;
        if (!Objects.equals(id, order.id)) return false;
        if (!Objects.equals(cart, order.cart)) return false;
        if (!Objects.equals(createAt, order.createAt)) return false;
        if (!Objects.equals(productsAmount, order.productsAmount))
            return false;
        if (!Objects.equals(discount, order.discount)) return false;
        if (!Objects.equals(shippingAmount, order.shippingAmount))
            return false;
        if (!Objects.equals(shippingAddress, order.shippingAddress))
            return false;
        return Objects.equals(totalAmount, order.totalAmount);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cart != null ? cart.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (productsAmount != null ? productsAmount.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (isFreeShipping ? 1 : 0);
        result = 31 * result + (shippingAmount != null ? shippingAmount.hashCode() : 0);
        result = 31 * result + (shippingAddress != null ? shippingAddress.hashCode() : 0);
        result = 31 * result + (totalAmount != null ? totalAmount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cart=" + cart +
                ", createAt=" + createAt +
                ", productsAmount=" + productsAmount +
                ", discount=" + discount +
                ", isFreeShipping=" + isFreeShipping +
                ", shippingAmount=" + shippingAmount +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
