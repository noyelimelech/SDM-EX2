package uiComponents.afterOrderStoresGui;

import SDM.Discount;
import SDM.OneStoreOrder;
import SDM.SDMEngine;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import uiComponents.FXMLLoaderProxy;
import uiComponents.afterOrderStoreWeBuyFromGui.afterOrderStoreWeBuyFromGuiController;
import uiComponents.discountGui.discountGuiController;
import uiComponents.discountsInOrderHolder.DiscountsInOrderHolderController;

import java.net.URL;

public class afterOrderStoresGuiController {

    @FXML private Button continueButton;
    @FXML private VBox dynamicVBox;

    SDMEngine sdmEngine;
    private FlowPane dynamicAreaFlowPane;
    private VBox leftMenuVBox;

    public SDMEngine getSdmEngine() {
        return sdmEngine;
    }

    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine = sdmEngine;
        updateGuiWithStoresWeBuyFrom();
    }

    @FXML
    public void continueButtonAction() {
        dynamicAreaFlowPane.getChildren().clear();
        FXMLLoaderProxy loader = new FXMLLoaderProxy();
        URL fxmlLocation = getClass().getResource("/uiComponents/discountsInOrderHolder/discountsInOrderHolderFXML.fxml");
        loader.setLocation(fxmlLocation);

        Node discountsInOrderHolder = loader.load();
        DiscountsInOrderHolderController discountsInOrderHolderController=loader.getController();
        discountsInOrderHolderController.setSdmEngine(sdmEngine);
        discountsInOrderHolderController.setDynamicAreaFlowPane(dynamicAreaFlowPane);
        discountsInOrderHolderController.setLeftMenuVBox(leftMenuVBox);

        dynamicAreaFlowPane.getChildren().add(discountsInOrderHolder);
    }

    public void updateGuiWithStoresWeBuyFrom()
    {

        for(OneStoreOrder oneStoreOrder: sdmEngine.getListOfOneStoreOrdersOfCurrentOrder())
        {

                FXMLLoaderProxy loader = new FXMLLoaderProxy();
                URL fxmlLocation = getClass().getResource("/uiComponents/afterOrderStoreWeBuyFromGui/afetrOrderStoreWeBuyFromGuiFXML.fxml");
                loader.setLocation(fxmlLocation);

                Node storeWeBuyFromGui = loader.load();
                afterOrderStoreWeBuyFromGuiController storeWeBuyFromGuiController = loader.getController();

                storeWeBuyFromGuiController.updateLabels(oneStoreOrder);
                dynamicVBox.getChildren().add(storeWeBuyFromGui);
        }

    }


    public void setDynamicAreaFlowPane(FlowPane dynamicAreaFlowPane) {
        this.dynamicAreaFlowPane = dynamicAreaFlowPane;
    }

    public FlowPane getDynamicAreaFlowPane() {
        return dynamicAreaFlowPane;
    }

    public void setLeftMenuVBox(VBox leftMenuVBox) {
        this.leftMenuVBox = leftMenuVBox;
    }

    public VBox getLeftMenuVBox() {
        return leftMenuVBox;
    }
}



