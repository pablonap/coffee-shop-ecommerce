package com.trafilea.coffeeshop.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.lang.NonNull;

// inconsistent naming convention, some entities doesn't include suffix "Entity" while others do
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private double price;

    private String category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CartProduct> cartProducts;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<CartProduct> getCartProducts() {
        return Set.copyOf(cartProducts);
    }

    public void setCartProducts(@NonNull Set<CartProduct> cartProducts) {
        this.cartProducts = new HashSet<>(cartProducts);
    }

    Product() {

    }

    public Product(@NonNull String name, double price, String category) {
        this.price = price;
        this.name = name;
        this.cartProducts = new HashSet<>(0);
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (!Objects.equals(name, product.name)) return false;
        if (!Objects.equals(price, product.price)) return false;
        return Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
