package SDM;

import SDM.Exception.*;
import SDM.jaxb.schema.XMLHandlerBaseOnSchema;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;

public class SDMEngine {

    private Map<Integer, Store> allStores = new HashMap<>();
    private Map<Integer, Item> allItems = new HashMap<>();
    private Map<Integer, Customer> allCustomers = new HashMap<>();
    private List<Order> allOrders;
    private Order currentOrder;
    private Map<Integer, StoreItem> allStoreItemsWithPriceForSpecificStore = new HashMap<>(); //private Map for storeItems to show to UI
    private boolean xmlFileLoaded = false;

    public List<Customer> getAllCustomers() {
        return (new ArrayList<>(allCustomers.values()));
    }

    public Map<Integer, Store> getAllStoresMap() {
        return this.allStores;
    }

    public List<Store> getAllStores() {
        return new ArrayList<>(allStores.values());
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(allItems.values());
    }

    public List<Order> getAllOrders() {
        return allOrders;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public List<StoreItem> getAllStoreItemsWithPriceForSpecificStore() {
        return new ArrayList<>(allStoreItemsWithPriceForSpecificStore.values());
    }

    public boolean isXMLFileLoaded() {
        return xmlFileLoaded;
    }

    public Item.ItemType getItemTypeByID(int itemID) {
        return allItems.get(itemID).getType();
    }

    public void updateAllStoresAndAllItemsAndAllCustomers(String stPath) throws DuplicateStoreIDException, DuplicateStoreItemException, LocationIsOutOfBorderException, JAXBException, FileNotFoundException, DuplicateItemException, FileNotEndWithXMLException, TryingToGivePriceOfItemWhichIDNotExistException, TryingToGiveDifferentPricesForSameStoreItemException, ItemNoOneSellException, StoreWithNoItemException {
        Map<Integer, Item> tempAllItems;
        Map<Integer, Store> tempAllStores = new HashMap<>();
        Map<Integer, Customer> tempAllCustomers ;

        XMLHandlerBaseOnSchema xmlHandler = new XMLHandlerBaseOnSchema();
        xmlHandler.updateStoresAndItemsAndCostumers(stPath);

        tempAllItems = xmlHandler.getItems();

        tempAllCustomers= xmlHandler.getCostumers();

        //convert List of store to Map of<int id,Store)
        for (Store st : xmlHandler.getStores()) {
            tempAllStores.put(st.getId(), st);
        }

        updateAllItemWithTheStoresWhoSellThem(tempAllItems, tempAllStores);

        verifyEveryItemSoldByAtLeastOneStore(tempAllItems);
        verifyEveryStoreSellAtLeastOneItem(tempAllStores);
        xmlFileLoaded = true;
        allStores = tempAllStores;
        allItems = tempAllItems;

        allCustomers = tempAllCustomers;

        allOrders = new LinkedList<>();

    }

    private void updateAllItemWithTheStoresWhoSellThem(Map<Integer, Item> tempAllItems, Map<Integer, Store> tempAllStores) {
        for (Item item : tempAllItems.values()) {
            for (Store st : tempAllStores.values()) {
                if (st.getItemsThatSellInThisStore().containsKey(item.getId())) {
                    item.setStoresSellThisItem(st);
                }
            }
        }
    }


    private void verifyEveryItemSoldByAtLeastOneStore(Map<Integer, Item> tempAllItems) throws ItemNoOneSellException {
        for(Item item : tempAllItems.values()) {
            if(item.getStoresSellThisItem().size() == 0) {
                throw new ItemNoOneSellException(item.getId());
            }
        }
    }

    private void verifyEveryStoreSellAtLeastOneItem(Map<Integer, Store> tempAllStores) throws StoreWithNoItemException {
        for(Store store : tempAllStores.values()) {
            if(store.getItemsThatSellInThisStore().size() == 0) {
                throw new StoreWithNoItemException(store.getId());
            }
        }
    }

    public void CheckIfIsValidStoreId(int storeId) throws InvalidIdStoreChooseException {
        boolean flaIsValidIdStore = allStores.containsKey(storeId);
        if (!flaIsValidIdStore) {
            throw (new InvalidIdStoreChooseException(storeId));
        }
    }

    public boolean checkIfThisLocationInUsedOfStore(Location costumerLocationToCheck) {
        boolean flagIsValidCostumerLocation = true;

        for (Store st : this.getAllStores()) {
            if (st.getLocation().equals(costumerLocationToCheck)) {
                flagIsValidCostumerLocation = false;
                break;
            }
        }
        return flagIsValidCostumerLocation;


    }

    public void updateAllStoreItemsForSaleInCurrentStoreOrder(Store store) {
        allStoreItemsWithPriceForSpecificStore = new HashMap<>();

        for (Item item : allItems.values()) {
            StoreItem storeItem = new StoreItem();
            storeItem.setItem(item);
            //currentOrder.setStoreOrderMadeFrom(store);
            storeItem.setStore(store);
            int priceOfItem = getPriceOfItemInThisStoreORZero(item.getId(),store);
            storeItem.setPrice(priceOfItem);
            allStoreItemsWithPriceForSpecificStore.put(item.getId(), storeItem);
        }
    }

    public void createNewDynamicOrder(Customer customerEX1, Date dateOrder) {
        currentOrder = Order.makeNewOrder(customerEX1, dateOrder, null, OrderType.DYNAMIC_ORDER);
    }

    public void createNewOneStoreOrder(Customer customerEX1, Date dateOrder, Store store) {
        currentOrder = Order.makeNewOrder(customerEX1, dateOrder, store, OrderType.ONE_STORE_ORDER);
    }

    public void addItemToCurrentOrder(int choosedItemId, double choosedAmountOfItem) throws NegativeAmountOfItemInException
    {
        this.currentOrder.addItemToOrder(allItems.get(choosedItemId),choosedAmountOfItem);
    }

    public void continueCurrentOrderToDiscounts() throws NegativeAmountOfItemInException {
        currentOrder.continueToDiscounts();
    }

    public List<OneStoreOrder> getListOfOneStoreOrdersOfCurrentOrder() {
        List<OneStoreOrder> listOfInnerOSO = null;

        if(currentOrder instanceof DynamicOrder) {
            listOfInnerOSO = new ArrayList<OneStoreOrder>(((DynamicOrder) currentOrder).getInnerOneStoreOrderMap().values());
        }

        return listOfInnerOSO;
    }

    public List<Discount> getListOfDiscountsOfCurrentOrder() {
        return currentOrder.getDiscountsAvailable();
    }

    public boolean useDiscountOfCurrentOrder(Discount discountToUse) throws NegativeAmountOfItemInException {
        return currentOrder.useDiscount(discountToUse, null);
    }

    public boolean useDiscountOfCurrentOrder(Discount discountToUse, Offer offerChosen) throws NegativeAmountOfItemInException {
        return currentOrder.useDiscount(discountToUse, offerChosen);
    }

    public void completeCurrentOrder() throws NegativeAmountOfItemInException {
        currentOrder.completeOrder();
        allOrders.add(currentOrder);
        currentOrder = null;
    }

    private int getPriceOfItemInThisStoreORZero(int itemId, Store store) {
        int resPrice = 0;

        if (store.getItemsThatSellInThisStore().containsKey(itemId)) {
            resPrice = store.getItemsThatSellInThisStore().get(itemId).getPrice();
        }

        return resPrice;
    }

    public boolean checkIfThisValidItemId(int choosedItemNumber) {
        return allStoreItemsWithPriceForSpecificStore.containsKey(choosedItemNumber);
    }

    public boolean checkIfItemPriceIsNotZero(int choosedItemNumber) {
        return (allStoreItemsWithPriceForSpecificStore.get(choosedItemNumber).getPrice()) != 0;
    }

    public void addNewItemToStore(int storeID, Item itemToAdd, int priceOfItem) {
        //Store storeToAddItem = allStores.get(storeID).addNewItem();
        //storeToAddItem.getItemsThatSellInThisStore().
    }
}

