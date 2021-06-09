package Assignment3.client;

public class Item {
    private String itemName;
    private double price;
    private int quantity;

    public Item(String itemName, double price, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return quantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQty(int quantity) {
        this.quantity = quantity;
    }

    public String output() {
        return ("Name: " + itemName + "\nPrice: $" + String.valueOf(price) + "\nQty: " + String.valueOf(quantity)
                + "\n");
    }

}