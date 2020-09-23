package uiComponents.itemComponent;

import SDM.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class itemUIController
{

    @FXML private Label itemIdLabel;

    @FXML private Label itemNameLable;

    @FXML private Label itemTypeLable;

    @FXML private Label numOfStoresLable;

    @FXML private Label avgPriceLable;

    @FXML private Label amountOfOrder;


    public void setItemIdLabel(String itemId) {
        itemIdLabel.setText(itemId);
    }

    public void setItemNameLable(String name){
        itemNameLable.setText(name);
    }

    public void setItemTypeLable(String type){
       itemTypeLable.setText(type);
    }
    public void setNumOfStoresLable(String numOfStoes){
        numOfStoresLable.setText(numOfStoes);
    }

    public void setAvgPriceLable(String avgPrice){
        avgPriceLable.setText(avgPrice);
    }

    public void setAmountOfOrder(String amount){
        amountOfOrder.setText(amount);
    }


    public void setItemLables(Item item) {
        setItemIdLabel((String.format("%d",item.getId())));
        setItemNameLable(item.getName());
        setItemTypeLable(item.getType().toString());
        setNumOfStoresLable((String.format("%d",item.getStoresSellThisItem().size())));
        setAvgPriceLable((String.format("%1$,.2f",item.getAveragePrice())));
        setAmountOfOrder((String.format("%1$,.2f",item.getTotalAmountSoldOnAllStores())));
    }
}