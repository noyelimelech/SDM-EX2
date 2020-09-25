package uiComponents.storeGUI;

import SDM.Order;
import SDM.Store;
import SDM.StoreItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uiComponents.FXMLLoaderProxy;
import uiComponents.storeGUI.storeItemGUI.StoreItemController;
import uiComponents.storeGUI.storeOrderGUI.StoreOrderController;

import java.io.IOException;
import java.net.URL;

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
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/storeGUI/storeItemGUI/storeItemFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node storeItemGUI = loader.load();
            StoreItemController storeItemController = loader.getController();

            storeItemController.setStoreItem(storeItem);
            storeItemPlaceHolder.getChildren().add(storeItemGUI);
        }
    }

    private void updateGUIWithStoreOrders() {
        for(Order order : store.getOrders()) {
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/storeGUI/storeOrderGUI/storeOrderFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node storeOrderGUI = loader.load();
            StoreOrderController storeOrderController = loader.getController();

            storeOrderController.setStoreOrder(order);
            ordersPlaceHolder.getChildren().add(storeOrderGUI);
        }
    }
}
