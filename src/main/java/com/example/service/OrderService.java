package com.example.service;

import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderService for creating orders with various promotions.
 * Follows Open-Closed Principle: open for extension (new promotions), closed for modification.
 */
public class OrderService {

    private final List<Promotion> promotions;

    public OrderService() {
        this.promotions = new ArrayList<>();
    }

    /**
     * Add a promotion to be applied to orders.
     * 
     * @param promotion the promotion to add
     */
    public void addPromotion(Promotion promotion) {
        this.promotions.add(promotion);
    }

    /**
     * Set threshold discount promotion (for backward compatibility with tests).
     * 
     * @param threshold the threshold amount
     * @param discount the discount amount
     */
    public void setThresholdDiscount(int threshold, int discount) {
        promotions.clear();
        promotions.add(new com.example.promotion.ThresholdDiscountPromotion(threshold, discount));
    }

    /**
     * Enable buy one get one promotion for cosmetics (for backward compatibility with tests).
     */
    public void enableBuyOneGetOneForCosmetics() {
        if (promotions.isEmpty()) {
            promotions.add(new com.example.promotion.BuyOneGetOnePromotion("cosmetics"));
        } else {
            // Add to existing promotions
            promotions.add(new com.example.promotion.BuyOneGetOnePromotion("cosmetics"));
        }
    }

    /**
     * Enable eleven eleven promotion (for backward compatibility with tests).
     */
    public void enableElevenElevenPromotion() {
        if (promotions.isEmpty()) {
            promotions.add(new com.example.promotion.ElevenElevenPromotion());
        } else {
            // Add to existing promotions
            promotions.add(new com.example.promotion.ElevenElevenPromotion());
        }
    }

    /**
     * Create an order with the configured promotions applied.
     * 
     * @param items the items in the order
     * @return the order with promotions applied
     */
    public Order createOrder(List<OrderItem> items) {
        // Calculate original amount (sum of all items) - use original quantities for pricing
        int originalAmount = items.stream()
                .mapToInt(item -> item.getQuantity() * item.getUnitPrice())
                .sum();
        
        // Create initial order with no discount
        Order order = new Order(items, originalAmount, 0, originalAmount);
        
        // Apply all promotions in sequence
        for (Promotion promotion : promotions) {
            order = promotion.apply(order);
        }
        
        return order;
    }
}

