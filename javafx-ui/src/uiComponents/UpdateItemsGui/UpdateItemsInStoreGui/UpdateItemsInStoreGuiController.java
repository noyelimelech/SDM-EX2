package uiComponents.UpdateItemsGui.UpdateItemsInStoreGui;
////uiComponents.UpdateItemsGui.UpdateItemsInStoreGui.UpdateItemsInStoreGuiController
import SDM.Item;
import SDM.SDMEngine;
import SDM.Store;
import SDM.StoreItem;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UpdateItemsInStoreGuiController {

    @FXML private ComboBox<Store> chooseStoreComboBox;
    @FXML private ComboBox<Item> chooseItemsComboBox;
    @FXML private Button confirmButton;
    @FXML private TextField priceTextField;
    @FXML private Label textFieldErrorLabel;

    SDMEngine sdmEngine;


    //methods

    @FXML
    public void initialize() {
        chooseItemsComboBox.disableProperty().bind(chooseStoreComboBox.getSelectionModel().selectedItemProperty().isNull());
        priceTextField.disableProperty().bind(chooseItemsComboBox.getSelectionModel().selectedItemProperty().isNull());
        chooseStoreComboBox.getSelectionModel().
                selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null) {
                chooseItemsComboBox.getSelectionModel().clearSelection();
                setItemsComboBox(newValue);
            }
        }));

        priceTextField.textProperty().addListener(((obs, oldValue, newValue) -> {
            if(newValue == null){
                confirmButton.disableProperty().set(true);
                textFieldErrorLabel.visibleProperty().set(false);
            }
            else {
                if(!newValue.equals("")) {
                    textFieldErrorLabel.visibleProperty().set(!textFieldVerified());
                    confirmButton.disableProperty().set(!textFieldVerified());
                }
                else{
                    confirmButton.disableProperty().set(true);
                    textFieldErrorLabel.visibleProperty().set(false);
                }
            }
        }));
    }

    private boolean textFieldVerified() {
        return priceTextField.textProperty().get().matches("^\\d+$");
    }

    @FXML
    void confirmButtonAction() {
        Store st = chooseStoreComboBox.getSelectionModel().getSelectedItem();
        Item item=chooseItemsComboBox.getSelectionModel().getSelectedItem();
        String priceSt = priceTextField.getText();
        chooseItemsComboBox.getSelectionModel().clearSelection();
        chooseStoreComboBox.getSelectionModel().clearSelection();
        priceTextField.clear();

        sdmEngine.updatePriceOfItem(st,item,priceSt);

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Item updated");
        successAlert.setHeaderText("Item updated");
        successAlert.setContentText("Item has been updated succesfully");
        successAlert.show();
    }



    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine = sdmEngine;
    }

    public void setGuiComboBoxAndButtons()
    {
        setStoreComboBox();

    }


    public void setStoreComboBox() {
        chooseStoreComboBox.getItems().addAll(sdmEngine.getAllStores());
    }


    public void setItemsComboBox(Store st) {

        Map<Integer, StoreItem> itemsInChoosedStore= st.getItemsThatSellInThisStore();

        chooseItemsComboBox.getItems().clear();

        for (StoreItem storeItem:itemsInChoosedStore.values())
        {
            chooseItemsComboBox.getItems().add(storeItem.getItem());

        }
    }














}
