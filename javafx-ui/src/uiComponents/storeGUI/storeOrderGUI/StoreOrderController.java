package uiComponents.storeGUI.storeOrderGUI;

import SDM.OneStoreOrder;
import SDM.Order;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import uiComponents.FXMLLoaderProxy;
import uiComponents.FinalDetailsOnStoreAndItemsFromStore.FinalDetailsOnStoreAndItemsFromStoreController;

import java.net.URL;
import java.text.SimpleDateFormat;

public class StoreOrderController {

    @FXML private Label orderDateLabel;
    @FXML private Label totalItemsLabel;
    @FXML private Label itemsTotalPriceLabel;
    @FXML private Label deliveryPriceLabel;
    @FXML private Label totalPriceLabel;
    @FXML private Label dynamicOrderLabel;
    @FXML private Button dynamicOrderInfoButton;


    private OneStoreOrder storeOrder;

    public OneStoreOrder getStoreOrder() {
        return storeOrder;
    }

    public void setStoreOrder(OneStoreOrder storeOrder) {
        this.storeOrder = storeOrder;
        updateGUIWithStoreOrderData();
    }

    private void updateGUIWithStoreOrderData() {
        orderDateLabel.setText((new SimpleDateFormat("dd/MM/yyyy")).format(storeOrder.getDate()));
        totalItemsLabel.setText(Integer.toString(storeOrder.getTotalItemsInOrder()));
        itemsTotalPriceLabel.setText(String.format("%.2f", storeOrder.getPriceOfAllItems()));
        deliveryPriceLabel.setText(String.format("%.2f", storeOrder.getDeliveryPrice()));
        totalPriceLabel.setText(String.format("%.2f", storeOrder.getTotalPrice()));

        dynamicOrderLabel.visibleProperty().set(storeOrder.isPartOfDynamicOrder());
        dynamicOrderInfoButton.visibleProperty().set(storeOrder.isPartOfDynamicOrder());
    }


    @FXML
    void dynamicOrderInfoAction() {
        Dialog<Boolean> dynamicOrderDialog = new Dialog<>();
        dynamicOrderDialog.setTitle("Information about dynamic order");
        dynamicOrderDialog.setHeaderText("Information about dynamic order");
        dynamicOrderDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        FXMLLoaderProxy loader = new FXMLLoaderProxy();
        URL fxmlLocation = getClass().getResource("/uiComponents/FinalDetailsOnStoreAndItemsFromStore/FinalDetailsOnStoreAndItemsFromStoreFXML.fxml");
        loader.setLocation(fxmlLocation);

        Node finalDetailsOnStoreAndItemsFromStore = loader.load();

        FinalDetailsOnStoreAndItemsFromStoreController finalDetailsOnStoreAndItemsFromStoreController=loader.getController();
        finalDetailsOnStoreAndItemsFromStoreController.setStoreLabelsAndSetItems(storeOrder);

        dynamicOrderDialog.getDialogPane().setContent(finalDetailsOnStoreAndItemsFromStore);
        dynamicOrderDialog.show();
    }
}
