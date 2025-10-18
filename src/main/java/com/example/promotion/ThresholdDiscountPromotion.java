package com.example.promotion;

import com.example.model.Order;

/**
 * Threshold discount promotion: applies a discount when the order subtotal reaches a threshold.
 */
public class ThresholdDiscountPromotion implements Promotion {
    
    private final int threshold;
    private final int discountAmount;
    
    public ThresholdDiscountPromotion(int threshold, int discountAmount) {
        this.threshold = threshold;
        this.discountAmount = discountAmount;
    }
    
    @Override
    public Order apply(Order order) {
        int originalAmount = order.getOriginalAmount();
        int discount = 0;
        
        if (threshold > 0 && originalAmount >= threshold) {
            discount = discountAmount;
        }
        
        int totalAmount = originalAmount - discount;
        
        return new Order(order.getItems(), originalAmount, discount, totalAmount);
    }
}

