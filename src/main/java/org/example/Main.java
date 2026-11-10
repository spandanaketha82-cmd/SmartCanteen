
package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    ArrayList<FoodItem> cart = new ArrayList<>();
    ArrayList<Order> orderHistory = new ArrayList<>();

    int orderCounter = 1;
    Order currentOrder;

    VBox cartItemsLayout = new VBox(12);

    Label totalLabel = new Label("Total: Rs.0");
    Label orderIdLabel = new Label("Order ID: Not Generated");
    Label statusLabel = new Label("Status: Not Placed");

    Button placeOrderButton = new Button("Place Order");
    Button receiptButton = new Button("Generate Receipt");
    Button historyButton = new Button("View Order History");

    String blue = "#142D4E";
    String lightBlue = "#EAF1FA";
    String white = "#FFFFFF";

    @Override
    public void start(Stage stage) {

        loadOrderHistory();

        // HOME SCREEN

        Label title = new Label("SMART CANTEEN");
        title.setStyle(
                "-fx-font-size: 36px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );

        Label subtitle = new Label(
                "FOOD ORDERING AND BILLING SYSTEM"
        );

        subtitle.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-text-fill: #DCE6F2;"
        );

        Label welcome = new Label(
                "Welcome to Smart Canteen!"
        );

        welcome.setStyle(
                "-fx-font-size: 23px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #142D4E;"
        );

        Label description = new Label(
                "Order your favourite food easily."
        );

        description.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-text-fill: #64748B;"
        );

        Button menuButton = new Button("View Food Menu");

        stylePrimaryButton(menuButton);
        stylePrimaryButton(historyButton);

        VBox homeLayout = new VBox(20);
        homeLayout.setAlignment(Pos.CENTER);
        homeLayout.setPadding(new Insets(30));
        homeLayout.setStyle(
                "-fx-background-color: #F1F5FA;"
        );

        VBox welcomeCard = new VBox(18);
        welcomeCard.setAlignment(Pos.CENTER);
        welcomeCard.setPadding(new Insets(40));
        welcomeCard.setMaxWidth(500);

        welcomeCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 18;" +
                        "-fx-border-color: #DCE6F2;" +
                        "-fx-border-radius: 18;"
        );

        welcomeCard.getChildren().addAll(
                welcome,
                description,
                menuButton,
                historyButton
        );

        VBox homeHeader = new VBox(10);
        homeHeader.setAlignment(Pos.CENTER);
        homeHeader.setPadding(new Insets(35));

        homeHeader.setStyle(
                "-fx-background-color: #142D4E;" +
                        "-fx-background-radius: 15;"
        );

        homeHeader.getChildren().addAll(
                title,
                subtitle
        );

        homeLayout.getChildren().addAll(
                homeHeader,
                welcomeCard
        );

        Scene homeScene = new Scene(homeLayout, 1100, 750);

        // FOOD ITEMS WITH IMAGES

        FoodItem[] drinks = {
                new FoodItem("Sprite", "Drinks", 20, "sprite.jpg"),
                new FoodItem("Pulpy", "Drinks", 20, "pulpy.jpg"),
                new FoodItem("Maaza", "Drinks", 20, "maaza.jpg"),
                new FoodItem("Limca", "Drinks", 20, "limca.jpg")
        };

        FoodItem[] biryani = {
                new FoodItem("Chicken Biryani", "Biryani", 220, "chicken_biryani.jpg"),
                new FoodItem("Egg Biryani", "Biryani", 150, "egg_biryani.jpg"),
                new FoodItem("Paneer Biryani", "Biryani", 160, "paneer_biryani.jpg"),
                new FoodItem("Chicken Fry Piece Biryani", "Biryani", 240, "chicken_fry_piece_biryani.jpg"),
                new FoodItem("Mutton Biryani", "Biryani", 300, "mutton_biryani.jpg")
        };

        FoodItem[] dosa = {
                new FoodItem("Ghee Dosa", "Dosa", 70, "ghee_dosa.jpg"),
                new FoodItem("Plain Dosa", "Dosa", 35, "plain_dosa.jpg"),
                new FoodItem("Onion Dosa", "Dosa", 45, "onion_dosa.jpg"),
                new FoodItem("Egg Dosa", "Dosa", 50, "egg_dosa.jpg")
        };

        FoodItem[] juices = {
                new FoodItem("Watermelon Juice", "Juices", 60, "watermelon_juice.jpg"),
                new FoodItem("Kharbuja Juice", "Juices", 60, "kharbuja_juice.jpg"),
                new FoodItem("Grapes Juice", "Juices", 60, "grapes_juice.jpg")
        };

        FoodItem[] iceCream = {
                new FoodItem("Chocolate", "Ice Cream", 80, "chocolate.jpg"),
                new FoodItem("Caramel", "Ice Cream", 100, "caramel.jpg")
        };

        // MENU SCREEN

        VBox menuLayout = new VBox(18);
        menuLayout.setPadding(new Insets(20));
        menuLayout.setStyle(
                "-fx-background-color: #F1F5FA;"
        );

        Label menuTitle = new Label("FOOD MENU");

        menuTitle.setStyle(
                "-fx-font-size: 25px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #142D4E;"
        );

        menuLayout.getChildren().add(menuTitle);

        addCategory(menuLayout, "DRINKS", drinks);
        addCategory(menuLayout, "BIRYANI", biryani);
        addCategory(menuLayout, "DOSA", dosa);
        addCategory(menuLayout, "JUICES", juices);
        addCategory(menuLayout, "ICE CREAM", iceCream);

        ScrollPane scrollPane = new ScrollPane(menuLayout);
        scrollPane.setFitToWidth(true);

        scrollPane.setStyle(
                "-fx-background: #F1F5FA;" +
                        "-fx-background-color: #F1F5FA;"
        );

        // CART PANEL

        Label cartTitle = new Label("YOUR CART");

        cartTitle.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #142D4E;"
        );

        totalLabel.setStyle(
                "-fx-font-size: 23px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #142D4E;" +
                        "-fx-padding: 12 0 12 0;"
        );

        orderIdLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #142D4E;"
        );

        statusLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #142D4E;"
        );

        VBox cartLayout = new VBox(15);
        cartLayout.setPadding(new Insets(20));
        cartLayout.setPrefWidth(370);
        cartLayout.setMinWidth(330);

        cartLayout.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-color: #DCE6F2;" +
                        "-fx-border-radius: 15;"
        );

        cartLayout.getChildren().addAll(
                cartTitle,
                cartItemsLayout,
                totalLabel,
                placeOrderButton,
                orderIdLabel,
                statusLabel
        );

        // STATUS BUTTONS

        Button pendingButton = new Button("Pending");
        Button preparingButton = new Button("Preparing");
        Button completedButton = new Button("Completed");

        styleSecondaryButton(pendingButton);
        styleSecondaryButton(preparingButton);
        styleSecondaryButton(completedButton);

        HBox statusButtons = new HBox(7);
        statusButtons.setAlignment(Pos.CENTER);

        statusButtons.getChildren().addAll(
                pendingButton,
                preparingButton,
                completedButton
        );

        cartLayout.getChildren().addAll(
                statusButtons,
                receiptButton
        );

        stylePrimaryButton(placeOrderButton);
        stylePrimaryButton(receiptButton);

        placeOrderButton.setMaxWidth(Double.MAX_VALUE);
        receiptButton.setMaxWidth(Double.MAX_VALUE);

        // PLACE ORDER

        placeOrderButton.setOnAction(e -> {

            if (cart.isEmpty()) {

                orderIdLabel.setText(
                        "Please add items first!"
                );

            } else {

                String orderId =
                        String.format("SC%03d", orderCounter);

                orderCounter++;

                currentOrder = new Order(orderId, cart);

                orderHistory.add(currentOrder);

                saveOrderHistory();

                orderIdLabel.setText(
                        "Order ID: " + currentOrder.orderId
                );

                statusLabel.setText(
                        "Status: " + currentOrder.status
                );

                cart.clear();

                updateCart();
            }
        });

        // PENDING STATUS

        pendingButton.setOnAction(e -> {

            if (currentOrder != null) {

                currentOrder.status = "Pending";
                statusLabel.setText("Status: Pending");
                saveOrderHistory();
            }
        });

        // PREPARING STATUS

        preparingButton.setOnAction(e -> {

            if (currentOrder != null) {

                currentOrder.status = "Preparing";
                statusLabel.setText("Status: Preparing");
                saveOrderHistory();
            }
        });

        // COMPLETED STATUS

        completedButton.setOnAction(e -> {

            if (currentOrder != null) {

                currentOrder.status = "Completed";
                statusLabel.setText("Status: Completed");
                saveOrderHistory();
            }
        });

        // RECEIPT BUTTON

        receiptButton.setOnAction(e -> {

            if (currentOrder == null) {

                statusLabel.setText(
                        "Please place an order first!"
                );

            } else {

                generateReceipt(currentOrder);
            }
        });

        // HISTORY BUTTON

        historyButton.setOnAction(e -> showOrderHistory());

        // TOP HEADER FOR MENU

        Label appTitle = new Label("SMART CANTEEN");

        appTitle.setStyle(
                "-fx-font-size: 23px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );

        Button backButton = new Button("Home");
        styleSecondaryButton(backButton);

        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 22, 15, 22));
        header.setStyle(
                "-fx-background-color: #142D4E;"
        );

        header.getChildren().addAll(
                appTitle,
                backButton
        );

        // MAIN MENU LAYOUT

        HBox mainLayout = new HBox(18);
        mainLayout.setPadding(new Insets(18));
        mainLayout.setStyle(
                "-fx-background-color: #F1F5FA;"
        );

        HBox.setHgrow(
                scrollPane,
                javafx.scene.layout.Priority.ALWAYS
        );

        mainLayout.getChildren().addAll(
                scrollPane,
                cartLayout
        );

        VBox fullMenuLayout = new VBox();
        fullMenuLayout.getChildren().addAll(
                header,
                mainLayout
        );

        VBox.setVgrow(
                mainLayout,
                javafx.scene.layout.Priority.ALWAYS
        );

        Scene menuScene = new Scene(
                fullMenuLayout,
                1100,
                750
        );

        // NAVIGATION

        menuButton.setOnAction(e -> {

            stage.setScene(menuScene);
            stage.setTitle("Smart Canteen - Food Menu");
        });

        backButton.setOnAction(e -> {

            stage.setScene(homeScene);
            stage.setTitle("Smart Canteen");
        });

        stage.setTitle("Smart Canteen");
        stage.setScene(homeScene);
        stage.show();
    }

    // PRIMARY BUTTON STYLE

    void stylePrimaryButton(Button button) {

        button.setStyle(
                "-fx-background-color: #1D4E89;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 11 20 11 20;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"
        );
    }

    // SECONDARY BUTTON STYLE

    void styleSecondaryButton(Button button) {

        button.setStyle(
                "-fx-background-color: #EAF1FA;" +
                        "-fx-text-fill: #142D4E;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 9 12 9 12;" +
                        "-fx-background-radius: 7;" +
                        "-fx-border-color: #C7D6E8;" +
                        "-fx-border-radius: 7;" +
                        "-fx-cursor: hand;"
        );
    }

    // DISPLAY FOOD CATEGORIES WITH IMAGES

    void addCategory(
            VBox layout, String category, FoodItem[] items) {

        VBox categoryCard = new VBox(12);
        categoryCard.setPadding(new Insets(15));

        categoryCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #DCE6F2;" +
                        "-fx-border-radius: 12;"
        );

        Label categoryLabel = new Label(category);

        categoryLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #142D4E;"
        );

        categoryCard.getChildren().add(categoryLabel);

        for (FoodItem item : items) {

            // FOOD IMAGE

            ImageView imageView = new ImageView();

            imageView.setFitWidth(100);
            imageView.setFitHeight(75);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            String imagePath = "/images/" + item.imageName;

            java.net.URL imageURL =
                    getClass().getResource(imagePath);

            if (imageURL != null) {

                Image image = new Image(
                        imageURL.toExternalForm(),
                        100,
                        75,
                        true,
                        true
                );

                imageView.setImage(image);

                if (image.isError()) {
                    System.out.println(
                            "Image error: " + imagePath
                    );
                }

            } else {

                System.out.println(
                        "Image not found: " + imagePath
                );
            }

            // FOOD NAME AND PRICE

            Label itemLabel = new Label(
                    item.name + "\nRs." + item.price
            );

            itemLabel.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #334155;"
            );

            // ADD BUTTON

            Button addButton = new Button("Add +");
            styleSecondaryButton(addButton);

            HBox row = new HBox(15);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(5));

            row.getChildren().addAll(
                    imageView,
                    itemLabel,
                    addButton
            );

            HBox.setHgrow(
                    itemLabel,
                    javafx.scene.layout.Priority.ALWAYS
            );

            categoryCard.getChildren().add(row);

            addButton.setOnAction(e -> {

                cart.add(item);
                updateCart();
            });
        }

        layout.getChildren().add(categoryCard);
    }

    // UPDATE CART

    void updateCart() {

        cartItemsLayout.getChildren().clear();

        placeOrderButton.setDisable(cart.isEmpty());

        int total = 0;

        if (cart.isEmpty()) {

            Label emptyLabel = new Label(
                    "Your cart is empty."
            );

            emptyLabel.setStyle(
                    "-fx-text-fill: #64748B;" +
                            "-fx-font-size: 14px;"
            );

            cartItemsLayout.getChildren().add(emptyLabel);
        }

        for (int i = 0; i < cart.size(); i++) {

            FoodItem item = cart.get(i);

            if (cart.indexOf(item) == i) {

                int quantity = 0;

                for (FoodItem f : cart) {

                    if (f == item) {
                        quantity++;
                    }
                }

                Label itemLabel = new Label(
                        item.name + " (Rs." + item.price + ")"
                );

                itemLabel.setStyle(
                        "-fx-font-size: 13px;" +
                                "-fx-text-fill: #142D4E;"
                );

                Label quantityLabel = new Label(
                        "Qty: " + quantity
                );

                quantityLabel.setStyle(
                        "-fx-font-weight: bold;" +
                                "-fx-text-fill: #142D4E;"
                );

                Button plusButton = new Button("+");
                Button minusButton = new Button("-");
                Button removeButton = new Button("Remove");

                styleSecondaryButton(plusButton);
                styleSecondaryButton(minusButton);

                removeButton.setStyle(
                        "-fx-background-color: #FCE8E8;" +
                                "-fx-text-fill: #B42318;" +
                                "-fx-background-radius: 7;" +
                                "-fx-padding: 8 10 8 10;" +
                                "-fx-cursor: hand;"
                );

                HBox row = new HBox(7);
                row.setAlignment(Pos.CENTER_LEFT);

                row.getChildren().addAll(
                        itemLabel,
                        minusButton,
                        quantityLabel,
                        plusButton,
                        removeButton
                );

                cartItemsLayout.getChildren().add(row);

                plusButton.setOnAction(e -> {

                    cart.add(item);
                    updateCart();
                });

                minusButton.setOnAction(e -> {

                    cart.remove(item);
                    updateCart();
                });

                removeButton.setOnAction(e -> {

                    cart.removeIf(f -> f == item);
                    updateCart();
                });
            }
        }

        for (FoodItem item : cart) {
            total = total + item.price;
        }

        totalLabel.setText("Total: Rs." + total);
    }

    // GENERATE RECEIPT

    void generateReceipt(Order order) {

        String receipt = "";

        receipt = receipt + "       SMART CANTEEN\n";
        receipt = receipt + "       FOOD RECEIPT\n";
        receipt = receipt + "----------------------------\n";

        receipt = receipt + "Order ID: "
                + order.orderId + "\n";

        receipt = receipt + "Date: "
                + order.orderDateTime + "\n";

        receipt = receipt + "Status: "
                + order.status + "\n";

        receipt = receipt + "----------------------------\n";

        for (int i = 0; i < order.items.size(); i++) {

            FoodItem item = order.items.get(i);

            if (order.items.indexOf(item) == i) {

                int quantity = 0;

                for (FoodItem f : order.items) {

                    if (f == item) {
                        quantity++;
                    }
                }

                int amount = item.price * quantity;

                receipt = receipt + item.name + "\n";

                receipt = receipt + "Qty: " + quantity
                        + "  Price: Rs." + amount + "\n";
            }
        }

        receipt = receipt + "----------------------------\n";
        receipt = receipt + "TOTAL: Rs." + order.total + "\n";
        receipt = receipt + "----------------------------\n";
        receipt = receipt + "Thank You! Visit Again.";

        TextArea receiptArea = new TextArea(receipt);

        receiptArea.setEditable(false);
        receiptArea.setWrapText(true);
        receiptArea.setPrefWidth(350);
        receiptArea.setPrefHeight(400);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Smart Canteen Receipt");
        alert.setHeaderText("Your Order Receipt");

        alert.getDialogPane().setContent(receiptArea);

        alert.showAndWait();
    }

    // DISPLAY ORDER HISTORY

    void showOrderHistory() {

        String history = "";

        if (orderHistory.isEmpty()) {

            history = "No orders placed yet.";

        } else {

            history = "       DAILY ORDER HISTORY\n";

            history = history
                    + "----------------------------\n";

            for (Order order : orderHistory) {

                history = history + "\nOrder ID: "
                        + order.orderId + "\n";

                history = history + "Date: "
                        + order.orderDateTime + "\n";

                history = history + "Status: "
                        + order.status + "\n";

                history = history + "Items:\n";

                for (int i = 0; i < order.items.size(); i++) {

                    FoodItem item = order.items.get(i);

                    if (order.items.indexOf(item) == i) {

                        int quantity = 0;

                        for (FoodItem f : order.items) {

                            if (f == item) {
                                quantity++;
                            }
                        }

                        history = history + item.name
                                + " x " + quantity + "\n";
                    }
                }

                history = history + "Total: Rs."
                        + order.total + "\n";

                history = history
                        + "----------------------------\n";
            }
        }

        TextArea historyArea = new TextArea(history);

        historyArea.setEditable(false);
        historyArea.setWrapText(true);
        historyArea.setPrefWidth(400);
        historyArea.setPrefHeight(450);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Daily Order History");
        alert.setHeaderText("Previous Orders");

        alert.getDialogPane().setContent(historyArea);

        alert.showAndWait();
    }

    // SAVE ORDER HISTORY

    void saveOrderHistory() {

        try {

            FileWriter writer = new FileWriter("orders.txt");

            for (Order order : orderHistory) {

                String line = order.orderId + "|"
                        + order.status + "|"
                        + order.orderDateTime + "|";

                for (int i = 0; i < order.items.size(); i++) {

                    FoodItem item = order.items.get(i);

                    line = line + item.name + "~"
                            + item.category + "~"
                            + item.price;

                    if (i < order.items.size() - 1) {
                        line = line + ";";
                    }
                }

                writer.write(line + "\n");
            }

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error saving order history."
            );
        }
    }

    // LOAD ORDER HISTORY

    void loadOrderHistory() {

        try {

            File file = new File("orders.txt");

            if (!file.exists()) {
                return;
            }

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] parts = line.split("\\|", 4);

                if (parts.length != 4) {
                    continue;
                }

                String orderId = parts[0];
                String status = parts[1];
                String dateTime = parts[2];

                ArrayList<FoodItem> savedItems =
                        new ArrayList<>();

                String[] itemParts = parts[3].split(";");

                for (String data : itemParts) {

                    String[] details = data.split("~", 3);

                    if (details.length == 3) {

                        String name = details[0];
                        String category = details[1];
                        int price = Integer.parseInt(details[2]);

                        savedItems.add(
                                new FoodItem(name, category, price)
                        );
                    }
                }

                Order order = new Order(
                        orderId,
                        status,
                        dateTime,
                        savedItems
                );

                orderHistory.add(order);

                int number = Integer.parseInt(
                        orderId.substring(2)
                );

                if (number >= orderCounter) {
                    orderCounter = number + 1;
                }
            }

            scanner.close();

        } catch (Exception e) {

            System.out.println(
                    "Error loading order history."
            );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
