package SDM;

import SDM.Exception.NegativeAmountOfItemInException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order
{
    private static int idCounter = 1;
    private final int id;
    private final Map<Integer, OrderItem> orderItemCart;
    private final Store storeOrderMadeFrom;
    private final Customer customer;
    private final Date date;
    private final double deliveryPrice;
    private double priceOfAllItems;
    private double totalPrice;

    private Order(Customer customer, Date date, Store storeOrderMadeFrom) {
        this.customer = customer;
        this.date = date;
        this.id = idCounter;
        this.storeOrderMadeFrom = storeOrderMadeFrom;
        deliveryPrice = storeOrderMadeFrom.getDeliveryPPK() * distanceBetweenCostumerAndStore();
        orderItemCart = new HashMap<>();
    }

    public static Order makeNewOrder(Customer customer, Date date, Store storeOrderMadeFrom) {
        return new Order(customer, date, storeOrderMadeFrom);
    }

    public int getId() {
        return id;
    }

    public Map<Integer, OrderItem> getOrderItemCart() {
        return orderItemCart;
    }

    public Store getStoreOrderMadeFrom() {
        return storeOrderMadeFrom;
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

    public void addItemToOrder(StoreItem itemToAdd, String amountToAdd) throws NegativeAmountOfItemInException {
        if(orderItemCart.containsKey(itemToAdd.getItem().getId())) {
            orderItemCart.get(itemToAdd.getItem().getId()).addAmount(amountToAdd);
        }
        else {
            OrderItem newItemInOrder = OrderItem.Factory.makeNewOrderItem(itemToAdd);
            newItemInOrder.addAmount(amountToAdd);
            orderItemCart.put(itemToAdd.getItem().getId(), newItemInOrder);
        }
    }

    public void completeOrder() {
        idCounter++;
        priceOfAllItems = calculatePriceOfOrderItems();
        totalPrice = priceOfAllItems + deliveryPrice;
        orderItemCart.forEach((orderItemID, orderItem) -> {
            try {
                orderItem.updateItemAmountSold();
                orderItem.updateStoreItemAmountSold();
            } catch (NegativeAmountOfItemInException e) {
                orderItem.clearAmount();
            }
        });
        customer.addNewOrder(this);
        storeOrderMadeFrom.getOrders().add(this);
    }

    private double calculatePriceOfOrderItems() {
        return orderItemCart.values().stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public double distanceBetweenCostumerAndStore() {
        return Location.distanceBetweenLocations(customer.getLocation(), storeOrderMadeFrom.getLocation());
    }

    public int getTotalItemsInOrder()
    {
        int totalItems=0;

        for (Map.Entry<Integer, OrderItem> iterator : orderItemCart.entrySet())
        {
            OrderItem orderItem = iterator.getValue();
            Item.ItemType type=orderItem.getItemInOrder().getItem().getType();

            switch (type)
            {
                case QUANTITY:
                    totalItems+=((OrderQuantityItem)orderItem).getQuantity();
                    break;
                case WEIGHT:
                    totalItems+=1;
                    break;
            }
        }
        return (totalItems);
    }
}
