package uiComponents.mainComponent;

import SDM.Exception.*;
import SDM.Item;
import SDM.SDMEngine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import uiComponents.itemComponent.itemUIController;
import uiComponents.xmlLoadingGUI.XmlLoadingController;

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
    void loadXMLButtonAction()
    {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlLocation = getClass().getResource("/uiComponents/xmlLoadingGUI/xmlLoadingFXML.fxml");
        loader.setLocation(fxmlLocation);
        Node xmlLoadingGUI = null;
        try {
            xmlLoadingGUI = loader.load();
        } catch (IOException e) {
            Alert loadingAlert = new Alert(Alert.AlertType.ERROR);
            loadingAlert.setTitle("FXML Loading ERROR");
            loadingAlert.setHeaderText("Seems like we got a problem on our error loading... Nothing you can do...");

        }
        XmlLoadingController xmlLoadingController = loader.getController();
        xmlLoadingController.setSdmEngine(sdmEngine);
        xmlLoadingController.setStage(primaryStage);
        dynamicAreaFlowPane.getChildren().add(xmlLoadingGUI);


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
