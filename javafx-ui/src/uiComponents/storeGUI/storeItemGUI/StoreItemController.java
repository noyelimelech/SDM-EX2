package uiComponents.storeGUI.storeItemGUI;

import SDM.StoreItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StoreItemController {

    @FXML private Label itemIdLabel;
    @FXML private Label itemNameLabel;
    @FXML private Label itemTypeLabel;
    @FXML private Label itemPriceLabel;
    @FXML private Label totalSoldLabel;

    private StoreItem storeItem;

    public StoreItem getStoreItem() {
        return storeItem;
    }

    public void setStoreItem(StoreItem storeItem) {
        this.storeItem = storeItem;
        updateGUIWithStoreItemData();
    }

    private void updateGUIWithStoreItemData() {
        itemIdLabel.setText(Integer.toString(storeItem.getItem().getId()));
        itemNameLabel.setText(storeItem.getItem().getName());
        itemTypeLabel.setText(storeItem.getItem().getType().toString());
        itemPriceLabel.setText(Integer.toString(storeItem.getPrice()));

        String totalAmountSoldStr = storeItem.getTotalAmountSoldInThisStore() == 0 ? "0" : String.format("%.2f", storeItem.getTotalAmountSoldInThisStore());

        totalSoldLabel.setText(totalAmountSoldStr);
    }


}
