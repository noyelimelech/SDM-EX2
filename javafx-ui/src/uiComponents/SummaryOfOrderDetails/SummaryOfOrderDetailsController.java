package uiComponents.SummaryOfOrderDetails;

import SDM.OneStoreOrder;
import SDM.SDMEngine;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import uiComponents.FXMLLoaderProxy;
import uiComponents.FinalDetailsOnStoreAndItemsFromStore.FinalDetailsOnStoreAndItemsFromStoreController;

import java.net.URL;
import java.util.List;

public class SummaryOfOrderDetailsController {

    @FXML private Button confirmButton;
    @FXML private Button cancelOrderButton;
    @FXML private VBox dynamicVBoxSummeryDetails;
    @FXML private Label totalOrderCostLabel;
    @FXML private Label totalDeliveriesCostLabel;
    @FXML private Label TotalPriceLabel;


    private SDMEngine sdmEngine;
    private FlowPane dynamicAreaFlowPane;



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

    public void updateFinalDetailsOnStoreAndItemsFromStore()
    {

        List<OneStoreOrder> oneStoreOrders = sdmEngine.getListOfOneStoreOrdersOfCurrentOrder();
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

    }

    @FXML
    void confirmButtonAction() {

    }


    public void setLabels(SDMEngine sdmEngine) {

        totalOrderCostLabel.setText((String.format("%.2f",sdmEngine.getCurrentOrder().getPriceOfAllItems())));
        totalDeliveriesCostLabel.setText((String.format("%.2f",sdmEngine.getCurrentOrder().getDeliveryPrice())));
        TotalPriceLabel.setText((String.format("%.2f",sdmEngine.getCurrentOrder().getDeliveryPrice())));

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
