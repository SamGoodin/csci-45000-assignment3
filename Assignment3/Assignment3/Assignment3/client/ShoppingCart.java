package Assignment3.client;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import Assignment3.client.CartRem; // import interface
import Assignment3.client.Item;

// Runs on Server
public class ShoppingCart implements CartRem {
    private List<Item> cartList;
    private HashMap<Item, Integer> cartData;

    public ShoppingCart() throws RemoteException {
        cartList = new ArrayList<>();
        cartData = new HashMap<Item, Integer>();
        System.out.println("ShoppingCart: Shopping cart created");
    }

    public void clearCart() {
        cartList.clear();
        cartData.clear();
    }

    public boolean inCart(String itemName) {
        if (this.getItem(itemName) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void changeQuantity(String itemName, int quantity) {
        Item itemToChange = this.getItem(itemName);
        cartData.put(itemToChange, quantity);
    }

    public void removeItem(String itemName, int quantity) {
        int currentQty = cartData.get(this.getItem(itemName));
        int newQty = currentQty - quantity;
        if (newQty >= 1) {
            this.changeQuantity(itemName, newQty);
            System.out.println("ShoppingCart: Item " + itemName + " quantity now " + newQty + "!");
        } else {
            // Remove from data
            Item itemToRemove = this.getItem(itemName);
            cartData.remove(itemToRemove);

            // Remove all instances from list
            for (int i = 0; i < currentQty; i++) {
                itemToRemove = this.getItem(itemName);
                cartList.remove(itemToRemove);
            }
            System.out.println("ShoppingCart: Item " + itemName + " removed!");
        }
    }

    public void addItem(String itemName, int quantity) {
        Item itemToAdd = this.getItem(itemName);
        if (inCart(itemName)) {
            cartData.put(itemToAdd, cartData.get(itemToAdd) + quantity);
        } else {
            cartData.put(itemToAdd, quantity);
        }
        for (int i = 0; i < quantity; i++) {
            cartList.add(itemToAdd);
        }
        System.out.println("ShoppingCart: Item " + itemName + " added to cart!");
    }

    public Item getItem(String itemName) {
        for (Item item : cartList) {
            if (item.getItemName().equals(itemName)) {
                System.out.println("ShoppingCart: Item found!");
                return item;
            }
        }
        System.out.println("ShoppingCart: Item not found!");
        return null;
    }

    public Map<Item, Integer> checkout() {
        System.out.println(this.output());
        double total = 0.0;
        int totalItems = 0;
        for (Item item : cartList) {
            total += (item.getPrice());
            totalItems++;
        }
        System.out.println("Total: " + String.valueOf(total) + "\nNumber of Items: " + String.valueOf(totalItems));
        HashMap<Item, Integer> copy = cartData;
        this.clearCart();
        return copy;
    }

    public String output() {
        String output = "Shopping Cart:\n";
        for (Item item : cartList) {
            output += (item.output());
        }
        return output;
    }

}