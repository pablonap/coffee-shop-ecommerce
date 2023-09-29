package com.trafilea.coffeeshop.model;

import com.google.common.base.Preconditions;
import jakarta.persistence.*;

import java.util.Objects;
import static java.util.Objects.requireNonNull;

// inconsistent naming convention, some entities doesn't include suffix "Entity" while others do
@Entity
@Table(name = "cart_product")
public class CartProduct  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    // non-public method intended to be only used by JPA implementation
    CartProduct() {
    }

    static CartProduct cartProductOf(Cart cart, Product product, int quantity) {
        // invariants check - fail fast
        requireNonNull(cart);
        requireNonNull(product);
        Preconditions.checkArgument(quantity > 0);

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);

        return cartProduct;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = requireNonNull(cart);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = requireNonNull(product);
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantity) {
        Preconditions.checkArgument(quantity > 0);
        this.quantity += quantity;
    }

    public void setQuantity(int quantity) {
        Preconditions.checkArgument(quantity > 0); // quantity = 0 should remove this reference from cart
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartProduct that = (CartProduct) o;

        // Disclaimer: id is some sort of artificial key -> id can be used to identify already persisted references
        // combination of cart+product is the real natural key and can also be used to identify references, even for those that weren't persisted yet

        if (id != that.id) return false;
        // quantity should NOT be used to identify a reference of this class. Severe BUG: CarProducts WILL BE LOST in Cart#cartProducts set as soon as the quantity gets updated!
        if (!Objects.equals(cart, that.cart)) return false;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cart != null ? cart.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "id=" + id +
                ", cart=" + cart +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
