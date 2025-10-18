package com.example.steps;

import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.service.OrderService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderPricingSteps {

    private OrderService orderService;
    private Order order;
    private List<OrderItem> orderItems;

    @Given("no promotions are applied")
    public void no_promotions_are_applied() {
        orderService = new OrderService();
    }

    @Given("the threshold discount promotion is configured:")
    public void the_threshold_discount_promotion_is_configured(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> config = rows.get(0);
        int threshold = Integer.parseInt(config.get("threshold"));
        int discount = Integer.parseInt(config.get("discount"));
        
        if (orderService == null) {
            orderService = new OrderService();
        }
        orderService.setThresholdDiscount(threshold, discount);
    }

    @Given("the buy one get one promotion for cosmetics is active")
    public void the_buy_one_get_one_promotion_for_cosmetics_is_active() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        orderService.enableBuyOneGetOneForCosmetics();
    }

    @Given("the eleven eleven promotion is active")
    public void the_eleven_eleven_promotion_is_active() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        orderService.enableElevenElevenPromotion();
    }

    @When("a customer places an order with:")
    public void a_customer_places_an_order_with(io.cucumber.datatable.DataTable dataTable) {
        orderItems = new ArrayList<>();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> row : rows) {
            String productName = row.get("productName");
            int quantity = Integer.parseInt(row.get("quantity"));
            int unitPrice = Integer.parseInt(row.get("unitPrice"));
            
            OrderItem item;
            if (row.containsKey("category")) {
                String category = row.get("category");
                item = new OrderItem(productName, category, quantity, unitPrice);
            } else {
                item = new OrderItem(productName, quantity, unitPrice);
            }
            orderItems.add(item);
        }
        
        order = orderService.createOrder(orderItems);
    }

    @Then("the order summary should be:")
    public void the_order_summary_should_be(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> expected = rows.get(0);
        
        if (expected.containsKey("totalAmount")) {
            int expectedTotal = Integer.parseInt(expected.get("totalAmount"));
            assertThat(order.getTotalAmount()).isEqualTo(expectedTotal);
        }
        
        if (expected.containsKey("originalAmount")) {
            int expectedOriginal = Integer.parseInt(expected.get("originalAmount"));
            assertThat(order.getOriginalAmount()).isEqualTo(expectedOriginal);
        }
        
        if (expected.containsKey("discount")) {
            int expectedDiscount = Integer.parseInt(expected.get("discount"));
            assertThat(order.getDiscount()).isEqualTo(expectedDiscount);
        }
    }

    @Then("the customer should receive:")
    public void the_customer_should_receive(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> row : rows) {
            String productName = row.get("productName");
            int expectedQuantity = Integer.parseInt(row.get("quantity"));
            
            OrderItem item = order.getItems().stream()
                    .filter(i -> i.getProductName().equals(productName))
                    .findFirst()
                    .orElse(null);
            
            assertThat(item).isNotNull();
            assertThat(item.getQuantity()).isEqualTo(expectedQuantity);
        }
    }
}

