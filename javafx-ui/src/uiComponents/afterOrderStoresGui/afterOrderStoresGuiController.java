package uiComponents.afterOrderStoresGui;

import SDM.Discount;
import SDM.OneStoreOrder;
import SDM.SDMEngine;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import uiComponents.FXMLLoaderProxy;
import uiComponents.afterOrderStoreWeBuyFromGui.afterOrderStoreWeBuyFromGuiController;
import uiComponents.discountGui.discountGuiController;

import java.net.URL;

public class afterOrderStoresGuiController {

    @FXML private Button continueButton;
    @FXML private VBox dynamicVBox;

    SDMEngine sdmEngine;



    @FXML
    public void continueButtonAction() {

    }

    public void updateGuiWithStoresWeBuyFrom()
    {

        for(OneStoreOrder oneStoreOrder: sdmEngine.getListOfOneStoreOrdersOfCurrentOrder())
        {

                FXMLLoaderProxy loader = new FXMLLoaderProxy();
                URL fxmlLocation = getClass().getResource("/uiComponents/afterOrderStoreWeBuyFromGui/afterOrderStoreWeBuyFromGuiFXML.fxml");
                loader.setLocation(fxmlLocation);

                Node storeWeBuyFromGui = loader.load();
                afterOrderStoreWeBuyFromGuiController storeWeBuyFromGuiController = loader.getController();

                storeWeBuyFromGuiController.updateLabels(oneStoreOrder);
                dynamicVBox.getChildren().add(storeWeBuyFromGui);
        }

    }










    }



