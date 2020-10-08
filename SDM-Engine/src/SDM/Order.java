package SDM;

import SDM.Exception.NegativeAmountOfItemInException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Order
{
    private static int idCounter = 1;
    protected final int id;
    protected final Map<Integer, OrderItem> orderItemCart;
    protected final Customer customer;
    protected final Date date;
    protected double deliveryPrice;
    protected double priceOfAllItems;
    protected double totalPrice;

    public Order(Customer customer, Date date) {
        this.customer = customer;
        this.date = date;
        this.id = idCounter;
        orderItemCart = new HashMap<>();
    }

    //Might be better in an order factory class
    public static Order makeNewOrder(Customer customer, Date date, Store storeOrderMadeFrom, OrderType orderType) {
        if(orderType == OrderType.ONE_STORE_ORDER) {
            return new OneStoreOrder(customer, date, storeOrderMadeFrom);
        }
        else if (orderType == OrderType.DYNAMIC_ORDER) {
            return new DynamicOrder(customer, date);
        }
        else {
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public Map<Integer, OrderItem> getOrderItemCart() {
        return orderItemCart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getDate() {
        return date;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public double getPriceOfAllItems() {
        return priceOfAllItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isEmpty() {
        return orderItemCart.size() == 0;
    }

    protected void incIdCounter() {
        idCounter++;
    }

    protected abstract void calculateDeliveryPrice();
    public abstract void addItemToOrder(Item itemToAdd, double amountToAdd) throws NegativeAmountOfItemInException;
    public abstract void completeOrder() throws NegativeAmountOfItemInException;
    protected abstract double calculatePriceOfOrderItems();
    public abstract int getTotalItemsInOrder();
}
