package com.example.promotion;

import com.example.model.Order;
import com.example.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Buy one get one promotion for cosmetics: adds one extra item for each cosmetics item purchased.
 */
public class BuyOneGetOnePromotion implements Promotion {
    
    private final String targetCategory;
    
    public BuyOneGetOnePromotion(String targetCategory) {
        this.targetCategory = targetCategory;
    }
    
    @Override
    public Order apply(Order order) {
        List<OrderItem> finalItems = new ArrayList<>();
        
        for (OrderItem item : order.getItems()) {
            if (targetCategory.equals(item.getCategory())) {
                // Buy one get one: create new item with increased quantity
                finalItems.add(new OrderItem(item.getProductName(), item.getCategory(), 
                        item.getQuantity() + 1, item.getUnitPrice()));
            } else {
                finalItems.add(item);
            }
        }
        
        return new Order(finalItems, order.getOriginalAmount(), order.getDiscount(), order.getTotalAmount());
    }
}

