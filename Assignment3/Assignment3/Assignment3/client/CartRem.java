package Assignment3.client;

import java.rmi.*;
import java.util.*;

public interface CartRem {

    // Clear Cart
    public void clearCart();

    // In Cart
    public boolean inCart(String itemName);

    // Change Quantity of Item in Cart
    public void changeQuantity(String itemName, int quantity);

    // Remove Item from Cart
    public void removeItem(String itemName, int quantity);

    // Add Item to Cart
    public void addItem(String itemName, int quantity);

    // Get Item from Cart
    public Item getItem(String itemName);

    // Checkout
    public Map<Item, Integer> checkout();

    // Output Cart
    public String output();
}