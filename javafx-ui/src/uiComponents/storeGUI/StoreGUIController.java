package uiComponents.storeGUI;

import SDM.Order;
import SDM.Store;
import SDM.StoreItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StoreGUIController {

    @FXML private Label storeIdLabel;
    @FXML private Label storeNameLabel;
    @FXML private Label ppkLabel;
    @FXML private Label amountOfOrder;
    @FXML private VBox storeItemPlaceHolder;
    @FXML private VBox ordersPlaceHolder;

    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
        updateGUIWithStoreData();
    }

    private void updateGUIWithStoreData() {
        storeIdLabel.setText(Integer.toString(store.getId()));
        storeNameLabel.setText(store.getName());
        ppkLabel.setText(Integer.toString(store.getDeliveryPPK()));
        amountOfOrder.setText(String.format("%.2f",store.getTotalAmountForDeliveries()));
        updateGUIWithStoreItems();
        updateGUIWithStoreOrders();
    }

    private void updateGUIWithStoreItems() {
        for(StoreItem storeItem : store.getItemsThatSellInThisStore().values()) {
            //TODO need to add code for each store item gui making
        }
    }

    private void updateGUIWithStoreOrders() {
        for(Order order : store.getOrders()) {
            //TODO need to add code for each store order gui making
        }
    }
}
