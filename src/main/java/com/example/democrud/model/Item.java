package com.example.democrud.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Simple in-memory domain object. No database involved yet -
 * this lives entirely in application memory and resets every
 * time the app restarts.
 */
public class Item {

    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @PositiveOrZero(message = "price must be zero or positive")
    private double price;

    public Item() {
    }

    public Item(Long id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
