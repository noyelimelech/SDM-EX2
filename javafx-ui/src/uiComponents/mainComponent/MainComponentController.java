package uiComponents.mainComponent;

import SDM.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uiComponents.FXMLLoaderProxy;
import uiComponents.SummaryOfOrderDetails.SummaryOfOrderDetailsController;
import uiComponents.UpdateItemsGui.AddItemsToStoreGui.AddItemsToStoreGuiController;
import uiComponents.UpdateItemsGui.RemoveItemsFromStoreGui.RemoveItemsFromStoreGuiController;
import uiComponents.UpdateItemsGui.UpdateItemsInStoreGui.UpdateItemsInStoreGuiController;
import uiComponents.customerUIComponent.costumerUIController;
import uiComponents.itemComponent.itemUIController;
import uiComponents.makeNewOrderGUI.makeNewOrderGUIController;
import uiComponents.mapGUI.StoresAndCustomersMap;
import uiComponents.storeGUI.StoreGUIController;
import uiComponents.xmlLoadingGUI.XmlLoadingController;

import java.io.IOException;
import java.net.URL;

public class MainComponentController {

    @FXML private Button loadXMLButton;
    @FXML private Button showStoresButton;
    @FXML private Button showItemsButton;
    @FXML private Button showOrderButton;
    @FXML private FlowPane dynamicAreaFlowPane;
    @FXML private ScrollPane innerScrollPane;
    @FXML private Button showCustomersButton;
    @FXML private Button makeNewOrderButton;
    @FXML private VBox leftMainMenu;

    ////new buttons to aa/remove/update items in store
    @FXML private Button addItemsButton;
    @FXML private Button removeItemsButton;
    @FXML private Button updatePriceButton;
    @FXML private Button showMapButton;


    private Stage primaryStage;
    private SDMEngine sdmEngine;

    private SimpleBooleanProperty isXMLFileLoaded;
    private SimpleBooleanProperty isAnyOrderMade;

    public MainComponentController() {
        isXMLFileLoaded = new SimpleBooleanProperty(false);
        isAnyOrderMade = new SimpleBooleanProperty(false);
    }

    public SimpleBooleanProperty isXMLFileLoadedProperty() {
        return isXMLFileLoaded;
    }

    public SimpleBooleanProperty isAnyOrderMadeProperty() {
        return isAnyOrderMade;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }


    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine=sdmEngine;
        isAnyOrderMade.bind(sdmEngine.anyOrderMadeProperty());
    }

    @FXML
    public void initialize() {
        showItemsButton.disableProperty().bind(isXMLFileLoaded.not());
        showStoresButton.disableProperty().bind(isXMLFileLoaded.not());
        showOrderButton.disableProperty().bind(isXMLFileLoaded.not().or(isAnyOrderMade.not()));
        showCustomersButton.disableProperty().bind(isXMLFileLoaded.not());
        makeNewOrderButton.disableProperty().bind(isXMLFileLoaded.not());
        addItemsButton.disableProperty().bind(isXMLFileLoaded.not());
        removeItemsButton.disableProperty().bind(isXMLFileLoaded.not());
        updatePriceButton.disableProperty().bind(isXMLFileLoaded.not());
        showMapButton.disableProperty().bind(isXMLFileLoaded.not());
    }

    @FXML
    void loadXMLButtonAction()
    {
        dynamicAreaFlowPane.getChildren().clear();
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

        xmlLoadingController.setUpdateWhenLoadingIsFinishedConsumer(
                (loadingResult) -> isXMLFileLoaded.set(loadingResult));

        dynamicAreaFlowPane.getChildren().add(xmlLoadingGUI);
    }

    @FXML
    void makeNewOrderAction() throws IOException {
        dynamicAreaFlowPane.getChildren().clear();
        leftMainMenu.disableProperty().set(true);
        FXMLLoader loader = new FXMLLoader();
        URL fxmlLocation = getClass().getResource("/uiComponents/makeNewOrderGUI/makeNewOrderGUIFXML.fxml");
        loader.setLocation(fxmlLocation);



        Node makeNewOrderGui = loader.load();
        makeNewOrderGUIController makeNewOrderGUIController = loader.getController();
        makeNewOrderGUIController.setLeftMenuVBox(leftMainMenu);
        makeNewOrderGUIController.setDynamicAreaFlowPane(dynamicAreaFlowPane);
        makeNewOrderGUIController.setSdmEngine(sdmEngine);
        makeNewOrderGUIController.setCustomerComboBox();

        dynamicAreaFlowPane.getChildren().add(makeNewOrderGui);
        /*
        Node itemUI = loader.load();
        itemUIController itemController = loader.getController();

        itemController.setItemLables(item);
        dynamicAreaFlowPane.getChildren().add(itemUI);

         */

    }

    @FXML
    void showItemsButtonAction() throws IOException {
        dynamicAreaFlowPane.getChildren().clear();



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
///////NOY 10/10
    @FXML
    void showOrderButtonAction() {
        dynamicAreaFlowPane.getChildren().clear();


        for (Order order:sdmEngine.getAllOrders()) {
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/SummaryOfOrderDetails/SummaryOfOrderDetailsFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node summaryOfOrderDetails = loader.load();

            SummaryOfOrderDetailsController summaryOfOrderDetailsController=loader.getController();
            summaryOfOrderDetailsController.setDynamicAreaFlowPane(dynamicAreaFlowPane);
            summaryOfOrderDetailsController.setSdmEngine(sdmEngine);
            //summaryOfOrderDetailsController.setLeftMenuVBox(leftMenuVBox);

            summaryOfOrderDetailsController.updateFinalDetailsOnStoreAndItemsFromStore(order.getListOfOneStoreOrders());
            summaryOfOrderDetailsController.setLabelsShowOrders(order);
            summaryOfOrderDetailsController.makeUnvisibelButtons();
            dynamicAreaFlowPane.getChildren().add(summaryOfOrderDetails);
        }
    }

    @FXML
    void showStoresButtonAction() {
        dynamicAreaFlowPane.getChildren().clear();

        for (Store store : sdmEngine.getAllStores())
        {
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/storeGUI/storeGUIFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node itemUI = loader.load();
            StoreGUIController storeGUIController = loader.getController();

            storeGUIController.setStore(store);

            dynamicAreaFlowPane.getChildren().add(itemUI);

        }

    }

    //NOY 25.9
    @FXML
    void showCostumersButtonAction() {

        dynamicAreaFlowPane.getChildren().clear();

        for (Customer customer : sdmEngine.getAllCustomers())
        {
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/customerUIComponent/costumerUIFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node customerUI = loader.load();

            costumerUIController customerUIController= loader.getController();
            customerUIController.setCustomer(customer);

            dynamicAreaFlowPane.getChildren().add(customerUI);
        }



    }




/////TODO
///buttons to add/remove/update items in store
    @FXML
    void addItemsButtonAction() {

        dynamicAreaFlowPane.getChildren().clear();

        FXMLLoaderProxy loader = new FXMLLoaderProxy();
        URL fxmlLocation = getClass().getResource("/uiComponents/UpdateItemsGui/AddItemsToStoreGui/AddItemsToStoreGuiFXML.fxml");
        loader.setLocation(fxmlLocation);

        Node addItemsToStoreGui = loader.load();

        AddItemsToStoreGuiController addItemsToStoreGuiController= loader.getController();
        addItemsToStoreGuiController.setSdmEngine(sdmEngine);
        addItemsToStoreGuiController.setGuiComboBoxAndButtons();

        dynamicAreaFlowPane.getChildren().add(addItemsToStoreGui);

    }


    @FXML
    void updatePriceButtonAction() {
        dynamicAreaFlowPane.getChildren().clear();

        FXMLLoaderProxy loader = new FXMLLoaderProxy();
        URL fxmlLocation = getClass().getResource("/uiComponents/UpdateItemsGui/UpdateItemsInStoreGui/UpdateItemsInStoreGuiFXML.fxml");
        loader.setLocation(fxmlLocation);

        Node updateItemsInStoreGui = loader.load();

        UpdateItemsInStoreGuiController updateItemsInStoreGuiController = loader.getController();
        updateItemsInStoreGuiController.setSdmEngine(sdmEngine);
        updateItemsInStoreGuiController.setGuiComboBoxAndButtons();

        dynamicAreaFlowPane.getChildren().add(updateItemsInStoreGui);

    }




    @FXML
    void removeItemsButtonAction() {
        dynamicAreaFlowPane.getChildren().clear();

        FXMLLoaderProxy loader = new FXMLLoaderProxy();
        URL fxmlLocation = getClass().getResource("/uiComponents/UpdateItemsGui/RemoveItemsFromStoreGui/RemoveItemsFromStoreGuiFXML.fxml");
        loader.setLocation(fxmlLocation);

        Node removeItemsFromStoreGui = loader.load();

        RemoveItemsFromStoreGuiController removeItemsFromStoreGuiController = loader.getController();
        removeItemsFromStoreGuiController.setSdmEngine(sdmEngine);
        removeItemsFromStoreGuiController.setGuiComboBoxAndButtons();

        dynamicAreaFlowPane.getChildren().add(removeItemsFromStoreGui);
    }




    @FXML
    void showMapAction() {
        dynamicAreaFlowPane.getChildren().clear();
        StoresAndCustomersMap map = new StoresAndCustomersMap();
        map.setSdmEngine(sdmEngine);
        map.buildMap();
        dynamicAreaFlowPane.getChildren().add(map.getMap());
    }








}
