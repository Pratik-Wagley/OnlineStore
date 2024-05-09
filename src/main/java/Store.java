
import com.pluralsight.Product;
import com.pluralsight.UsersBalance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        UsersBalance usersBalance = new UsersBalance(500);

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.println("4. Find product by ID");
            System.out.println("5. Checkout");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                case 4:
                    findProductById(inventory, scanner);
                case 5:
                    checkOut(cart, scanner, usersBalance);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String id = (parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    Product product = new Product(id, name, price);
                    inventory.add(product);
                } else {
                    System.out.println("error" + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error");
        }

    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        System.out.println("Inveentory");
        System.out.println(inventory);
        System.out.println("To add a product to you cart, enter the product ID: ");
        String chosenProductId = scanner.nextLine();
        for (Product product : inventory) {
            if (product.getId().equals(chosenProductId)) {
                cart.add(product);
                System.out.println(product.getName() + " is successfully added to your cart!");
            }

        }
        // This method should display a list of products from the inventory,
        // and prompt the user to add items to their cart. The method should
        // prompt the user to enter the ID of the product they want to add to
        // their cart. The method should
        // add the selected product to the cart ArrayList.
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {
        System.out.println("Cart: ");
        for (Product product : cart) {
            System.out.println("Product id: " + product.getId() + " Name: " + product.getName());
            totalAmount = totalAmount + product.getPrice();
        }
        System.out.println("Your total is: $" + totalAmount);

        boolean running = true;
        System.out.println("To remove a product from your cart, enter the product ID.\nTo not make any changes enter X: ");
        String userInput = scanner.nextLine();
        while (running) {
            if (userInput.equalsIgnoreCase("X")) {
                running = false;
            }
            for (Product product : cart) {
                if (userInput.equals(product.getId())) {
                    cart.remove(product);
                    totalAmount -= product.getPrice();
                    System.out.println("Your cart has been updated successfully!\nHere is your updated cart: ");
                    for (Product updatedProduct : cart) {
                        System.out.println("Product id: " + product.getId() + " Name: " + product.getName());
                    }
                    System.out.println("Your total is: $" + totalAmount);

                    System.out.println("To remove another product from your cart, enter the product ID.\nTo not make any changes enter X: ");
                    userInput = scanner.nextLine();

                }
            }
            if (running) {
                System.out.println("To remove another product from your cart, enter the product ID.\nTo not make any changes enter X: ");
                userInput = scanner.nextLine();

            } else {
                System.out.println("Invalid Input, please try again!");

            }
        }


        // This method should display the items in the cart ArrayList, along
        // with the total cost of all items in the cart. The method should
        // prompt the user to remove items from their cart by entering the ID
        // of the product they want to remove. The method should update the cart ArrayList and totalAmount
        // variable accordingly.
    }

    public static void checkOut(ArrayList<Product> cart, Scanner scanner, UsersBalance usersBalance) {
        double totalAmount = 0;
        for (Product product : cart) {
            totalAmount = totalAmount + product.getPrice();
        }
        System.out.println("Your total amount is: $" + totalAmount);
        System.out.println("To confirm your purchase enter C: ");
        String userInput = scanner.nextLine().toUpperCase();
        if (userInput.equals("C")) {
            while (usersBalance.getUsersBalance() >= totalAmount) {
                System.out.println("Purchase confirmed!\n You payed: " + totalAmount);
                usersBalance.setUsersBalance(usersBalance.getUsersBalance() - totalAmount);
                System.out.println("Your remaining balance is " + usersBalance.getUsersBalance());
            }
            if (usersBalance.getUsersBalance() < totalAmount) {
                System.out.println("Insufficient Funds");
            }

        } else {
            System.out.println("Invalid command");
        }
        // This method should calculate the total cost of all items in the cart,
        // and display a summary of the purchase to the user. The method should
        // prompt the user to confirm the purchase, and deduct the total cost
        // from their account if they confirm.
    }

    public static void findProductById(ArrayList<Product> inventory, Scanner scanner) {
        System.out.println("Enter the product ID to find your product: ");
        String userInput = scanner.nextLine();
        for (Product product : inventory) {
            if (userInput.equals(product.getId())) {
                System.out.println("Your product is: " + product.getName());
            } else {
                System.out.println("Product not found");
            }

        }


        // This method should search the inventory ArrayList for a product with
        // the specified ID, and return the corresponding Product object. If
        // no product with the specified ID is found, the method should return
        // null.
    }
}