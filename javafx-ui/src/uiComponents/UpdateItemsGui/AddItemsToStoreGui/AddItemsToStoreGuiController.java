package uiComponents.UpdateItemsGui.AddItemsToStoreGui;
///uiComponents.UpdateItemsGui.AddItemsToStoreGui.AddItemsToStoreGuiController
import SDM.Item;
import SDM.SDMEngine;
import SDM.Store;
import SDM.StoreItem;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddItemsToStoreGuiController
{


    @FXML private ComboBox<Store> chooseStoreComboBox;
    @FXML private ComboBox<Item> chooseItemsComboBox;
    @FXML private Button confirmButton;
    @FXML private TextField priceTextField;

    SDMEngine sdmEngine;

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
    }

    //methods
    @FXML
    void confirmButtonAction() {
        Store st = chooseStoreComboBox.getSelectionModel().getSelectedItem();
        Item item=chooseItemsComboBox.getSelectionModel().getSelectedItem();
        String priceSt = priceTextField.getText();
        chooseItemsComboBox.getSelectionModel().clearSelection();
        chooseStoreComboBox.getSelectionModel().clearSelection();
        priceTextField.clear();

        sdmEngine.addNewItemToStore(st,item,priceSt);

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Item added");
        successAlert.setHeaderText("Item added");
        successAlert.setContentText("Item has been added succesfully");
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
        List<Item> itemsThatNotSellInTheStore=getItemsThatNotSellInTheStore(itemsInChoosedStore);
        chooseItemsComboBox.getItems().setAll(itemsThatNotSellInTheStore);
    }

    private List<Item> getItemsThatNotSellInTheStore(Map<Integer, StoreItem> itemsInChoosedStore) {
        List<Item> itemsNoSellInThisStore=new LinkedList<>();

        for (Item item:sdmEngine.getAllItems())
        {
            if(!(itemsInChoosedStore.containsKey(item.getId())))
            {
               itemsNoSellInThisStore.add(item);
            }
        }

        return(itemsNoSellInThisStore);
    }


}
