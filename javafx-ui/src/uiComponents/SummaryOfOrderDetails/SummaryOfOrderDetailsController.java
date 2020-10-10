package uiComponents.SummaryOfOrderDetails;

import SDM.Exception.NegativeAmountOfItemInException;
import SDM.OneStoreOrder;
import SDM.Order;
import SDM.SDMEngine;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import uiComponents.FXMLLoaderProxy;
import uiComponents.FinalDetailsOnStoreAndItemsFromStore.FinalDetailsOnStoreAndItemsFromStoreController;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SummaryOfOrderDetailsController {

    @FXML private Button confirmButton;
    @FXML private Button cancelOrderButton;
    @FXML private VBox dynamicVBoxSummeryDetails;
    @FXML private Label totalOrderCostLabel;
    @FXML private Label totalDeliveriesCostLabel;
    @FXML private Label TotalPriceLabel;


    private SDMEngine sdmEngine;
    private FlowPane dynamicAreaFlowPane;
    private VBox leftMenuVBox;


    public SDMEngine getSdmEngine() {
        return sdmEngine;
    }

    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine = sdmEngine;
    }

    public FlowPane getDynamicAreaFlowPane() {
        return dynamicAreaFlowPane;
    }

    public void setDynamicAreaFlowPane(FlowPane dynamicAreaFlowPane) {
        this.dynamicAreaFlowPane = dynamicAreaFlowPane;
    }

    public void updateFinalDetailsOnStoreAndItemsFromStore(List<OneStoreOrder> oneStoreOrders)
    {

        ///List<OneStoreOrder> oneStoreOrders = sdmEngine.getListOfOneStoreOrdersOfCurrentOrder();
        for (OneStoreOrder oneStoreOrder:oneStoreOrders)
        {
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/FinalDetailsOnStoreAndItemsFromStore/FinalDetailsOnStoreAndItemsFromStoreFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node finalDetailsOnStoreAndItemsFromStore = loader.load();

            FinalDetailsOnStoreAndItemsFromStoreController finalDetailsOnStoreAndItemsFromStoreController=loader.getController();
            finalDetailsOnStoreAndItemsFromStoreController.setStoreLabelsAndSetItems(oneStoreOrder);


            dynamicVBoxSummeryDetails.getChildren().add(finalDetailsOnStoreAndItemsFromStore);
        }

    }





    @FXML
    void cancelOrderButtonAction() {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("Order Cancel");
        cancelAlert.setContentText("Are you sure you want to cancel this order?");

        ButtonType yesButton = new ButtonType("YES");
        ButtonType noButton = new ButtonType("NO");

        cancelAlert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = cancelAlert.showAndWait();
        if(result.get() == yesButton) {
            sdmEngine.cancelCurrentOrder();
            dynamicAreaFlowPane.getChildren().clear();
            leftMenuVBox.disableProperty().set(false);
        }
    }

    @FXML
    void confirmButtonAction() {
        try {
            sdmEngine.completeCurrentOrder();
        } catch (NegativeAmountOfItemInException e) {
            e.printStackTrace();
        }

        Alert confirmAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmAlert.setTitle("Order Confirmed!");
        confirmAlert.setContentText("Your order is confirmed and you will have it shortly!");
        confirmAlert.show();

        dynamicAreaFlowPane.getChildren().clear();
        leftMenuVBox.disableProperty().set(false);
    }


    public void setLabels(SDMEngine sdmEngine) {

        totalOrderCostLabel.setText((String.format("%.2f",sdmEngine.getCurrentOrder().getPriceOfAllItems())));
        totalDeliveriesCostLabel.setText((String.format("%.2f",sdmEngine.getCurrentOrder().getDeliveryPrice())));
        TotalPriceLabel.setText((String.format("%.2f",sdmEngine.getCurrentOrder().getDeliveryPrice())));

    }

    public void setLeftMenuVBox(VBox leftMenuVBox) {
        this.leftMenuVBox = leftMenuVBox;
    }

    public VBox getLeftMenuVBox() {
        return leftMenuVBox;
    }

    ///////
    public void setLabelsShowOrders(Order order) {

        totalOrderCostLabel.setText((String.format("%.2f",order.getPriceOfAllItems())));
        totalDeliveriesCostLabel.setText((String.format("%.2f",order.getDeliveryPrice())));
        TotalPriceLabel.setText((String.format("%.2f",order.getDeliveryPrice())));





    }

    public void makeUnvisibelButtons() {
       cancelOrderButton.setVisible(false);
       confirmButton.setVisible(false);

    }



    /*
    idLabel.setText(String.format("%d",orderItem.getItemInOrder().getItem().getId()));
        nameLabel.setText(orderItem.getItemInOrder().getItem().getName());
        typeLabel.setText(orderItem.getItemInOrder().getItem().getType().toString());
        amountLabel.setText(String.format("%.2f",orderItem.getAmount()));
        priceLabel.setText(String.format("%d",orderItem.getItemInOrder().getPrice()));
        totalPriceLabel.setText(String.format("%.2f",orderItem.getTotalPrice()));
     */
}
