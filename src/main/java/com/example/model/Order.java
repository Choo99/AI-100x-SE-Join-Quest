package com.example.model;

import java.util.List;

public class Order {
    private List<OrderItem> items;
    private int originalAmount;
    private int discount;
    private int totalAmount;

    public Order(List<OrderItem> items, int originalAmount, int discount, int totalAmount) {
        this.items = items;
        this.originalAmount = originalAmount;
        this.discount = discount;
        this.totalAmount = totalAmount;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int getOriginalAmount() {
        return originalAmount;
    }

    public int getDiscount() {
        return discount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}


