package Assignment3.client;

import java.rmi.*;
import java.net.*;
import java.io.*;
import java.util.*;

import Assignment3.client.LoginRem; //import interface

// Runs on Local
public class MainClient {

    public static void main(String args[]) {

        try {
            String name = "//in-csci-rrpc01:6724/LoginRemImpl";
            LoginRem remobj = (LoginRem) Naming.lookup(name);

            System.out.println("----------Client connected to Server-----------");
            Scanner sc = new Scanner(System.in);
            boolean isRunning = true;
            while (isRunning) {
                System.out.println("Welcome. Do you need to\n1. Login\n2. Register\n3. Exit\n");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        // Login
                        boolean loginLoop = true;
                        while (loginLoop) {
                            System.out.println("Are you an administrator?\n1. Yes\n2. No\n");
                            int acc = sc.nextInt();
                            sc.nextLine();
                            System.out.println("What is your username?");
                            String username = sc.nextLine();
                            System.out.println("What is your password?");
                            String password = sc.nextLine();
                            int status = -1;
                            if (acc == 1) {
                                // Admin Account
                                status = remobj.login(username, password, 1);
                            } else {
                                // User Account
                                status = remobj.login(username, password, 0);
                            }
                            if (status == 1) {
                                // Successful login
                                System.out.println("Login successful!\n");
                                boolean clientLoop = true;
                                while (clientLoop) {
                                    int clientChoice;
                                    switch (acc) {
                                        case 1:
                                            // Admin
                                            /*
                                             * 1. Browse 2. Update Item (store) 3. Remove Item (store) 4. Add Item
                                             * (store) 5. View Cart 6. Add Item (cart) 7. Remove Item (cart) 8. Logout
                                             */
                                            System.out.println(
                                                    "Select what you want to do:\n1.Browse\n2.Update Item (in store)\n3.Remove Item (from store)\n4.Add Item (to store)\n5.View Cart\n6.Add Item (to cart)\n7.Remove Item (from cart)\n8.Logout");
                                            clientChoice = sc.nextInt();
                                            sc.nextLine();
                                            if (clientChoice < 1 || clientChoice > 8) {
                                                System.out.println("Incorrect option selected.");
                                            } else {
                                                String itemName, newItemName;
                                                double price;
                                                int quantity;
                                                List<String[]> browsedItems;
                                                switch (clientChoice) {
                                                    case 1:
                                                        // Browse
                                                        browsedItems = remobj.browse();
                                                        System.out.println("------Browsed Items------");
                                                        for (String[] str : browsedItems) {
                                                            System.out.println(Arrays.toString(str));
                                                        }
                                                        break;
                                                    case 2:
                                                        // Update Item (store)
                                                        System.out.println(
                                                                "What is the current name of the item to be updated?");
                                                        itemName = sc.nextLine();
                                                        System.out.println("What is the new name of the item?");
                                                        newItemName = sc.nextLine();
                                                        System.out.println("What is the price of the item?");
                                                        price = sc.nextDouble();
                                                        sc.nextLine();
                                                        System.out.println("What is the quantity of the item?");
                                                        quantity = sc.nextInt();
                                                        sc.nextLine();
                                                        remobj.updateItem(itemName, newItemName, price, quantity);
                                                        browsedItems = remobj.browse();
                                                        System.out.println("------Browsed Items------");
                                                        for (String[] str : browsedItems) {
                                                            System.out.println(Arrays.toString(str));
                                                        }
                                                        break;
                                                    case 3:
                                                        // Remove Item (store)
                                                        System.out
                                                                .println("What is the name of the item to be removed?");
                                                        itemName = sc.nextLine();
                                                        List<String[]> itemList = remobj.removeItem(itemName);
                                                        System.out.println("------New Item List------");
                                                        for (String[] str : itemList) {
                                                            System.out.println(Arrays.toString(str));
                                                        }
                                                        break;
                                                    case 4:
                                                        // Add Item (store)
                                                        System.out.println("What is the name of the item to be added?");
                                                        newItemName = sc.nextLine();
                                                        System.out.println("What is the price of the item?");
                                                        price = sc.nextDouble();
                                                        sc.nextLine();
                                                        System.out.println("What is the quantity of the item?");
                                                        quantity = sc.nextInt();
                                                        sc.nextLine();
                                                        int output = remobj.addItem(newItemName, price, quantity);
                                                        if (output == 1) {
                                                            System.out.println("Item successfully added!");
                                                        } else {
                                                            System.out.println("Item was not added!");
                                                        }
                                                        break;
                                                    case 5:
                                                        // View Cart
                                                        System.out.println(remobj.getCart().output());
                                                        break;
                                                    case 6:
                                                        // Add Item (cart)
                                                        System.out.println("What is the name of the item to be added?");
                                                        newItemName = sc.nextLine();
                                                        System.out.println(
                                                                "What is the quantity of the item to be added?");
                                                        quantity = sc.nextInt();
                                                        sc.nextLine();
                                                        remobj.addItemToCart(newItemName, quantity);
                                                        System.out.println(remobj.getCart().output());
                                                        break;
                                                    case 7:
                                                        // Remove Item (cart)
                                                        System.out
                                                                .println("What is the name of the item to be removed?");
                                                        newItemName = sc.nextLine();
                                                        System.out.println(
                                                                "What is the quantity of the item to be removed?");
                                                        quantity = sc.nextInt();
                                                        sc.nextLine();
                                                        remobj.addItemToCart(newItemName, quantity);
                                                        System.out.println(remobj.getCart().output());
                                                        break;
                                                    case 8:
                                                        // Logout
                                                        remobj.logout();
                                                        clientLoop = false;
                                                        loginLoop = false;
                                                        break;
                                                }
                                            }
                                            break;
                                        case 2:
                                            // User
                                            // 1. Browse
                                            // 2. View Cart
                                            // 3. Add Item to cart
                                            // 4. Remove item from cart
                                            // 5. Logout
                                            System.out.println(
                                                    "Select what you want to do:\n1.Browse\n2.View Cart\n3.Add Item (to cart)\n4.Remove Item (from cart)\n5.Logout");
                                            clientChoice = sc.nextInt();
                                            sc.nextLine();
                                            if (clientChoice < 1 || clientChoice > 5) {
                                                System.out.println("Incorrect option selected.");
                                            } else {
                                                String newItemName;
                                                double price;
                                                int quantity;
                                                List<String[]> browsedItems;
                                                switch (clientChoice) {
                                                    case 1:
                                                        // Browse
                                                        browsedItems = remobj.browse();
                                                        System.out.println("------Browsed Items------");
                                                        for (String[] str : browsedItems) {
                                                            System.out.println(Arrays.toString(str));
                                                        }
                                                        break;
                                                    case 2:
                                                        // View Cart
                                                        System.out.println(remobj.getCart().output());
                                                        break;
                                                    case 3:
                                                        // Add Item (cart)
                                                        System.out.println("What is the name of the item to be added?");
                                                        newItemName = sc.nextLine();
                                                        System.out.println(
                                                                "What is the quantity of the item to be added?");
                                                        quantity = sc.nextInt();
                                                        sc.nextLine();
                                                        remobj.addItemToCart(newItemName, quantity);
                                                        System.out.println(remobj.getCart().output());
                                                        break;
                                                    case 4:
                                                        // Remove Item (cart)
                                                        System.out
                                                                .println("What is the name of the item to be removed?");
                                                        newItemName = sc.nextLine();
                                                        System.out.println(
                                                                "What is the quantity of the item to be removed?");
                                                        quantity = sc.nextInt();
                                                        sc.nextLine();
                                                        remobj.addItemToCart(newItemName, quantity);
                                                        System.out.println(remobj.getCart().output());
                                                        break;
                                                    case 5:
                                                        // Logout
                                                        remobj.logout();
                                                        clientLoop = false;
                                                        loginLoop = false;
                                                        break;
                                                }
                                            }
                                            break;

                                    }
                                }

                            } else {
                                // Unsuccessful login
                                System.out.println("Login unsuccessful! Please try again.");
                                loginLoop = false;
                                break;
                            }
                        }
                        break;
                    case 2:
                        // Register
                        System.out.println("====Registration=====");
                        System.out.println("Enter your username: ");
                        String username = sc.nextLine();
                        System.out.println("Enter your password: ");
                        String password = sc.nextLine();
                        System.out.println("Enter 0 for user account or 1 for admin: ");
                        int type = sc.nextInt();
                        sc.nextLine();
                        int registerStatus = remobj.register(username, password, type);
                        if (registerStatus == 1) {
                            System.out.println("Registration successful! Please login.");
                        } else {
                            System.out.println(
                                    "Registration unsuccessful! Please try again. " + String.valueOf(registerStatus));
                        }
                        break;
                    case 3:
                        // Exit
                        isRunning = false;
                        break;
                }
            }
            System.out.println("----------Client disconnected from Server-----------");

            /*
             * ~Update Item~
             * 
             * remobj.updateItem("Mouse", "Keyboard", 14.55, 1); List<String[]> browsedItems
             * = remobj.browse(); System.out.println("------Browsed Items------"); for
             * (String[] str : browsedItems) { System.out.println(Arrays.toString(str)); }
             */

            /*
             * ~Add Item~
             * 
             * String output = remobj.addItem("Keyboard", 25.99, 13);
             * System.out.println(output);
             */

            /*
             * ~Remove Item~
             * 
             * List<String[]> itemList = remobj.removeItem("Apple");
             * System.out.println("------New Item List------"); for (String[] str :
             * itemList) { System.out.println(Arrays.toString(str)); }
             */

            /*
             * ~Browse~
             * 
             * List<String[]> browsedItems = remobj.browse();
             * System.out.println("------Browsed Items------"); for (String[] str :
             * browsedItems) { System.out.println(Arrays.toString(str)); }
             */

            /*
             * ~Login/Register~
             * 
             * Scanner sc = new Scanner(System.in);
             * System.out.println("What would you like to do?");
             * System.out.println("1: Registration"); System.out.println("2: Login"); int
             * choice = sc.nextInt();
             * 
             * System.out.println("Enter your username: "); String username = sc.nextLine();
             * System.out.println("Enter your password: "); String password = sc.nextLine();
             * System.out.println("Enter 0 for user or 1 for admin: "); int type =
             * sc.nextInt();
             * 
             * switch (choice) { case 1: // Registration
             * 
             * // Send to server to login String regStatus = remobj.register(username,
             * password, type); System.out.println("Server Response Received:");
             * System.out.println(regStatus);
             * 
             * case 2: // Login
             * 
             * // Send to server to login String loginStatus = remobj.login(username,
             * password, type); System.out.println("Server Response Received:");
             * System.out.println(loginStatus); }
             */

        } catch (Exception e) {
            System.out.println("Client err : " + e.getMessage());
            e.printStackTrace();
        }
    }

}