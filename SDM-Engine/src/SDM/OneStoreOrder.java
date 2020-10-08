package SDM;

import SDM.Exception.NegativeAmountOfItemInException;

import java.util.Date;
import java.util.Map;

public class OneStoreOrder extends Order {

    private final Store storeOrderMadeFrom;
    private boolean partOfDynamicOrder = false;

    public OneStoreOrder(Customer customer, Date date, Store storeOrderMadeFrom1) {
        super(customer, date);
        this.storeOrderMadeFrom = storeOrderMadeFrom1;
        calculateDeliveryPrice();
    }

    protected void calculateDeliveryPrice() {
        deliveryPrice = storeOrderMadeFrom.getDeliveryPPK() * distanceBetweenCostumerAndStore();
    }

    public Store getStoreOrderMadeFrom() {
        return storeOrderMadeFrom;
    }

    public boolean isPartOfDynamicOrder() {
        return partOfDynamicOrder;
    }

    public void setPartOfDynamicOrder(boolean partOfDynamicOrder) {
        this.partOfDynamicOrder = partOfDynamicOrder;
    }

    @Override
    public void addItemToOrder(Item itemToAdd, double amountToAdd) throws NegativeAmountOfItemInException {
        addStoreItemToOrder(storeOrderMadeFrom.getItemsThatSellInThisStore().get(itemToAdd.getId()), Double.toString(amountToAdd));
    }

    private void addStoreItemToOrder(StoreItem itemToAdd, String amountToAdd) throws NegativeAmountOfItemInException {
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
        incIdCounter();
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

        if(!partOfDynamicOrder) {
            customer.addNewOrder(this);
        }

        storeOrderMadeFrom.getOrders().add(this);
    }

    @Override
    protected double calculatePriceOfOrderItems() {
        return orderItemCart.values().stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public double distanceBetweenCostumerAndStore() {
        return Location.distanceBetweenLocations(customer.getLocation(), storeOrderMadeFrom.getLocation());
    }

    @Override
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
