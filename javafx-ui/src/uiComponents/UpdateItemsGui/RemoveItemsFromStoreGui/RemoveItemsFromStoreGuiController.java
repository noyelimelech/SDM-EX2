package uiComponents.UpdateItemsGui.RemoveItemsFromStoreGui;
////uiComponents.UpdateItemsGui.RemoveItemsFromStoreGui.RemoveItemsFromStoreGuiController
import SDM.Item;
import SDM.SDMEngine;
import SDM.Store;
import SDM.StoreItem;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.Map;

public class RemoveItemsFromStoreGuiController {

    @FXML
    private ComboBox<Store> chooseStoreComboBox;
    @FXML
    private ComboBox<Item> chooseItemsComboBox;
    @FXML
    private Button confirmButton;

    SDMEngine sdmEngine;


    //methods

    @FXML
    public void initialize() {
        chooseItemsComboBox.disableProperty().bind(chooseStoreComboBox.getSelectionModel().selectedItemProperty().isNull());

        chooseStoreComboBox.getSelectionModel().
                selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                chooseItemsComboBox.getSelectionModel().clearSelection();
                setItemsComboBox(newValue);
            }
        }));
    }


    @FXML
    void confirmButtonAction() {
        Store st = chooseStoreComboBox.getSelectionModel().getSelectedItem();
        Item item = chooseItemsComboBox.getSelectionModel().getSelectedItem();

        chooseItemsComboBox.getSelectionModel().clearSelection();
        chooseStoreComboBox.getSelectionModel().clearSelection();


        if(sdmEngine.removeItemFromStore(st, item))
        {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Item removed");
            successAlert.setHeaderText("Item removed");
            successAlert.setContentText("Item has been removed succesfully");
            successAlert.show();
        }

        else
        {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Failed to delete item");
            successAlert.setHeaderText("Failed to delete item");
            successAlert.setContentText("Failed to delete item");
            successAlert.show();
        }


    }


    public  void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine = sdmEngine;
    }

    public void setGuiComboBoxAndButtons() {
        setStoreComboBox();

    }


    public void setStoreComboBox() {
        chooseStoreComboBox.getItems().addAll(sdmEngine.getAllStores());
    }


    public void setItemsComboBox(Store st) {

        Map<Integer, StoreItem> itemsInChoosedStore = st.getItemsThatSellInThisStore();

        chooseItemsComboBox.getItems().clear();

        for (StoreItem storeItem : itemsInChoosedStore.values()) {
            chooseItemsComboBox.getItems().add(storeItem.getItem());

        }

    }
}
