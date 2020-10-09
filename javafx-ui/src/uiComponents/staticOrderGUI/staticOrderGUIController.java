package uiComponents.staticOrderGUI;

import SDM.Customer;
import SDM.SDMEngine;
import SDM.Store;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import uiComponents.FXMLLoaderProxy;
import uiComponents.OrderItemChoiceGui.OrderItemChoiceController;
import uiComponents.storeGUI.StoreGUIController;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class staticOrderGUIController {

    @FXML private ComboBox<Store> chooseStoreComboBox;
    @FXML private Label ppkLabel;
    @FXML private Button continueButton;

    SDMEngine sdmEngine;
    FlowPane dynamicAreaFlowPane;

    //noy 8/10
    Date orderDate;
    Customer customer;
    Store store;

    @FXML
    public void initialize() {
        continueButton.disableProperty().bind(chooseStoreComboBox.getSelectionModel().selectedItemProperty().isNull());
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setStore(Store store) {
        this.store = store;

    }

    @FXML
    void continueButtonAction() throws IOException {

        //noy 8/10
        this.store=chooseStoreComboBox.getSelectionModel().getSelectedItem();
        sdmEngine.createNewOneStoreOrder(customer,orderDate,store);

        dynamicAreaFlowPane.getChildren().clear();

        FXMLLoader loader = new FXMLLoader();
        URL fxmlLocation = getClass().getResource("/uiComponents/OrderItemChoiceGui/OrderItemChoiceFXML.fxml");
        loader.setLocation(fxmlLocation);

        Node orderItemChoiceGui = loader.load();
        OrderItemChoiceController orderItemChoiceController=loader.getController();
        orderItemChoiceController.setSdmEngine(sdmEngine);
        orderItemChoiceController.setDynamicAreaFlowPane(dynamicAreaFlowPane);

        dynamicAreaFlowPane.getChildren().add(orderItemChoiceGui);


        }



    public void setStoreComboBox() {

        List<Store> allStores = sdmEngine.getAllStores();
        for (Store st : allStores) {
            chooseStoreComboBox.getItems().add(st);
        }
    }


        public void setSDMEngine (SDMEngine sdmEngine){

            this.sdmEngine = sdmEngine;
            chooseStoreComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    ppkLabel.setText(Integer.toString(newValue.getDeliveryPPK()));
                    ppkLabel.setVisible(true);
                }
            });
        }

        public void setDynamicAreaFlowPane(FlowPane dynamicAreaFlowPane){
            this.dynamicAreaFlowPane = dynamicAreaFlowPane;
        }
    }
