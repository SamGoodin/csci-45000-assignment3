package Assignment3.client;

import java.rmi.*;
import java.util.*;

public interface LoginRem extends Remote {

    public int addNum(int a, int b) throws RemoteException;

    // Find Item
    public String[] findItem(String itemName) throws RemoteException;

    // Login
    public int login(String username, String password, int type) throws RemoteException;

    // Logout
    public int logout() throws RemoteException;

    // Register
    public int register(String username, String password, int type) throws RemoteException;

    // Browse
    public List<String[]> browse() throws RemoteException;

    // Remove Item
    public List<String[]> removeItem(String itemName) throws RemoteException;

    // Add Item
    public int addItem(String itemName, double price, int quantity) throws RemoteException;

    // Update Item
    public void updateItem(String itemName, String newItemName, double newPrice, int newQty) throws RemoteException;

    // Create Cart
    public void createCart() throws RemoteException;

    // Add item to Cart
    public void addItemToCart(String itemName, int quantity) throws RemoteException;

    // Remove item from cart
    public void removeItemFromCart(String itemName, int quantity) throws RemoteException;

    // Purchase Cart
    public void purchaseCart() throws RemoteException;

    // Get Cart
    public ShoppingCart getCart() throws RemoteException;

}