package uiComponents.OrderItemChoiceGui;

import SDM.*;
import SDM.Exception.NegativeAmountOfItemInException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import uiComponents.FXMLLoaderProxy;
import uiComponents.afterOrderStoresGui.afterOrderStoresGuiController;
import uiComponents.discountsInOrderHolder.DiscountsInOrderHolderController;

import javax.xml.crypto.dsig.SignatureMethod;
import java.net.URL;

public class OrderItemChoiceController {

    @FXML private TableView<StoreItemAdapter> itemTableView;
    @FXML private TableColumn<StoreItemAdapter, Integer> iDCol;
    @FXML private TableColumn<StoreItemAdapter, String> nameCol;
    @FXML private TableColumn<StoreItemAdapter, Item.ItemType> typeCol;
    @FXML private TableColumn<StoreItemAdapter, Integer> priceCol;
    @FXML private Button addItemsButton;
    @FXML private Button continueButton;
    @FXML private TextField itemsCounterTextField;
    @FXML private Label succesLabel;
    @FXML private Label textFieldErrorLabel;

    private SDMEngine sdmEngine;
    private SimpleBooleanProperty buyWiseOrder = new SimpleBooleanProperty(true);
    FlowPane dynamicAreaFlowPane;
    private VBox leftMenuVBox;


    @FXML
    public void initialize() {
        setTableColValueFactory();

        priceCol.visibleProperty().bind(buyWiseOrder.not());

        itemTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        itemTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            itemsCounterTextField.disableProperty().set(newSelection == null);
            addItemsButton.disableProperty().set(newSelection == null);
            if(newSelection != null) {
                succesLabel.visibleProperty().set(false);
            }
        });

        itemsCounterTextField.textProperty().addListener((observable -> {
                textFieldErrorLabel.visibleProperty().set(!textFieldVerified());
                addItemsButton.disableProperty().set(!textFieldVerified() ||  itemTableView.getSelectionModel().selectedItemProperty().isNull().get());
        }));
    }

    private void setTableColValueFactory() {
        iDCol.setCellValueFactory(new PropertyValueFactory<StoreItemAdapter, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<StoreItemAdapter, String>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<StoreItemAdapter, Item.ItemType>("type"));
        priceCol.setCellValueFactory(new PropertyValueFactory<StoreItemAdapter, Integer>("price"));
    }

    public SDMEngine getSdmEngine() {
        return sdmEngine;
    }

    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine = sdmEngine;
        buyWiseOrder.set(sdmEngine.getCurrentOrder() instanceof DynamicOrder);
        populateTableViewWithItems();
    }

    public boolean getBuyWiseOrder() {
        return buyWiseOrder.get();
    }

    public SimpleBooleanProperty buyWiseOrderProperty() {
        return buyWiseOrder;
    }

    public void setBuyWiseOrder(boolean buyWiseOrder) {
        this.buyWiseOrder.set(buyWiseOrder);
    }

    public FlowPane getDynamicAreaFlowPane() {
        return dynamicAreaFlowPane;
    }

    public void setDynamicAreaFlowPane(FlowPane dynamicAreaFlowPane) {
        this.dynamicAreaFlowPane = dynamicAreaFlowPane;
    }

    @FXML
    void addItemButtonAction() {
        if(textFieldVerified()) {
            try {
                sdmEngine.addItemToCurrentOrder(itemTableView.getSelectionModel().getSelectedItem().getId(), Double.parseDouble(itemsCounterTextField.getText()));
                itemTableView.getSelectionModel().clearSelection();
                itemsCounterTextField.textProperty().set("");
                succesLabel.visibleProperty().set(true);
            } catch (NegativeAmountOfItemInException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean textFieldVerified() {
        if(itemTableView.getSelectionModel().selectedItemProperty().isNull().get()){
            return true;
        }
        if(itemTableView.getSelectionModel().selectedItemProperty().get().getType() == Item.ItemType.QUANTITY) {
            return itemsCounterTextField.textProperty().get().matches("^\\d+$");
            }
        else {
            return itemsCounterTextField.textProperty().get().matches("^\\d+(\\.\\d+)?$");
        }
    }

    @FXML
    void finishOrderButtonAction() throws NegativeAmountOfItemInException {
        dynamicAreaFlowPane.getChildren().clear();
        sdmEngine.continueCurrentOrderToDiscounts();
        if(buyWiseOrder.get()) {
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/afterOrderStoresGui/afterOrderStoresGuiFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node afterOrderStoresGui = loader.load();
            afterOrderStoresGuiController controller=loader.getController();
            controller.setSdmEngine(sdmEngine);
            controller.setLeftMenuVBox(leftMenuVBox);
            controller.setDynamicAreaFlowPane(dynamicAreaFlowPane);

            dynamicAreaFlowPane.getChildren().add(afterOrderStoresGui);
        }
        else {
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/discountsInOrderHolder/discountsInOrderHolderFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node discountsInOrderHolder = loader.load();
            DiscountsInOrderHolderController discountsInOrderHolderController=loader.getController();
            discountsInOrderHolderController.setSdmEngine(sdmEngine);
            discountsInOrderHolderController.setLeftMenuVBox(leftMenuVBox);
            discountsInOrderHolderController.setDynamicAreaFlowPane(dynamicAreaFlowPane);

            dynamicAreaFlowPane.getChildren().add(discountsInOrderHolder);
        }

    }

    private void populateTableViewWithItems() {
        if(!getBuyWiseOrder()) {
            for(StoreItem storeItem : ((OneStoreOrder)sdmEngine.getCurrentOrder()).getStoreOrderMadeFrom().getItemsThatSellInThisStore().values()) {
                itemTableView.getItems().add(new StoreItemAdapter(storeItem));
            }
        }
        else {
            for(Item item : sdmEngine.getAllItems()) {
                itemTableView.getItems().add(new StoreItemAdapter(item));
            }
        }
    }

    public void setLeftMenuVBox(VBox leftMenuVBox) {
        this.leftMenuVBox = leftMenuVBox;
    }

    public VBox getLeftMenuVBox() {
        return leftMenuVBox;
    }
}
