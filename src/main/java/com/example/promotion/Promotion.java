package com.example.promotion;

import com.example.model.Order;

/**
 * Promotion interface for applying various discount rules to orders.
 * Follows Open-Closed Principle: open for extension, closed for modification.
 */
public interface Promotion {
    
    /**
     * Apply the promotion to the order.
     * 
     * @param order the order to apply promotion to
     * @return the order after applying the promotion
     */
    Order apply(Order order);
}

