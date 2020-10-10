package uiComponents.storeGUI.storeOrderGUI;

import SDM.OneStoreOrder;
import SDM.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;

public class StoreOrderController {

    @FXML private Label orderDateLabel;
    @FXML private Label totalItemsLabel;
    @FXML private Label itemsTotalPriceLabel;
    @FXML private Label deliveryPriceLabel;
    @FXML private Label totalPriceLabel;

    private OneStoreOrder storeOrder;

    public OneStoreOrder getStoreOrder() {
        return storeOrder;
    }

    public void setStoreOrder(OneStoreOrder storeOrder) {
        this.storeOrder = storeOrder;
        updateGUIWithStoreOrderData();
    }

    private void updateGUIWithStoreOrderData() {
        orderDateLabel.setText((new SimpleDateFormat("dd/MM-HH:mm")).format(storeOrder.getDate()));
        totalItemsLabel.setText(Integer.toString(storeOrder.getTotalItemsInOrder()));
        itemsTotalPriceLabel.setText(String.format("%.2f", storeOrder.getPriceOfAllItems()));
        deliveryPriceLabel.setText(String.format("%.2f", storeOrder.getDeliveryPrice()));
        totalPriceLabel.setText(String.format("%.2f", storeOrder.getTotalPrice()));
    }
}
