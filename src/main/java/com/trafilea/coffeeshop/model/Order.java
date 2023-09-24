package com.trafilea.coffeeshop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    @Column(name="create_at")
    private LocalDateTime createAt;

    @Column(name="products_amount")
    private Integer productsAmount;

    private double discount;

    @Column(name="is_free_shipping")
    private boolean isFreeShipping = false;

    private static final Double SHIPPING_DEFAULT_AMOUNT = 5.0;

    @Column(name="shipping_amount")
    private Double shippingAmount;

    @Column(name="shipping_address")
    private String shippingAddress;

    @Column(name="total_amount")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private State state;

    public Order() {
    }

    public Order(Cart cart) {
        this.cart = cart;
        this.shippingAmount = SHIPPING_DEFAULT_AMOUNT;
        this.state = State.FINISHED;
        this.createAt = LocalDateTime.now();
    }

    public double calculateTotalAmount() {
        double partialTotalAmount = this.cart.getCartProducts()
                .stream()
                .mapToDouble(cp -> cp.getProduct().getPrice() * cp.getQuantity())
                .sum();

        partialTotalAmount = partialTotalAmount - (partialTotalAmount * this.discount);

        return partialTotalAmount + this.shippingAmount;
    }

    public int calculateProductsAmount() {
        return this.cart.getCartProducts()
                .stream()
                .mapToInt(cp -> cp.getQuantity())
                .sum();
    }

    public long getId() {
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
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

        Order order = (Order) o;

        if (id != order.id) return false;
        if (Double.compare(order.discount, discount) != 0) return false;
        if (isFreeShipping != order.isFreeShipping) return false;
        if (!Objects.equals(createAt, order.createAt)) return false;
        if (!Objects.equals(productsAmount, order.productsAmount))
            return false;
        if (!Objects.equals(shippingAmount, order.shippingAmount))
            return false;
        if (!Objects.equals(shippingAddress, order.shippingAddress))
            return false;
        if (!Objects.equals(totalAmount, order.totalAmount)) return false;
        return state == order.state;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (productsAmount != null ? productsAmount.hashCode() : 0);
        temp = Double.doubleToLongBits(discount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isFreeShipping ? 1 : 0);
        result = 31 * result + (shippingAmount != null ? shippingAmount.hashCode() : 0);
        result = 31 * result + (shippingAddress != null ? shippingAddress.hashCode() : 0);
        result = 31 * result + (totalAmount != null ? totalAmount.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createAt=" + createAt +
                ", productsAmount=" + productsAmount +
                ", discount=" + discount +
                ", isFreeShipping=" + isFreeShipping +
                ", shippingAmount=" + shippingAmount +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", totalAmount=" + totalAmount +
                ", state=" + state +
                '}';
    }
}
