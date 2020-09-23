package uiComponents.mainComponent;

import SDM.Exception.*;
import SDM.Item;
import SDM.SDMEngine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import uiComponents.itemComponent.itemUIController;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainComponentController {

    @FXML private Button loadXMLButton;
    @FXML private Button showStoresButton;
    @FXML private Button showItemsButton;
    @FXML private Button showOrderButton;
    @FXML private Button oneStoreButton;
    @FXML private Button buyWiseOrderButton;
    @FXML private FlowPane dynamicAreaFlowPane;

    private Stage primaryStage;
    private SDMEngine sdmEngine;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }


    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine=sdmEngine;
    }

    @FXML
    void loadXMLButtonAction() throws IOException, DuplicateStoreItemException, DuplicateStoreIDException, DuplicateItemException, StoreWithNoItemException, ItemNoOneSellException, FileNotEndWithXMLException, LocationIsOutOfBorderException, TryingToGivePriceOfItemWhichIDNotExistException, JAXBException, TryingToGiveDifferentPricesForSameStoreItemException
    {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        sdmEngine.updateAllStoresAndAllItems(selectedFile.getPath());
/*

        FXMLLoader loader = new FXMLLoader();
        URL fxmlLocation = getClass().getResource("/uiComponents/mainComponent/mainComponentFXML.fxml");
        loader.setLocation(fxmlLocation);
        Node xxx = loader.load();

        dynamicAreaFlowPane.getChildren().add(xxx);
*/


    }


    @FXML
    void buyWiseOrderButtonAction() {


    }



    @FXML
    void oneStoreButtonAction() {

    }

    @FXML
    void showItemsButtonAction() throws IOException {



            for (Item item:sdmEngine.getAllItems())
            {
                FXMLLoader loader = new FXMLLoader();
                URL fxmlLocation = getClass().getResource("/uiComponents/itemComponent/itemUIFxml.fxml");
                loader.setLocation(fxmlLocation);
                Node itemUI = loader.load();
                itemUIController itemController = loader.getController();

                itemController.setItemLables(item);
                dynamicAreaFlowPane.getChildren().add(itemUI);

            }

    }

    @FXML
    void showOrderButtonAction() {

    }

    @FXML
    void showStoresButtonAction() {

    }


}
