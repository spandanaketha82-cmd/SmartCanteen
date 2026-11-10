
package org.example;

public class FoodItem {

    String name;
    String category;
    int price;
    String imageName;

    FoodItem(String name, String category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageName = "";
    }

    FoodItem(String name, String category, int price, String imageName) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageName = imageName;
    }
}
