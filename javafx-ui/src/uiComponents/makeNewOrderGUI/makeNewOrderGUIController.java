
package uiComponents.makeNewOrderGUI;

import SDM.Customer;
import SDM.SDMEngine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import uiComponents.OrderItemChoiceGui.OrderItemChoiceController;
import uiComponents.staticOrderGUI.staticOrderGUIController;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class makeNewOrderGUIController {

    @FXML private ComboBox<Customer> chooseCustomerComboBox;
    @FXML private DatePicker datePicker;
    @FXML private Button oneStoreOrderButton;
    @FXML private Button dynamicOrderButton;


    SDMEngine sdmEngine;
    FlowPane dynamicAreaFlowPane;

    @FXML
    public void initialize() {
        oneStoreOrderButton.disableProperty().bind(chooseCustomerComboBox.getSelectionModel().selectedItemProperty().isNull().or(datePicker.valueProperty().isNull()));
        dynamicOrderButton.disableProperty().bind(chooseCustomerComboBox.getSelectionModel().selectedItemProperty().isNull().or(datePicker.valueProperty().isNull()));
    }


    @FXML
    void dynamicOrderButtonAction() throws IOException {
        //noy 8/10
        //convert LocalDate to Date
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);

        sdmEngine.createNewDynamicOrder(chooseCustomerComboBox.getSelectionModel().getSelectedItem(), date);

        dynamicAreaFlowPane.getChildren().clear();

        FXMLLoader loader = new FXMLLoader();
        URL fxmlLocation = getClass().getResource("/uiComponents/OrderItemChoiceGui/OrderItemChoiceFXML.fxml");
        loader.setLocation(fxmlLocation);

        Node orderItemChoiceGui = loader.load();
        OrderItemChoiceController orderItemChoiceController=loader.getController();
        orderItemChoiceController.setSdmEngine(sdmEngine);
        orderItemChoiceController.setDynamicAreaFlowPane(dynamicAreaFlowPane);

        dynamicAreaFlowPane.getChildren().add(orderItemChoiceGui);


        //TODO chooseCustomerComboBox.getSelectionModel().getSelectedItem();
    }

    @FXML
    void oneStoreOrderButtonAction() throws IOException {
        dynamicAreaFlowPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        URL fxmlLocation = getClass().getResource("/uiComponents/staticOrderGUI/staticOrderGUIFXML.fxml");
        loader.setLocation(fxmlLocation);



        Node staticOrderGui = loader.load();
        staticOrderGUIController staticOrderGUIController = loader.getController();
        //staticOrderGUIController.setCustomerComboBoxes(sdmEngine);

        staticOrderGUIController.setSDMEngine(sdmEngine);
        staticOrderGUIController.setDynamicAreaFlowPane(dynamicAreaFlowPane);
        staticOrderGUIController.setStoreComboBox();

        //NOY 8/10
        //convert LocalDate to Date
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);

        staticOrderGUIController.setCustomer(chooseCustomerComboBox.getSelectionModel().getSelectedItem());
        staticOrderGUIController.setOrderDate(date);


        dynamicAreaFlowPane.getChildren().add(staticOrderGui);



    }
    public void setSdmEngine(SDMEngine sdmEngine)
    {
        this.sdmEngine = sdmEngine;
    }


    public void setCustomerComboBox() {
        chooseCustomerComboBox.getItems().addAll(sdmEngine.getAllCustomers());
        /*
        List<Customer> allCustomers= sdmEngine.getAllCustomers();
        for (Customer customer : allCustomers) {
            chooseCustomerComboBox.getItems().add(customer);
        }

         */


    }

    public void setDynamicAreaFlowPane(FlowPane dynamicAreaFlowPane) {
        this.dynamicAreaFlowPane = dynamicAreaFlowPane;
    }
}












