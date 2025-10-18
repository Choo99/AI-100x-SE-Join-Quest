package com.example.promotion;

import com.example.model.Order;
import com.example.model.OrderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Eleven Eleven promotion: discount for every 10 of the same product.
 * Rules:
 * - For every 10 of the same product (same productName), apply 200 discount
 * - No free items, only discount
 * - Only applies to items with the same productName
 * - Different products don't trigger the promotion
 */
public class ElevenElevenPromotion implements Promotion {
    
    @Override
    public Order apply(Order order) {
        List<OrderItem> finalItems = new ArrayList<>();
        int totalDiscount = order.getDiscount();
        int newOriginalAmount = 0;
        
        // Group items by product name
        Map<String, OrderItem> productMap = new HashMap<>();
        for (OrderItem item : order.getItems()) {
            productMap.put(item.getProductName(), item);
        }
        
        // Apply promotion to each unique product
        for (OrderItem item : productMap.values()) {
            int quantity = item.getQuantity();
            int groupsOf10 = quantity / 10;
            
            if (groupsOf10 > 0) {
                // Apply discount: unitPrice * 2 per group of 10
                int discount = groupsOf10 * item.getUnitPrice() * 2;
                totalDiscount += discount;
                
                // Calculate original amount based on quantity
                newOriginalAmount += quantity * item.getUnitPrice();
                
                // No free items, keep original quantity
                finalItems.add(item);
            } else {
                // No promotion applied
                newOriginalAmount += quantity * item.getUnitPrice();
                finalItems.add(item);
            }
        }
        
        int totalAmount = newOriginalAmount - totalDiscount;
        
        return new Order(finalItems, newOriginalAmount, totalDiscount, totalAmount);
    }
}

