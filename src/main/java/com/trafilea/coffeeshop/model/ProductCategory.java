package com.trafilea.coffeeshop.model;

public enum ProductCategory {
    COFFEE("coffee"),
    EQUIPMENT("equipment"),
    ACCESSORIES("accessories");

    private final String description;

    private ProductCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
