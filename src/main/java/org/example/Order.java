
package org.example;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Order {

    String orderId;
    String status;
    String orderDateTime;
    ArrayList<FoodItem> items;
    int total;

    // Constructor for a new order

    Order(String orderId, ArrayList<FoodItem> cart) {

        this.orderId = orderId;
        this.status = "Pending";
        this.orderDateTime = LocalDateTime.now().toString();
        this.items = new ArrayList<>(cart);

        total = 0;

        for (FoodItem item : items) {
            total = total + item.price;
        }
    }

    // Constructor for loading saved orders

    Order(String orderId, String status,
          String orderDateTime, ArrayList<FoodItem> items) {

        this.orderId = orderId;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.items = new ArrayList<>(items);

        total = 0;

        for (FoodItem item : this.items) {
            total = total + item.price;
        }
    }
}
