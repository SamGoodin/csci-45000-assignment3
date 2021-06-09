package Assignment3.client;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import Assignment3.client.LoginRem; //import interface
import Assignment3.client.ShoppingCart;
import Assignment3.client.Item;

// Runs on Server
public class LoginRemImpl extends UnicastRemoteObject implements LoginRem {
    static ShoppingCart cart;
    static boolean loggedIn;
    static int accountType;

    public LoginRemImpl() throws RemoteException {
        super();
        loggedIn = false;
        accountType = -1;
    }

    public int addNum(int a, int b) {
        System.out.println("LoginRemImpl server: got request from client");
        System.out.println("Sending response...");
        return (a + b);
    }

    public String[] findItem(String itemName) {
        if (loggedIn) {
            try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/common/Items.csv"))) {
                String line;
                // Searching for itemName
                while ((line = br.readLine()) != null) {
                    System.out.println("Item: " + line);
                    String[] values = line.split(",");
                    if (itemName.equals(values[0])) {
                        System.out.println("Found item " + itemName + "!");
                        return values;
                    }
                }
            } catch (Exception e) {
                System.out.println("Remote findItem:browse err: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }

    }

    public int login(String username, String password, int type) {
        if (!loggedIn) {
            // Type 0: user | Type 1: admin
            System.out.println("===Request to Login " + type + "===");
            if (type == 0) {
                // User login: samg | koolaid
                try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/common/UserLogins.csv"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println("User: " + line);
                        String[] values = line.split(",");
                        if (username.equals(values[0]) && password.equals(values[1])) {
                            loggedIn = true;
                            accountType = 0;
                            this.createCart();
                            return (1);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Remote login err: " + e.getMessage());
                    e.printStackTrace();
                }
            } else if (type == 1) {
                // Admin login: sammy | pass
                try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/common/AdminLogins.csv"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println("Admin: " + line);
                        String[] values = line.split(",");
                        if (username.equals(values[0]) && password.equals(values[1])) {
                            loggedIn = true;
                            accountType = 1;
                            this.createCart();
                            return (1);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Remote login err: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            System.out.println("===Login request complete===");
            return (0);
        } else {
            return -1;
        }

    }

    public int logout() {
        if (loggedIn) {
            cart.clearCart();
            cart = null;
            loggedIn = false;
            accountType = -1;
            return (1);
        } else {
            return -1;
        }

    }

    public int register(String username, String password, int type) {
        // Type 0 = user | Type 1 = admin
        // NOTE: both ways of writing to CSV file work!
        System.out.println("===Request to Register " + type + "===");
        try {
            if (type == 0) {
                String toWrite = username + "," + password + "\n";
                Files.write(Paths.get("Assignment3/common/UserLogins.csv"), toWrite.getBytes(),
                        StandardOpenOption.APPEND);
                return 1;
            } else if (type == 1) {
                FileWriter csvWriter = new FileWriter("Assignment3/common/AdminLogins.csv");
                csvWriter.append(username + "," + password + "\n");
                csvWriter.flush();
                csvWriter.close();
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Remote register err: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("===Registration request complete===");
        return 0;

    }

    public List<String[]> browse() {
        if (loggedIn) {
            System.out.println("===Request to Browse===");
            List<String[]> output = new ArrayList<String[]>();
            try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/common/Items.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("Item: " + line);
                    String[] values = line.split(",");
                    output.add(values);
                }
            } catch (Exception e) {
                System.out.println("Remote browse err: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("===Browse request complete===");
            return output;
        } else {
            return null;
        }
    }

    public List<String[]> removeItem(String itemName) {
        if (loggedIn && accountType == 1) {
            System.out.println("===Request to Remove Item===");
            List<String[]> output = new ArrayList<String[]>();
            String toWrite = "";
            try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/common/Items.csv"))) {
                String line;
                // Searching for itemName
                while ((line = br.readLine()) != null) {
                    System.out.println("Item: " + line);
                    String[] values = line.split(",");
                    if (itemName.equals(values[0])) {
                        System.out.println("Found item " + itemName + "!");

                    } else {
                        output.add(values);
                        toWrite += (values[0] + "," + values[1] + "," + values[2] + "\n");
                    }
                }
            } catch (Exception e) {
                System.out.println("Remote removeItem:browse err: " + e.getMessage());
                e.printStackTrace();
            }
            // Rewrite without itemName
            try {
                Files.write(Paths.get("Assignment3/common/Items.csv"), toWrite.getBytes(), StandardOpenOption.WRITE);
            } catch (Exception e) {
                System.out.println("Remote removeItem:write err: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("===Remove Item request complete===");
            return output;
        } else {
            return null;
        }
    }

    public int addItem(String itemName, double price, int quantity) {
        if (loggedIn && accountType == 1) {
            System.out.println("===Request to Add Item===");
            List<String[]> output = new ArrayList<String[]>();
            String toWrite = itemName + "," + String.valueOf(price) + "," + String.valueOf(quantity) + "\n";

            // Write to file
            try {
                Files.write(Paths.get("Assignment3/common/Items.csv"), toWrite.getBytes(), StandardOpenOption.APPEND);
            } catch (Exception e) {
                System.out.println("Remote addItem:write err: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("===Add Item request complete===");
            return 1;
        } else {
            return -1;
        }
    }

    public void updateItem(String itemName, String newItemName, double newPrice, int newQty) {
        if (loggedIn && accountType == 1) {
            System.out.println("===Request to Update Item " + itemName + "===");
            List<String[]> output = new ArrayList<String[]>();
            String toWrite = "";
            try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/common/Items.csv"))) {
                String line;
                // Searching for itemName
                while ((line = br.readLine()) != null) {
                    System.out.println("Item: " + line);
                    String[] values = line.split(",");
                    if (itemName.equals(values[0])) {
                        System.out.println("Found item " + itemName + "!");
                        toWrite += (newItemName + "," + newPrice + "," + newQty + "\n");
                    } else {
                        toWrite += (values[0] + "," + values[1] + "," + values[2] + "\n");
                    }
                    output.add(values);
                }
            } catch (Exception e) {
                System.out.println("Remote updateItem:browse err: " + e.getMessage());
                e.printStackTrace();
            }
            // Rewrite with update
            // substring to remove final newline char
            toWrite = toWrite.substring(0, toWrite.length() - 1);
            System.out.println(toWrite);
            try {
                Files.write(Paths.get("Assignment3/common/Items.csv"), toWrite.getBytes(), StandardOpenOption.WRITE);
            } catch (Exception e) {
                System.out.println("Remote updateItem:write err: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("===Update Item request complete===");
        }
    }

    public void createCart() {
        if (loggedIn) {
            try {
                cart = new ShoppingCart();
            } catch (Exception e) {
                System.out.println("Remote createCart err: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void addItemToCart(String itemName, int quantity) {
        cart.addItem(itemName, quantity);
    }

    public void removeItemFromCart(String itemName, int quantity) {
        cart.removeItem(itemName, quantity);
    }

    public void purchaseCart() {
        if (loggedIn) {
            System.out.println("===Request to Purchase Cart===");
            Map<Item, Integer> cartData = cart.checkout();
            String itemName;
            double price;
            String[] itemData;
            int itemQty, quantity;
            // Use cartData to adjust item quantities
            for (Map.Entry<Item, Integer> entry : cartData.entrySet()) {
                itemName = entry.getKey().getItemName();
                price = entry.getKey().getPrice();
                itemData = this.findItem(itemName);
                itemQty = Integer.valueOf(itemData[1]);
                quantity = entry.getValue();
                this.updateItem(itemName, itemName, price, (itemQty - quantity));
            }
            System.out.println("===Purchase Cart request complete===");
        }
    }

    public ShoppingCart getCart() {
        return cart;
    }
}