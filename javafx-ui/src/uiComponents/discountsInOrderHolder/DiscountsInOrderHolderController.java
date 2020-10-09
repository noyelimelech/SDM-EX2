package uiComponents.discountsInOrderHolder;

import SDM.Discount;
import SDM.SDMEngine;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import uiComponents.FXMLLoaderProxy;
import uiComponents.FinalDetailsOnStoreAndItemsFromStore.FinalDetailsOnStoreAndItemsFromStoreController;
import uiComponents.SummaryOfOrderDetails.SummaryOfOrderDetailsController;
import uiComponents.discountInOrderGUI.DiscountInOrderController;

import java.net.URL;

public class DiscountsInOrderHolderController {

    @FXML private VBox discountPlaceHolder;

    private SDMEngine sdmEngine;
    private FlowPane dynamicAreaFlowPane;


    public SDMEngine getSdmEngine() {
        return sdmEngine;
    }

    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine = sdmEngine;
        insertDiscounts();
    }

    private void insertDiscounts() {
        if(sdmEngine.getListOfDiscountsOfCurrentOrder().isEmpty()) {
            Label noDiscountLabel = new Label("We Dont have any discounts for you, sorry.");
            noDiscountLabel.styleProperty().set("-fx-font-size: 30px");
            discountPlaceHolder.getChildren().add(noDiscountLabel);
        }

        for(Discount discount : sdmEngine.getListOfDiscountsOfCurrentOrder()) {
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/discountInOrderGUI/discountInOrderFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node discountUI = loader.load();

            DiscountInOrderController discountInOrderController = loader.getController();
            discountInOrderController.setDiscount(discount);
            discountInOrderController.setSdmEngine(sdmEngine);
            discountInOrderController.setDiscountsPlaceHolder(discountPlaceHolder);
            discountInOrderController.setNodeOfDiscount(discountUI);

            discountPlaceHolder.getChildren().add(discountUI);
        }
    }

    ///noy 9/10
    @FXML
    void doneDiscountsAction() {

        FXMLLoaderProxy loader = new FXMLLoaderProxy();
        URL fxmlLocation = getClass().getResource("/uiComponents/SummaryOfOrderDetails/SummaryOfOrderDetailsFXML.fxml");
        loader.setLocation(fxmlLocation);

        Node summaryOfOrderDetails = loader.load();

        SummaryOfOrderDetailsController summaryOfOrderDetailsController=loader.getController();
        summaryOfOrderDetailsController.setDynamicAreaFlowPane(dynamicAreaFlowPane);
        summaryOfOrderDetailsController.setSdmEngine(sdmEngine);
        summaryOfOrderDetailsController.updateFinalDetailsOnStoreAndItemsFromStore();
        summaryOfOrderDetailsController.setLabels(sdmEngine);

        dynamicAreaFlowPane.getChildren().clear();
        dynamicAreaFlowPane.getChildren().add(summaryOfOrderDetails);

        //goto last page of summary!
        //MOVE TO summaryOFoRDERdETAILS
        //לעשות סט ל- אנגין ודיינמיקפלואופין

    }

    public void setDynamicAreaFlowPane(FlowPane dynamicAreaFlowPane) {
        this.dynamicAreaFlowPane = dynamicAreaFlowPane;
    }

    public FlowPane getDynamicAreaFlowPane() {
        return dynamicAreaFlowPane;
    }
}
